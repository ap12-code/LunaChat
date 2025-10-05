/*
 * @author     ucchy
 * @license    LGPLv3
 * @copyright  Copyright ucchy 2020
 */
package com.github.ucchyocean.lc3.bungee.command;

import com.github.ucchyocean.lc3.bungee.member.ChannelMemberBungee;
import com.github.ucchyocean.lc3.command.LunaChatReplyCommand;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * Replyコマンドの処理クラス（Bungee実装）
 * @author ucchy
 */
public class ReplyCommandBungee extends Command {

    private LunaChatReplyCommand command;

    /**
     * コンストラクタ
     * @param name
     * @param permission
     * @param aliases
     */
    public ReplyCommandBungee(String name, String permission, String... aliases) {
        super(name, permission, aliases);
        command = new LunaChatReplyCommand();
    }

    /**
     * コマンドを実行したときに呼び出されるメソッド
     * @param sender 実行者
     * @param args 実行されたコマンドの引数
     * @see net.md_5.bungee.api.plugin.Command#execute(net.md_5.bungee.api.CommandSender, java.lang.String[])
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        command.execute(ChannelMemberBungee.getChannelMemberBungee(sender), "r", args);
    }
}
