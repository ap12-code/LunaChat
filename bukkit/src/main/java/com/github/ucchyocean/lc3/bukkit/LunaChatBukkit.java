/*
 * @author     ucchy
 * @license    LGPLv3
 * @copyright  Copyright ucchy 2020
 */
package com.github.ucchyocean.lc3.bukkit;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.github.ucchyocean.lc3.*;
import com.github.ucchyocean.lc3.bukkit.channel.BukkitChannel;
import com.github.ucchyocean.lc3.bukkit.member.ChannelMemberBukkit;
import com.github.ucchyocean.lc3.bukkit.util.UtilityBukkit;
import com.github.ucchyocean.lc3.fabric.channel.Channel;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.DrilldownPie;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.ucchyocean.lc3.bukkit.bridge.DynmapBridge;
import com.github.ucchyocean.lc3.bukkit.bridge.McMMOBridge;
import com.github.ucchyocean.lc3.bukkit.bridge.MultiverseCoreBridge;
import com.github.ucchyocean.lc3.bukkit.bridge.VaultChatBridge;
import com.github.ucchyocean.lc3.fabric.channel.ChannelManager;
import com.github.ucchyocean.lc3.command.LunaChatCommand;
import com.github.ucchyocean.lc3.command.LunaChatJapanizeCommand;
import com.github.ucchyocean.lc3.command.LunaChatMessageCommand;
import com.github.ucchyocean.lc3.command.LunaChatReplyCommand;
import com.github.ucchyocean.lc3.fabric.member.ChannelMember;
import org.jetbrains.annotations.NotNull;

/**
 * LunaChatのBukkit実装
 * @author ucchy
 */
public class LunaChatBukkit extends JavaPlugin implements PluginInterface {

    private static LunaChatBukkit instance;

    private LunaChatConfig config;
    private ChannelManager manager;
    private UUIDCacheData uuidCacheData;

    private VaultChatBridge vaultchat;
    private LunaChatLogger pluginLogger;
    private DynmapBridge dynmap;
    private MultiverseCoreBridge multiverse;

    private ScheduledTask expireCheckerTask;
    private LunaChatNormalChatLogger normalChatLogger;

    private LunaChatCommand lunachatCommand;
    private LunaChatMessageCommand messageCommand;
    private LunaChatReplyCommand replyCommand;
    private LunaChatJapanizeCommand lcjapanizeCommand;

    /**
     * プラグインが有効化されたときに呼び出されるメソッド
     * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
     */
    @Override
    public void onEnable() {

        LunaChat.setPlugin(this);
        LunaChat.setMode(LunaChatMode.BUKKIT);

        // Metrics
        Metrics metrics = new Metrics(this, 7936);
        metrics.addCustomChart(new DrilldownPie(
                "minecraft_server_version", () -> {
                    Map<String, Map<String, Integer>> map = new HashMap<>();
                    Map<String, Integer> sub = new HashMap<>();
                    sub.put(Bukkit.getVersion(), 1);
                    map.put(Bukkit.getName(), sub);
                    return map;
                }));

        // 変数などの初期化
        config = new LunaChatConfig(getDataFolder(), getFile());
        uuidCacheData = new UUIDCacheData(getDataFolder());
        Messages.initialize(new File(getDataFolder(), "messages"), getFile(), config.getLang());
        manager = new ChannelManager();
        normalChatLogger = new LunaChatNormalChatLogger("==normalchat");

        // チャンネルチャット無効なら、デフォルト発言先をクリアする(see issue #59)
        if ( !config.isEnableChannelChat() ) {
            manager.removeAllDefaultChannels();
        }

        // Vault のロード
        Plugin temp = getServer().getPluginManager().getPlugin("Vault");
        if ( temp != null ) {
            vaultchat = VaultChatBridge.load();
        }

        // Dynmap のロード
        temp = getServer().getPluginManager().getPlugin("dynmap");
        if ( temp != null ) {
            dynmap = DynmapBridge.load(temp);
            if ( dynmap != null ) {
                getServer().getPluginManager().registerEvents(dynmap, this);
            }
        }

        // MultiverseCore のロード
        temp = getServer().getPluginManager().getPlugin("Multiverse-Core");
        if ( temp != null ) {
            multiverse = MultiverseCoreBridge.load(temp);
        }

        // mcMMOのロード
        if ( getServer().getPluginManager().isPluginEnabled("mcMMO") ) {
            getServer().getPluginManager().registerEvents(new McMMOBridge(), this);
        }

        // リスナーの登録
        getServer().getPluginManager().registerEvents(new LunaChatBukkitEventListener(), this);

        // コマンドの登録
        lunachatCommand = new LunaChatCommand();
        messageCommand = new LunaChatMessageCommand();
        replyCommand = new LunaChatReplyCommand();
        lcjapanizeCommand = new LunaChatJapanizeCommand();

        // ロガーの登録
        pluginLogger = new LunaChatBukkitLogger(getLogger());

        // 期限チェッカータスクの起動
        expireCheckerTask = Bukkit.getAsyncScheduler().runAtFixedRate(
                this, s -> ExpireCheckTask.runTask(), 100, 600, TimeUnit.SECONDS);

        // イベント実行クラスの登録
        LunaChat.setEventSender(new LunaChatBukkitEventSender());

        // プラグインチャンネル登録
        getServer().getMessenger().registerOutgoingPluginChannel(this, LunaChat.PMC_MESSAGE);
    }

    /**
     * プラグインが無効化されたときに呼び出されるメソッド
     * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
     */
    @Override
    public void onDisable() {

        // 期限チェッカータスクの停止
        if ( expireCheckerTask != null ) {
            expireCheckerTask.cancel();
        }
    }

    /**
     * コマンド実行時に呼び出されるメソッド
     * @see org.bukkit.plugin.java.JavaPlugin#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
     */
    @Override
    public boolean onCommand(
            @NotNull CommandSender sender, Command command, @NotNull String label, String @NotNull [] args) {

        if ( command.getName().equals("lunachat") ) {
            return lunachatCommand.execute(ChannelMemberBukkit.getChannelMemberBukkit(sender), label, args);
        } else if ( command.getName().equals("tell") ) {
            return messageCommand.execute(ChannelMemberBukkit.getChannelMemberBukkit(sender), label, args);
        } else if ( command.getName().equals("reply") ) {
            return replyCommand.execute(ChannelMemberBukkit.getChannelMemberBukkit(sender), label, args);
        } else if ( command.getName().equals("japanize") ) {
            return lcjapanizeCommand.execute(ChannelMemberBukkit.getChannelMemberBukkit(sender), label, args);
        }

        return false;
    }

    /**
     * TABキー補完が実行されたときに呼び出されるメソッド
     * @see org.bukkit.plugin.java.JavaPlugin#onTabComplete(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
     */
    @Override
    public List<String> onTabComplete(
            CommandSender sender, Command command, String label, String[] args) {

        List<String> completeList = null;
        if ( command.getName().equals("lunachat") ) {
            completeList = lunachatCommand.onTabComplete(ChannelMemberBukkit.getChannelMemberBukkit(sender), args);
        }
        if ( completeList != null ) {
            return completeList;
        }
        return super.onTabComplete(sender, command, label, args);
    }

    /**
     * LunaChatのインスタンスを返す
     * @return LunaChat
     */
    public static LunaChatBukkit getInstance() {
        if ( instance == null ) {
            instance = (LunaChatBukkit)Bukkit.getPluginManager().getPlugin("LunaChat");
        }
        return instance;
    }

    /**
     * このプラグインのJarファイル自身を示すFileクラスを返す。
     * @return Jarファイル
     */
    public File getPluginJarFile() {
        return getFile();
    }

    /**
     * LunaChatAPIを取得する
     * @return LunaChatAPI
     */
    public LunaChatAPI getLunaChatAPI() {
        return manager;
    }

    /**
     * LunaChatConfigを取得する
     * @return LunaChatConfig
     */
    public LunaChatConfig getLunaChatConfig() {
        return config;
    }

    /**
     * VaultChat連携クラスを返す
     * @return VaultChatBridge
     */
    public VaultChatBridge getVaultChat() {
        return vaultchat;
    }

    /**
     * Dynmap連携クラスを返す
     * @return DynmapBridge
     */
    public DynmapBridge getDynmap() {
        return dynmap;
    }

    /**
     * MultiverseCore連携クラスを返す
     * @return MultiverseCoreBridge
     */
    public MultiverseCoreBridge getMultiverseCore() {
        return multiverse;
    }

    /**
     * 通常チャット用のロガーを返す
     * @return normalChatLogger
     */
    public LunaChatNormalChatLogger getNormalChatLogger() {
        return normalChatLogger;
    }

    @Override
    public LunaChatLogger getPluginLogger() {
        return this.pluginLogger;
    }

    /**
     * 通常チャット用のロガーを設定する
     * @param normalChatLogger normalChatLogger
     */
    protected void setNormalChatLogger(LunaChatNormalChatLogger normalChatLogger) {
        this.normalChatLogger = normalChatLogger;
    }

    /**
     * オンラインのプレイヤー名一覧を取得する
     * @return オンラインのプレイヤー名一覧
     */
    @Override
    public Set<String> getOnlinePlayerNames() {
        Set<String> list = new TreeSet<>();
        for ( Player p : Bukkit.getOnlinePlayers() ) {
            list.add(p.getName());
        }
        return list;
    }

    /**
     * このプラグインのログを記録する
     * @param level ログレベル
     * @param msg ログメッセージ
     */
    @Override
    public void log(Level level, String msg) {
        getLogger().log(level, msg);
    }

    /**
     * UUIDキャッシュデータを取得する
     * @return UUIDキャッシュデータ
     * @see com.github.ucchyocean.lc3.PluginInterface#getUUIDCacheData()
     */
    @Override
    public UUIDCacheData getUUIDCacheData() {
        return uuidCacheData;
    }

    /**
     * 非同期タスクを実行する
     * @param task タスク
     * @see com.github.ucchyocean.lc3.PluginInterface#runAsyncTask(java.lang.Runnable)
     */
    @Override
    public void runAsyncTask(Runnable task) {
        Bukkit.getAsyncScheduler().runNow(this, s -> task.run());
    }

    @Override
    public Channel getChannel(String channelName) {
        return new BukkitChannel(channelName);
    }

    @Override
    public ChannelMember getChannelMember(String playerName) {
        return ChannelMemberBukkit.getChannelMemberBukkit(playerName);
    }

    @Override
    public ChannelMember getChannelMember(UUID uuid) {
        return ChannelMemberBukkit.getChannelMemberBukkit(uuid);
    }

    /**
     * プラグインメッセージを送信する
     * @param bytes 送信内容
     */
    public void sendPluginMessage(byte[] bytes) {
        getServer().sendPluginMessage(this, LunaChat.PMC_MESSAGE, bytes);
    }

    @Override
    public boolean existsOfflinePlayer(String name) {
        return UtilityBukkit.existsOfflinePlayer(name);
    }
}
