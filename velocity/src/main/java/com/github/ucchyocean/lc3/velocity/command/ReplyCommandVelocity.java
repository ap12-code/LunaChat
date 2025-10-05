package com.github.ucchyocean.lc3.velocity.command;

import com.github.ucchyocean.lc3.command.LunaChatReplyCommand;
import com.github.ucchyocean.lc3.velocity.member.ChannelMemberVelocity;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

public class ReplyCommandVelocity implements SimpleCommand {
    private final LunaChatReplyCommand command = new LunaChatReplyCommand();

    @Override
    public void execute(Invocation invocation) {
        if (invocation.source() instanceof Player player) {
            command.execute(ChannelMemberVelocity.getChannelMemberVelocity(player), "r", invocation.arguments());
        } else {
            invocation.source().sendPlainMessage("このコマンドはコンソールからは実行できません");
        }
    }

    public static CommandMeta createMeta(CommandManager manager, Object plugin) {
        return manager.metaBuilder("reply")
                .aliases("r")
                .plugin(plugin)
                .build();
    }
}
