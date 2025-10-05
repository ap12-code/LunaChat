/*
 * @author     ucchy
 * @license    LGPLv3
 * @copyright  Copyright ucchy 2020
 */
package com.github.ucchyocean.lc3.velocity.member;

import com.github.ucchyocean.lc3.LunaChat;
import com.github.ucchyocean.lc3.fabric.member.ChannelMember;
import com.github.ucchyocean.lc3.velocity.LunaChatVelocity;
import com.github.ucchyocean.lc3.bridge.LuckPermsBridge;
import com.github.ucchyocean.lc3.util.ComponentAdapter;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

/**
 * ChannelMemberのBungeeCord-ProxiedPlayer実装
 * @author ucchy
 */
public class ChannelMemberVelocityPlayer extends ChannelMemberVelocity {

    private final UUID id;

    /**
     * コンストラクタ
     * @param id プレイヤーID
     */
    public ChannelMemberVelocityPlayer(String id) {
        this.id = UUID.fromString(id);
    }

    /**
     * コンストラクタ
     * @param id UUID
     */
    public ChannelMemberVelocityPlayer(UUID id) {
        this.id = id;
    }

    /**
     * プレイヤー名からUUIDを取得してChannelMemberProxiedPlayerを作成して返す
     * @param nameOrUuid 名前、または、"$" + UUID
     * @return ChannelMemberProxiedPlayer
     */
    public static ChannelMemberVelocityPlayer getChannelMember(String nameOrUuid) {
        if ( nameOrUuid.startsWith("$") ) {
            return new ChannelMemberVelocityPlayer(UUID.fromString(nameOrUuid.substring(1)));
        } else {
            Optional<Player> player = LunaChatVelocity.getInstance().getProxy().getPlayer(nameOrUuid);
            if (player.isPresent()) return new ChannelMemberVelocityPlayer(player.get().getUniqueId());
        }
        return null;
    }

    /**
     * オンラインかどうか
     * @return オンラインかどうか
     */
    @Override
    public boolean isOnline() {
        return (LunaChatVelocity.getInstance().getProxy().getPlayer(id).isPresent());
    }

    /**
     * プレイヤー名を返す
     * @return プレイヤー名
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#getName()
     */
    @Override
    public String getName() {
        String cache = LunaChat.getUUIDCacheData().get(id.toString());
        if ( cache != null ) {
            return cache;
        }
        Player player = getPlayer();
        if ( player != null ) {
            return player.getUsername();
        }
        return id.toString();
    }

    /**
     * プレイヤー表示名を返す
     * @return プレイヤー表示名
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#getDisplayName()
     */
    @Override
    public String getDisplayName() {
        Player player = getPlayer();
        if ( player != null ) {
            return player.getUsername();
        }
        return getName();
    }

    /**
     * プレフィックスを返す
     * @return プレフィックス
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#getPrefix()
     */
    @Override
    public String getPrefix() {

        LuckPermsBridge luckperms = LunaChatVelocity.getInstance().getLuckPerms();
        if ( luckperms != null ) {
            return luckperms.getPlayerPrefix(id);
        }
        return "";
    }

    /**
     * サフィックスを返す
     * @return サフィックス
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#getSuffix()
     */
    @Override
    public String getSuffix() {


        LuckPermsBridge luckperms = LunaChatVelocity.getInstance().getLuckPerms();
        if ( luckperms != null ) {
            return luckperms.getPlayerPrefix(id);
        }
        return "";
    }

    /**
     * メッセージを送る
     * @param message 送るメッセージ
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#sendMessage(String)
     */
    @Override
    public void sendMessage(String message) {
        if ( message == null || message.isEmpty() ) return;
        Player player = getPlayer();
        if ( player != null ) {
            player.sendMessage(ComponentAdapter.legacy(message));
        }
    }
    /**
     * メッセージを送る
     * @param message 送るメッセージ
     */
    public void sendMessage(Component message) {
        if ( message == null ) return;
        Player player = getPlayer();
        if ( player != null ) {
            player.sendMessage(message);
        }
    }

    /**
     * 指定されたパーミッションノードの権限を持っているかどうかを取得する
     * @param node パーミッションノード
     * @return 権限を持っているかどうか
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#hasPermission(String)
     */
    @Override
    public boolean hasPermission(String node) {
        Player player = getPlayer();
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
     * @see ChannelMember#isPermissionSet(String)
     */
    @Override
    public boolean isPermissionSet(String node) {
        Player player = getPlayer();
        if ( player != null ) {
            return player.hasPermission(node);
        }
        return false;
    }

    /**
     * 指定されたメッセージの内容を発言する
     * @param message メッセージ
     * @see ChannelMember#chat(String)
     */
    public void chat(String message) {
        Player player = getPlayer();
        if ( player != null ) {
            player.spoofChatInput(message);
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
    public @Nullable Player getPlayer() {
        return LunaChatVelocity.getInstance().getProxy().getPlayer(id).orElse(null);
    }

    /**
     * 発言者が今いるサーバーを取得する
     * @return サーバー
     * @see ChannelMemberBungee#getServer()
     */
    @Override
    public @Nullable RegisteredServer getServer() {
        Player player = getPlayer();
        if ( player != null ) {
            Optional<ServerConnection> currentServer = player.getCurrentServer();
            if (currentServer.isPresent()) {
                return currentServer.get().getServer();
            }
        }
        return null;
    }
}
