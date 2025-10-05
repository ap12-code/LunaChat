/*
 * @author     ucchy
 * @license    LGPLv3
 * @copyright  Copyright ucchy 2020
 */
package com.github.ucchyocean.lc3.bukkit.member;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.entity.Player;


/**
 * ChannelMemberのBukkit-BlockCommandSender実装
 * @author ucchy
 */
public class ChannelMemberBlock extends ChannelMemberBukkit {

    BlockCommandSender sender;

    /**
     * コンストラクタ
     * @param sender コマンドブロック
     */
    public ChannelMemberBlock(BlockCommandSender sender) {
        this.sender = sender;
    }

    /**
     * オンラインかどうか
     * @return 常にtrue
     */
    @Override
    public boolean isOnline() {
        return true;
    }

    /**
     * プレイヤー名を返す
     * @return プレイヤー名
     */
    @Override
    public String getName() {
        return sender.getName();
    }

    /**
     * プレイヤー表示名を返す
     * @return プレイヤー表示名
     */
    @Override
    public String getDisplayName() {
        return sender.getName();
    }

    /**
     * プレフィックスを返す
     * @return 常に空文字列
     */
    @Override
    public String getPrefix() {
        return "";
    }

    /**
     * サフィックスを返す
     * @return 常に空文字列
     */
    @Override
    public String getSuffix() {
        return "";
    }

    /**
     * メッセージを送る、実際は何もせずにメッセージを捨てる
     * @param message メッセージ
     */
    @Override
    public void sendMessage(String message) {
        // do nothing.
    }

    /**
     * メッセージを送る、実際は何もせずにメッセージを捨てる
     * @param message 送るメッセージ
     */
    public void sendMessage(Component message) {
        // do nothing.
    }

    /**
     * BukkitのPlayerを取得する
     * @return 常にnullが返される
     */
    @Override
    public Player getPlayer() {
        return null;
    }

    /**
     * 発言者が今いるワールドのワールド名を取得する
     * @return 常に空文字列
     */
    @Override
    public String getWorldName() {
        return "";
    }

    /**
     * 発言者が今いる位置を取得する
     * @return 発言者の位置
     */
    @Override
    public Location getLocation() {
        if ( sender != null ) {
            return sender.getBlock().getLocation();
        }
        return null;
    }

    /**
     * 指定されたパーミッションノードの権限を持っているかどうかを取得する
     * @param node パーミッションノード
     * @return 権限を持っているかどうか
     */
    @Override
    public boolean hasPermission(String node) {
        return sender.hasPermission(node);
    }

    /**
     * 指定されたパーミッションノードが定義されているかどうかを取得する
     * @param node パーミッションノード
     * @return 定義を持っているかどうか
     */
    @Override
    public boolean isPermissionSet(String node) {
        return sender.isPermissionSet(node);
    }

    /**
     * 指定されたメッセージの内容を発言する
     * @param message メッセージ
     */
    public void chat(String message) {
        Bukkit.broadcast(Component.text("<" + getName() + "> " + message));
    }

    /**
     * IDを返す
     * @return 名前をそのまま返す
     */
    @Override
    public String toString() {
        return getName();
    }

    @Override
    public World getWorld() {
        return sender.getBlock().getWorld();
    }

    public BlockCommandSender getBlockCommandSender() {
        return sender;
    }
}
