package com.github.ucchyocean.lc3.velocity.command;

import com.github.ucchyocean.lc3.command.LunaChatCommand;
import com.github.ucchyocean.lc3.velocity.member.ChannelMemberVelocity;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LunaChatCommandVelocity implements SimpleCommand {
    private final LunaChatCommand command = new LunaChatCommand();

    @Override
    public void execute(Invocation invocation) {
        if (invocation.source() instanceof Player player) {
            command.execute(ChannelMemberVelocity.getChannelMemberVelocity(player), "ch", invocation.arguments());
        } else {
            invocation.source().sendPlainMessage("このコマンドはコンソールからは実行できません");
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        if (invocation.source() instanceof Player player) {
            return command.onTabComplete(ChannelMemberVelocity.getChannelMemberVelocity(player), invocation.arguments());
        }
        return List.of();
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        return CompletableFuture.completedFuture(this.suggest(invocation));
    }

    public static CommandMeta createMeta(CommandManager manager, Object plugin) {
        return manager.metaBuilder("lunachat")
                .plugin(plugin)
                .aliases("lc", "ch")
                .build();
    }
}
