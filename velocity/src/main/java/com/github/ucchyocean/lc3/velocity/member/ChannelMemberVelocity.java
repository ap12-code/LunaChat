package com.github.ucchyocean.lc3.velocity.member;

import com.github.ucchyocean.lc3.fabric.member.ChannelMember;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;

public abstract class ChannelMemberVelocity extends ChannelMember {
    public abstract Player getPlayer();

    /**
     * 発言者が今いるサーバーを取得する
     * @return サーバー
     */
    public abstract RegisteredServer getServer();

    /**
     * 発言者が今いるサーバーのサーバー名を取得する
     * @return サーバー名
     */
    public String getServerName() {
        RegisteredServer server = getServer();
        if ( server != null ) {
            return server.getServerInfo().getName();
        }
        return "";
    }

    /**
     * 発言者が今いるワールド名を返す
     * @return 常に空文字列が返される
     * @see com.github.ucchyocean.lc3.fabric.member.ChannelMember#getWorldName()
     */
    @Override
    public String getWorldName() {
        return "";
    }

    /**
     * CommandSenderから、ChannelMemberを作成して返す
     * @param sender Object
     * @return ChannelMember
     */
    public static ChannelMemberVelocity getChannelMemberVelocity(Object sender) {
        if (!(sender instanceof CommandSource)) return null;
        if ( sender instanceof Player ) {
            return new ChannelMemberVelocityPlayer(((Player) sender).getUniqueId());
        } else {
            // ProxiedPlayer以外のCommandSenderは、ConsoleSenderしかないはず
            return new ChannelMemberVelocityConsole((CommandSource)sender);
        }
    }
}
