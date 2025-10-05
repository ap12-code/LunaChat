package com.github.ucchyocean.lc3.velocity.command;

import com.github.ucchyocean.lc3.command.LunaChatMessageCommand;
import com.github.ucchyocean.lc3.velocity.member.ChannelMemberVelocity;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MessageCommandVelocity implements SimpleCommand {
    private final LunaChatMessageCommand command = new LunaChatMessageCommand();

    @Override
    public void execute(Invocation invocation) {
        if (invocation.source() instanceof Player player) {
            command.execute(ChannelMemberVelocity.getChannelMemberVelocity(player), "m", invocation.arguments());
        } else {
            invocation.source().sendPlainMessage("このコマンドはコンソールからは実行できません");
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        if (invocation.source() instanceof Player player) {
            return command.onTabComplete(ChannelMemberVelocity.getChannelMemberVelocity(player), "m", invocation.arguments());
        }
        return List.of();
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        return CompletableFuture.completedFuture(this.suggest(invocation));
    }


    public static CommandMeta createMeta(CommandManager manager, Object plugin) {
        return manager.metaBuilder("tell")
                .plugin(plugin)
                .aliases("msg", "message", "m", "t", "w")
                .build();
    }
}
