/*
 * @author     ucchy
 * @license    LGPLv3
 * @copyright  Copyright ucchy 2020
 */
package com.github.ucchyocean.lc3.bukkit.member;

import java.util.UUID;

import com.github.ucchyocean.lc3.fabric.member.ChannelMemberOther;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.ucchyocean.lc3.LunaChat;
import com.github.ucchyocean.lc3.bukkit.LunaChatBukkit;
import com.github.ucchyocean.lc3.bukkit.bridge.VaultChatBridge;
import com.github.ucchyocean.lc3.util.BlockLocation;

/**
 * ChannelMemberのBukkitPlayer実装
 * @author ucchy
 */
public class ChannelMemberBukkitPlayer extends ChannelMemberBukkit {

    private final UUID id;

    /**
     * コンストラクタ
     * @param id プレイヤーID
     */
    public ChannelMemberBukkitPlayer(String id) {
        this.id = UUID.fromString(id);
    }

    /**
     * コンストラクタ
     * @param id UUID
     */
    public ChannelMemberBukkitPlayer(UUID id) {
        this.id = id;
    }

    /**
     * プレイヤー名からUUIDを取得してChannelMemberPlayerを作成して返す
     * @param name プレイヤー名
     * @return ChannelMemberPlayer
     */
    public static ChannelMemberBukkitPlayer getChannelMemberPlayerFromName(String name) {
        Player player = Bukkit.getPlayerExact(name);
        if ( player != null ) {
            return new ChannelMemberBukkitPlayer(player.getUniqueId());
        }
        OfflinePlayer offline = Bukkit.getOfflinePlayer(name);
        if ( offline != null && offline.getUniqueId() != null ) {
            return new ChannelMemberBukkitPlayer(offline.getUniqueId());
        }
        return null;
    }

    /**
     * CommandSenderから、ChannelMemberPlayerを作成して返す
     * @param sender CommandSender
     * @return ChannelPlayer
     */
    public static ChannelMemberBukkitPlayer getChannelPlayer(CommandSender sender) {
        if ( sender instanceof Player ) {
            return new ChannelMemberBukkitPlayer(((Player)sender).getUniqueId());
        }
        return new ChannelMemberBukkitPlayer(sender.getName());
    }

    /**
     * オンラインかどうか
     * @return オンラインかどうか
     */
    @Override
    public boolean isOnline() {
        Player player = Bukkit.getPlayer(id);
        return (player != null);
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
        Player player = Bukkit.getPlayer(id);
        if ( player != null ) {
            return player.getName();
        }
        OfflinePlayer offlineplayer = Bukkit.getOfflinePlayer(id);
        return offlineplayer.getName();
    }

    /**
     * プレイヤー表示名を返す
     * @return プレイヤー表示名
     */
    @Override
    public String getDisplayName() {
        Player player = getPlayer();
        if ( player != null ) {
            return player.getName();
        }
        return getName();
    }

    /**
     * プレフィックスを返す
     * @return プレフィックス
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMemberPlayer#getPrefix()
     */
    @Override
    public String getPrefix() {
        VaultChatBridge vault = LunaChatBukkit.getInstance().getVaultChat();
        if ( vault == null ) {
            return "";
        }
        Player player = getPlayer();
        if ( player != null ) {
            return vault.getPlayerPrefix(player);
        }
        return "";
    }

    /**
     * サフィックスを返す
     * @return サフィックス
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMemberPlayer#getSuffix()
     */
    @Override
    public String getSuffix() {
        VaultChatBridge vault = LunaChatBukkit.getInstance().getVaultChat();
        if ( vault == null ) {
            return "";
        }
        Player player = getPlayer();
        if ( player != null ) {
            return vault.getPlayerSuffix(player);
        }
        return "";
    }

    /**
     * メッセージを送る
     * @param message 送るメッセージ
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMemberPlayer#sendMessage(java.lang.String)
     */
    @Override
    public void sendMessage(String message) {
        if ( message == null || message.isEmpty() ) return;
        Player player = getPlayer();
        if ( player != null ) {
            player.sendMessage(message);
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
     * BukkitのPlayerを取得する
     * @return Player
     * @see com.github.ucchyocean.lc.channel.ChannelPlayer#getPlayer()
     */
    @Override
    public Player getPlayer() {
        return Bukkit.getPlayer(id);
    }

    /**
     * 発言者が今いるワールドを取得する
     * @return 発言者が今いるワールド
     * @see ChannelMemberBukkit#getWorld()
     */
    @Override
    public World getWorld() {
        Player player = getPlayer();
        if ( player != null ) return player.getWorld();
        return null;
    }

    /**
     * 発言者が今いるワールドのワールド名を取得する
     * @return ワールド名
     * @see com.github.ucchyocean.lc.channel.ChannelPlayer#getWorldName()
     */
    @Override
    public String getWorldName() {
        World world = getWorld();
        if ( world == null ) return "";
        if ( LunaChatBukkit.getInstance().getMultiverseCore() != null ) {
            return LunaChatBukkit.getInstance().getMultiverseCore().getWorldAlias(world.getName());
        }
        return world.getName();
    }

    /**
     * 発言者が今いる位置を取得する
     * @return 発言者の位置
     * @see com.github.ucchyocean.lc.channel.ChannelPlayer#getLocation()
     */
    @Override
    public Location getLocation() {
        Player player = getPlayer();
        if ( player != null ) {
            return player.getLocation();
        }
        return null;
    }

    /**
     * 指定されたパーミッションノードの権限を持っているかどうかを取得する
     * @param node パーミッションノード
     * @return 権限を持っているかどうか
     * @see com.github.ucchyocean.lc.channel.ChannelPlayer#hasPermission(java.lang.String)
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
     * @see com.github.ucchyocean.lc.channel.ChannelPlayer#isPermissionSet(java.lang.String)
     */
    @Override
    public boolean isPermissionSet(String node) {
        Player player = getPlayer();
        if ( player == null ) {
            return false;
        } else {
            return player.isPermissionSet(node);
        }
    }

    /**
     * 指定されたメッセージの内容を発言する
     * @param message メッセージ
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#chat(java.lang.String)
     */
    public void chat(String message) {
        Player player = getPlayer();
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

    public static ChannelMemberBukkitPlayer getChannelMember(String nameOrUuid) {
        if ( nameOrUuid.startsWith("$") ) {
            return new ChannelMemberBukkitPlayer(nameOrUuid.substring(1));
        } else {
            OfflinePlayer op = Bukkit.getOfflinePlayer(nameOrUuid);
            if ( op == null ) return null;
            return new ChannelMemberBukkitPlayer(op.getUniqueId());
        }
    }

    public ChannelMemberOther toChannelMemberOther() {
        ChannelMemberOther other = new ChannelMemberOther(getName(), getDisplayName(),
                getPrefix(), getSuffix(), getBlockLocation(), id.toString());
        other.setWorldName(getWorldName());
        return other;
    }

    private BlockLocation getBlockLocation() {
        Location loc = getLocation();
        if ( loc == null ) return null;
        return new BlockLocation(getWorldName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }
}
