package com.github.ucchyocean.lc3.velocity;

import com.github.ucchyocean.lc3.*;
import com.github.ucchyocean.lc3.bridge.LuckPermsBridge;
import com.github.ucchyocean.lc3.fabric.channel.Channel;
import com.github.ucchyocean.lc3.fabric.channel.ChannelManager;
import com.github.ucchyocean.lc3.fabric.member.ChannelMember;
import com.github.ucchyocean.lc3.velocity.channel.VelocityChannel;
import com.github.ucchyocean.lc3.velocity.command.JapanizeCommandVelocity;
import com.github.ucchyocean.lc3.velocity.command.LunaChatCommandVelocity;
import com.github.ucchyocean.lc3.velocity.command.MessageCommandVelocity;
import com.github.ucchyocean.lc3.velocity.command.ReplyCommandVelocity;
import com.github.ucchyocean.lc3.velocity.member.ChannelMemberVelocityPlayer;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Plugin(id = "lunachat", name = "LunaChat", version = "3.1.0", description = "LunaChat", authors = {"ucchyocean"})
public class LunaChatVelocity implements PluginInterface {
    private static LunaChatVelocity instance;

    private LunaChatConfig config;
    private ChannelManager manager;
    private UUIDCacheData uuidCacheData;
    private LunaChatNormalChatLogger normalChatLogger;
    private LunaChatLogger pluginLogger;
    private LuckPermsBridge luckperms;

    @Inject
    private Logger logger;

    @Inject
    private ProxyServer proxy;

    @Inject
    @DataDirectory
    private Path dataDirectory;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        LunaChat.setPlugin(this);
        LunaChat.setMode(LunaChatMode.VELOCITY);

        // 初期化
        config = new LunaChatConfig(getDataFolder(), getFile());
        uuidCacheData = new UUIDCacheData(getDataFolder());
        Messages.initialize(new File(getDataFolder(), "messages"), getFile(), config.getLang());

        manager = new ChannelManager();
        pluginLogger = new LunaChatVelocityLogger(this.logger);
        normalChatLogger = new LunaChatNormalChatLogger("==normalchat");

        // チャンネルチャット無効なら、デフォルト発言先をクリアする
        if ( !config.isEnableChannelChat() ) {
            manager.removeAllDefaultChannels();
        }

        // LuckPermsのロード
        Optional<PluginContainer> temp = getProxy().getPluginManager().getPlugin("luckperms");
        temp.ifPresent(pluginContainer -> luckperms = LuckPermsBridge.load(pluginContainer));

        // コマンド登録
        CommandManager manager = getProxy().getCommandManager();
        manager.register(LunaChatCommandVelocity.createMeta(manager, this), new LunaChatCommandVelocity());
        manager.register(MessageCommandVelocity.createMeta(manager, this), new MessageCommandVelocity());
        manager.register(ReplyCommandVelocity.createMeta(manager, this), new ReplyCommandVelocity());
        manager.register(JapanizeCommandVelocity.createMeta(manager, this), new JapanizeCommandVelocity());

        // リスナー登録
        getProxy().getEventManager().register(this, new LunaChatVelocityEventListener(this));

        // イベント実行クラスの登録
        LunaChat.setEventSender(new LunaChatVelocityEventSender());

        // プラグインチャンネル登録
        getProxy().getChannelRegistrar().register(MinecraftChannelIdentifier.from(LunaChat.PMC_MESSAGE));
        instance = this;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public ProxyServer getProxy() {
        return this.proxy;
    }

    public File getFile() {
        try {
            return new File(LunaChatVelocity.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            return dataDirectory.resolve("../LunaChat.jar").toFile();
        }
    }

    public static LunaChatVelocity getInstance() {
        return instance;
    }

    @Override
    public File getPluginJarFile() {
        return getFile();
    }

    @Override
    public LunaChatConfig getLunaChatConfig() {
        return config;
    }

    @Override
    public LunaChatAPI getLunaChatAPI() {
        return manager;
    }

    @Override
    public File getDataFolder() {
        if (Files.notExists(dataDirectory)) {
            try {
                Files.createDirectory(dataDirectory);
            } catch (IOException e) {
                proxy.shutdown();
            }
        }
        return dataDirectory.toFile();
    }

    @Override
    public LunaChatNormalChatLogger getNormalChatLogger() {
        return this.normalChatLogger;
    }

    @Override
    public LunaChatLogger getPluginLogger() {
        return this.pluginLogger;
    }

    @Override
    public Set<String> getOnlinePlayerNames() {
        return this.proxy.getAllPlayers().stream().map(Player::getUsername).collect(Collectors.toSet());
    }

    @Override
    public void log(Level level, String msg) {
        if (level.getName().equals("WARNING")) {
            logger.warn(msg);
        } else {
            logger.info(msg);
        }
    }

    @Override
    public UUIDCacheData getUUIDCacheData() {
        return uuidCacheData;
    }

    @Override
    public void runAsyncTask(Runnable task) {
        this.proxy.getScheduler().buildTask(this, task).schedule();
    }

    @Override
    public Channel getChannel(String channelName) {
        return new VelocityChannel(channelName);
    }

    @Override
    public ChannelMember getChannelMember(String playerName) {
        return ChannelMemberVelocityPlayer.getChannelMember(playerName);
    }

    @Override
    public ChannelMember getChannelMember(UUID uuid) {
        return ChannelMemberVelocityPlayer.getChannelMember(uuid.toString());
    }

    /**
     * LuckPerms連携クラスを取得する
     * @return LuckPerms連携クラス
     */
    public LuckPermsBridge getLuckPerms() {
        return luckperms;
    }
}
