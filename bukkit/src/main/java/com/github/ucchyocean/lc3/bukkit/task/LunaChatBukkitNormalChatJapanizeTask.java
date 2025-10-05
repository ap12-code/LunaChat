/*
 * @author     ucchy
 * @license    LGPLv3
 * @copyright  Copyright ucchy 2020
 */
package com.github.ucchyocean.lc3.bukkit.task;

import com.github.ucchyocean.lc3.bukkit.LunaChatBukkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.ucchyocean.lc3.LunaChat;
import com.github.ucchyocean.lc3.fabric.channel.JapanizeConvertTask;
import com.github.ucchyocean.lc3.japanize.JapanizeType;
import com.github.ucchyocean.lc3.fabric.member.ChannelMember;
import com.github.ucchyocean.lc3.bukkit.member.ChannelMemberBukkitPlayer;

/**
 * Japanize2行表示のときに、変換結果を遅延して通常チャットに表示するためのタスク
 * @author ucchy
 */
public class LunaChatBukkitNormalChatJapanizeTask extends BukkitRunnable {

    private ChannelMember player;
    private AsyncPlayerChatEvent event;

    private JapanizeConvertTask task;

    /**
     * コンストラクタ
     * @param org 変換前の文字列
     * @param type 変換タイプ
     * @param player 発言したプレイヤー
     * @param japanizeFormat 変換後に発言するときの、発言フォーマット
     * @param event イベント
     */
    public LunaChatBukkitNormalChatJapanizeTask(String org, JapanizeType type,
                                                ChannelMember player, String japanizeFormat, final AsyncPlayerChatEvent event) {

        task = new JapanizeConvertTask(org, type, japanizeFormat, null, player);
        this.player = player;
        this.event = event;
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {

        if ( task.runSync() ) {

            String result = task.getResult();

            // 送信
            for ( Player p : event.getRecipients() ) {
                p.sendMessage(result);
            }
            Bukkit.getConsoleSender().sendMessage(result);

            // 設定に応じてdynmapへ送信する
            if ( LunaChat.getConfig().isSendBroadcastChannelChatToDynmap() &&
                    LunaChatBukkit.getInstance().getDynmap() != null ) {
                if ( player != null && player instanceof ChannelMemberBukkitPlayer
                        && ((ChannelMemberBukkitPlayer)player).getPlayer() != null )
                    LunaChatBukkit.getInstance().getDynmap().chat(((ChannelMemberBukkitPlayer)player).getPlayer(), result);
                else
                    LunaChatBukkit.getInstance().getDynmap().broadcast(result);
            }
        }
    }
}
