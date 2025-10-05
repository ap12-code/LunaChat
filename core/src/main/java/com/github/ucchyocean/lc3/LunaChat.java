/*
 * @author     ucchy
 * @license    LGPLv3
 * @copyright  Copyright ucchy 2020
 */
package com.github.ucchyocean.lc3;

import java.io.File;
import java.util.UUID;

import com.github.ucchyocean.lc3.fabric.channel.Channel;
import com.github.ucchyocean.lc3.event.EventSenderInterface;
import com.github.ucchyocean.lc3.fabric.member.ChannelMember;

/**
 * LunaChat
 * @author ucchy
 */
@SuppressWarnings("unused")
public class LunaChat {

    /** Bukkit→BungeeCord チャット発言内容の転送に使用するプラグインメッセージチャンネル名 */
    public static final String PMC_MESSAGE = "lunachat:message";

    private static PluginInterface instance;
    private static LunaChatMode mode;
    private static EventSenderInterface eventSender;

    // LunaChatに実行元プラグインクラスを設定する
    public static void setPlugin(PluginInterface plugin) {
        instance = plugin;
    }

    /**
     * LunaChatのプラグインクラスを取得する
     * @return プラグインクラス、BukkitモードならLunaChatBukkit、BungeeCordモードならLunaChatBungee
     */
    public static PluginInterface getPlugin() {
        return instance;
    }

    // LunaChatの実行モードを設定する
    public static void setMode(LunaChatMode _mode) {
        mode = _mode;
    }

    /**
     * LunaChatの実行モードを取得する
     * @return 実行モード（BUKKIT or BUNGEE）
     */
    public static LunaChatMode getMode() {
        return mode;
    }

    // LunaChatのイベント実行クラスを取得する
    public static void setEventSender(EventSenderInterface eventSender) {
        LunaChat.eventSender = eventSender;
    }

    /**
     * LunaChatのイベント実行クラスを取得する
     * @return イベント実行クラス
     */
    public static EventSenderInterface getEventSender() {
        return eventSender;
    }

    /**
     * LunaChatのデータ格納フォルダを取得する
     * @return データ格納フォルダ
     */
    public static File getDataFolder() {
        return instance.getDataFolder();
    }

    /**
     * LunaChatのJarファイルを取得する
     * @return Jarファイル
     */
    public static File getPluginJarFile() {
        return instance.getPluginJarFile();
    }

    /**
     * LunaChatのコンフィグを取得する
     * @return コンフィグ
     */
    public static LunaChatConfig getConfig() {
        return instance.getLunaChatConfig();
    }

    /**
     * LunaChatのAPIを取得する
     * @return API
     */
    public static LunaChatAPI getAPI() {
        return instance.getLunaChatAPI();
    }

    /**
     * LunaChatの通常チャットロガーを取得する
     * @return 通常チャットロガー
     */
    public static LunaChatNormalChatLogger getNormalChatLogger() {
        return instance.getNormalChatLogger();
    }

    /**
     * LunaChatのプラグインロガーを取得する
     * @return プラグインロガー
     */
    public static LunaChatLogger getPluginLogger() {
        return instance.getPluginLogger();
    }

    /**
     * LunaChatのプラグインロガーにエラーを記録する
     */
    public static void logError(String e) {
        getPluginLogger().error(e);
    }

    /**
     * LunaChatのプラグインロガーにエラーを記録する
     */
    public static void logError(Throwable e) {
        getPluginLogger().error(e);
    }


    /**
     * LunaChatのUUIDキャッシュを取得する
     * @return UUIDキャッシュ
     */
    public static UUIDCacheData getUUIDCacheData() {
        return instance.getUUIDCacheData();
    }

    /**
     * LunaChatで非同期タスクを実行する
     * @param task 実行するタスク
     */
    public static void runAsyncTask(Runnable task) {
        instance.runAsyncTask(task);
    }

    public static Channel getChannel(String channel) {
        return instance.getChannel(channel);
    }

    public static ChannelMember getChannelMember(String playerName) {
        return instance.getChannelMember(playerName);
    }

    public static ChannelMember getChannelMember(UUID uuid) {
        return instance.getChannelMember(uuid);
    }

    public static boolean existsOfflinePlayer(String name) {
        return instance.existsOfflinePlayer(name);
    }
}