package com.github.ucchyocean.lc3.velocity.member;

import com.github.ucchyocean.lc3.velocity.LunaChatVelocity;
import com.github.ucchyocean.lc3.util.ComponentAdapter;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;

public class ChannelMemberVelocityConsole extends ChannelMemberVelocity {
    private final CommandSource sender;

    /**
     * コンストラクタ
     * @param sender ConsoleのCommandSender
     */
    public ChannelMemberVelocityConsole(CommandSource sender) {
        this.sender = sender;
    }

    /**
     * BungeeのProxiedPlayerを取得する
     *
     * @return 常にnullが返される
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMemberBungee#getPlayer()
     */
    @Override
    public Player getPlayer() {
        return null;
    }

    /**
     * 発言者が今いるサーバーを取得する
     *
     * @return 常にnullが返される
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMemberBungee#getServer()
     */
    @Override
    public RegisteredServer getServer() {
        return null;
    }

    /**
     * 発言者がオンラインかどうかを取得する
     * @return 常にtrueが返される
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#isOnline()
     */
    @Override
    public boolean isOnline() {
        return true;
    }

    /**
     * 発言者名を取得する
     * @return 発言者名
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#getName()
     */
    @Override
    public String getName() {
        return "CONSOLE";
    }

    /**
     * 発言者の表示名を取得する
     * @return 発言者の表示名
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#getDisplayName()
     */
    @Override
    public String getDisplayName() {
        return "CONSOLE";
    }

    /**
     * プレフィックスを返す
     * @return 常に空文字列
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#getPrefix()
     */
    @Override
    public String getPrefix() {
        return null;
    }

    /**
     * サフィックスを返す
     * @return 常に空文字列
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#getSuffix()
     */
    @Override
    public String getSuffix() {
        return null;
    }

    /**
     * 発言者にメッセージを送信する
     * @param message メッセージ
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#sendMessage(java.lang.String)
     */
    @Override
    public void sendMessage(String message) {
        if ( message == null || message.isEmpty() ) return;
        sender.sendMessage(ComponentAdapter.legacy(message));
    }

    /**
     * メッセージを送る
     * @param message 送るメッセージ
     */
    @Override
    public void sendMessage(Component message) {
        sender.sendMessage(message);
    }

    /**
     * 指定されたパーミッションノードの権限を持っているかどうかを取得する
     * @param node パーミッションノード
     * @return 権限を持っているかどうか
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#hasPermission(java.lang.String)
     */
    @Override
    public boolean hasPermission(String node) {
        return sender.hasPermission(node);
    }

    /**
     * 指定されたパーミッションノードが定義されているかどうかを取得する
     * @param node パーミッションノード
     * @return 定義を持っているかどうか
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#isPermissionSet(java.lang.String)
     */
    @Override
    public boolean isPermissionSet(String node) {
        return sender.hasPermission(node);
    }

    /**
     * 指定されたメッセージの内容を発言する
     * @param message メッセージ
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#chat(java.lang.String)
     */
    public void chat(String message) {
        LunaChatVelocity.getInstance().getProxy().sendMessage(ComponentAdapter.legacy("<" + getName() + ">" + message));
    }

    /**
     * IDを返す
     * @return 名前をそのまま返す
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#toString()
     */
    @Override
    public String toString() {
        return getName();
    }
}
