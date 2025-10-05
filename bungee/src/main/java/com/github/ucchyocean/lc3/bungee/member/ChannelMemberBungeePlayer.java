/*
 * @author     ucchy
 * @license    LGPLv3
 * @copyright  Copyright ucchy 2020
 */
package com.github.ucchyocean.lc3.bungee.member;

import java.util.UUID;

import com.github.ucchyocean.lc3.bungee.LunaChatBungee;
import com.github.ucchyocean.lc3.bungee.bridge.BungeePermsBridge;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import org.jetbrains.annotations.Nullable;

import com.github.ucchyocean.lc3.LunaChat;
import com.github.ucchyocean.lc3.bridge.LuckPermsBridge;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

/**
 * ChannelMemberのBungeeCord-ProxiedPlayer実装
 * @author ucchy
 */
public class ChannelMemberBungeePlayer extends ChannelMemberBungee {

    private UUID id;

    /**
     * コンストラクタ
     * @param id プレイヤーID
     */
    public ChannelMemberBungeePlayer(String id) {
        this.id = UUID.fromString(id);
    }

    /**
     * コンストラクタ
     * @param id UUID
     */
    public ChannelMemberBungeePlayer(UUID id) {
        this.id = id;
    }

    /**
     * プレイヤー名からUUIDを取得してChannelMemberProxiedPlayerを作成して返す
     * @param nameOrUuid 名前、または、"$" + UUID
     * @return ChannelMemberProxiedPlayer
     */
    public static ChannelMemberBungeePlayer getChannelMember(String nameOrUuid) {
        if ( nameOrUuid.startsWith("$") ) {
            return new ChannelMemberBungeePlayer(UUID.fromString(nameOrUuid.substring(1)));
        } else {
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(nameOrUuid);
            if ( player != null ) return new ChannelMemberBungeePlayer(player.getUniqueId());
        }
        return null;
    }

    /**
     * オンラインかどうか
     * @return オンラインかどうか
     */
    @Override
    public boolean isOnline() {
        return (ProxyServer.getInstance().getPlayer(id) != null);
    }

    /**
     * プレイヤー名を返す
     * @return プレイヤー名
     */
    @Override
    public String getName() {
        String cache = LunaChat.getUUIDCacheData().get(id.toString());
        if ( cache != null ) {
            return cache;
        }
        ProxiedPlayer player = getPlayer();
        if ( player != null ) {
            return player.getName();
        }
        return id.toString();
    }

    /**
     * プレイヤー表示名を返す
     * @return プレイヤー表示名
     */
    @Override
    public String getDisplayName() {
        ProxiedPlayer player = getPlayer();
        if ( player != null ) {
            return player.getDisplayName();
        }
        return getName();
    }

    /**
     * プレフィックスを返す
     * @return プレフィックス
     */
    @Override
    public String getPrefix() {

        LuckPermsBridge luckperms = LunaChatBungee.getInstance().getLuckPerms();
        if ( luckperms != null ) {
            return luckperms.getPlayerPrefix(id);
        }
        BungeePermsBridge bungeeperms = LunaChatBungee.getInstance().getBungeePerms();
        if ( bungeeperms != null ) {
            return bungeeperms.userPrefix(id.toString(), null, null);
        }
        return "";
    }

    /**
     * サフィックスを返す
     * @return サフィックス
     */
    @Override
    public String getSuffix() {

        LuckPermsBridge luckperms = LunaChatBungee.getInstance().getLuckPerms();
        if ( luckperms != null ) {
            return luckperms.getPlayerSuffix(id);
        }
        BungeePermsBridge bungeeperms = LunaChatBungee.getInstance().getBungeePerms();
        if ( bungeeperms != null ) {
            return bungeeperms.userSuffix(id.toString(), null, null);
        }
        return "";
    }

    /**
     * メッセージを送る
     * @param message 送るメッセージ
     */
    @Override
    public void sendMessage(String message) {
        if ( message == null || message.isEmpty() ) return;
        ProxiedPlayer player = getPlayer();
        if ( player != null ) {
            player.sendMessage(TextComponent.fromLegacy(message));
        }
    }
    /**
     * メッセージを送る
     * @param message 送るメッセージ
     */
    public void sendMessage(Component message) {
        if ( message == null ) return;
        ProxiedPlayer player = getPlayer();
        if ( player != null ) {
            player.sendMessage(BungeeComponentSerializer.get().serialize(message));
        }
    }

    /**
     * 指定されたパーミッションノードの権限を持っているかどうかを取得する
     * @param node パーミッションノード
     * @return 権限を持っているかどうか
     */
    @Override
    public boolean hasPermission(String node) {
        ProxiedPlayer player = getPlayer();
        if ( player == null ) {
            return false;
        } else {
            return player.hasPermission(node);
        }
    }

    /**
     * 指定されたパーミッションノードが定義されているかどうかを取得する
     * @param node パーミッションノード
     * @return 定義を持っているかどうか
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#isPermissionSet(java.lang.String)
     */
    @Override
    public boolean isPermissionSet(String node) {
        ProxiedPlayer player = getPlayer();
        if ( player != null ) {
            return player.getPermissions().contains(node);
        }
        return false;
    }

    /**
     * 指定されたメッセージの内容を発言する
     * @param message メッセージ
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#chat(java.lang.String)
     */
    public void chat(String message) {
        ProxiedPlayer player = getPlayer();
        if ( player != null ) {
            player.chat(message);
        }
    }

    /**
     * IDを返す
     * @return "$" + UUID を返す
     */
    @Override
    public String toString() {
        return "$" + id.toString();
    }

    /**
     * ProxiedPlayerを取得して返す
     * @return ProxiedPlayer
     */
    @Override
    public @Nullable ProxiedPlayer getPlayer() {
        return ProxyServer.getInstance().getPlayer(id);
    }

    /**
     * 発言者が今いるサーバーを取得する
     * @return サーバー
     * @see ChannelMemberBungee#getServer()
     */
    @Override
    public @Nullable Server getServer() {
        ProxiedPlayer player = getPlayer();
        if ( player != null ) {
            return player.getServer();
        }
        return null;
    }
}
