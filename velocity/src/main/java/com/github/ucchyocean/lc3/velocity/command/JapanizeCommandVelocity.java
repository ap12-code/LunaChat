package com.github.ucchyocean.lc3.velocity.command;

import com.github.ucchyocean.lc3.command.LunaChatJapanizeCommand;
import com.github.ucchyocean.lc3.velocity.member.ChannelMemberVelocity;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

public class JapanizeCommandVelocity implements SimpleCommand {
    private final LunaChatJapanizeCommand command = new LunaChatJapanizeCommand();

    @Override
    public void execute(Invocation invocation) {
        if (invocation.source() instanceof Player player) {
            command.execute(ChannelMemberVelocity.getChannelMemberVelocity(player.getUsername()), "jp", invocation.arguments());
        } else {
            invocation.source().sendPlainMessage("このコマンドはコンソールからは実行できません");
        }
    }

    public static CommandMeta createMeta(CommandManager manager, Object plugin) {
        return manager.metaBuilder("jp")
                .plugin(plugin)
                .build();
    }
}
