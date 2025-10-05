/*
 * @author     ucchy
 * @license    LGPLv3
 * @copyright  Copyright ucchy 2020
 */
package com.github.ucchyocean.lc3.bukkit;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;

import com.github.ucchyocean.lc3.bukkit.event.LunaChatBukkitChannelChatEvent;
import com.github.ucchyocean.lc3.bukkit.event.LunaChatBukkitChannelCreateEvent;
import com.github.ucchyocean.lc3.bukkit.event.LunaChatBukkitChannelMemberChangedEvent;
import com.github.ucchyocean.lc3.bukkit.event.LunaChatBukkitChannelMessageEvent;
import com.github.ucchyocean.lc3.bukkit.event.LunaChatBukkitChannelOptionChangedEvent;
import com.github.ucchyocean.lc3.bukkit.event.LunaChatBukkitChannelRemoveEvent;
import com.github.ucchyocean.lc3.bukkit.event.LunaChatBukkitPostJapanizeEvent;
import com.github.ucchyocean.lc3.bukkit.event.LunaChatBukkitPreChatEvent;
import com.github.ucchyocean.lc3.event.EventResult;
import com.github.ucchyocean.lc3.event.EventSenderInterface;
import com.github.ucchyocean.lc3.fabric.member.ChannelMember;

/**
 * Bukkitのイベント実行クラス
 * @author ucchy
 */
public class LunaChatBukkitEventSender implements EventSenderInterface {

    /**
     * チャンネルチャットのチャットイベント
     * @param channelName チャンネル名
     * @param originalMessage 発言内容
     * @param ngMaskedMessage 発言内容（NGマスク後）
     * @param messageFormat 発言に適用されるフォーマット
     * @return イベント実行結果
     * @see com.github.ucchyocean.lc3.event.EventSenderInterface#sendLunaChatChannelChatEvent(java.lang.String, com.github.ucchyocean.lc3.fabric.member.ChannelMember, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public EventResult sendLunaChatChannelChatEvent(String channelName, ChannelMember member, String originalMessage,
            String ngMaskedMessage, String messageFormat) {

        LunaChatBukkitChannelChatEvent event =
                new LunaChatBukkitChannelChatEvent(
                        channelName, member, originalMessage, ngMaskedMessage, messageFormat);
        Bukkit.getPluginManager().callEvent(event);


        EventResult result = new EventResult();
        result.setCancelled(event.isCancelled());
        result.setNgMaskedMessage(event.getNgMaskedMessage());
        result.setMessageFormat(event.getMessageFormat());
        return result;
    }

    /**
     * チャンネル作成イベント
     * @param channelName チャンネル名
     * @param member 作成した人
     * @return イベント実行結果
     * @see com.github.ucchyocean.lc3.event.EventSenderInterface#sendLunaChatChannelCreateEvent(java.lang.String, com.github.ucchyocean.lc3.fabric.member.ChannelMember)
     */
    @Override
    public EventResult sendLunaChatChannelCreateEvent(String channelName, ChannelMember member) {

        LunaChatBukkitChannelCreateEvent event =
                new LunaChatBukkitChannelCreateEvent(channelName, member);
        Bukkit.getPluginManager().callEvent(event);

        EventResult result = new EventResult();
        result.setCancelled(event.isCancelled());
        result.setChannelName(event.getChannelName());
        return result;
    }

    /**
     * メンバー変更イベント
     * @param channelName チャンネル名
     * @param before 変更前のメンバー
     * @param after 変更後のメンバー
     * @return イベント実行結果
     * @see com.github.ucchyocean.lc3.event.EventSenderInterface#sendLunaChatChannelMemberChangedEvent(java.lang.String, java.util.List, java.util.List)
     */
    @Override
    public EventResult sendLunaChatChannelMemberChangedEvent(String channelName, List<ChannelMember> before,
            List<ChannelMember> after) {

        LunaChatBukkitChannelMemberChangedEvent event =
                new LunaChatBukkitChannelMemberChangedEvent(channelName, before, after);
        Bukkit.getPluginManager().callEvent(event);

        EventResult result = new EventResult();
        result.setCancelled(event.isCancelled());
        return result;
    }

    /**
     * チャンネルチャットのメッセージイベント。このイベントはキャンセルできない。
     * @param channelName チャンネル名
     * @param member 発言者
     * @param message 発言内容（NGマスクやJapanizeされた後の内容）
     * @param recipients 受信者
     * @param displayName 発言者の表示名
     * @param originalMessage 発言内容（元々の内容）
     * @return イベント実行結果
     * @see com.github.ucchyocean.lc3.event.EventSenderInterface#sendLunaChatChannelChatEvent(String, ChannelMember, String, String, String) 
     */
    @Override
    public EventResult sendLunaChatChannelMessageEvent(String channelName, ChannelMember member, String message,
            List<ChannelMember> recipients, String displayName, String originalMessage) {

        LunaChatBukkitChannelMessageEvent event =
                new LunaChatBukkitChannelMessageEvent(channelName, member, message, recipients, displayName, originalMessage);
        Bukkit.getPluginManager().callEvent(event);


        EventResult result = new EventResult();
        result.setMessage(event.getMessage());
        result.setRecipients(event.getRecipients());
        return result;
     }

    /**
     * オプション変更イベント
     * @param channelName チャンネル名
     * @param member オプションを変更した人
     * @param options 変更後のオプション
     * @return イベント実行結果
     * @see com.github.ucchyocean.lc3.event.EventSenderInterface#sendLunaChatChannelOptionChangedEvent(String, ChannelMember, Map)
     */
    @Override
    public EventResult sendLunaChatChannelOptionChangedEvent(String channelName, ChannelMember member,
            Map<String, String> options) {

        LunaChatBukkitChannelOptionChangedEvent event = new LunaChatBukkitChannelOptionChangedEvent(channelName, member, options);
        Bukkit.getPluginManager().callEvent(event);

        EventResult result = new EventResult();
        result.setCancelled(event.isCancelled());
        result.setOptions(event.getOptions());

        return result;
    }

    /**
     * チャンネル削除イベント
     * @param channelName チャンネル名
     * @param member 削除を実行した人
     * @return イベント実行結果
     * @see com.github.ucchyocean.lc3.event.EventSenderInterface#sendLunaChatChannelRemoveEvent(java.lang.String, com.github.ucchyocean.lc3.fabric.member.ChannelMember)
     */
    @Override
    public EventResult sendLunaChatChannelRemoveEvent(String channelName, ChannelMember member) {

        LunaChatBukkitChannelRemoveEvent event = new LunaChatBukkitChannelRemoveEvent(channelName, member);
        Bukkit.getPluginManager().callEvent(event);

        EventResult result = new EventResult();
        result.setCancelled(event.isCancelled());
        result.setChannelName(event.getChannelName());

        return result;
    }

    /**
     * Japanize変換が行われた後に呼び出されるイベント
     * @param channelName チャンネル名
     * @param member 発言したメンバー
     * @param original 変換前の文字列
     * @param japanized 変換後の文字列
     * @return イベント実行結果
     * @see com.github.ucchyocean.lc3.event.EventSenderInterface#sendLunaChatPostJapanizeEvent(java.lang.String, com.github.ucchyocean.lc3.fabric.member.ChannelMember, java.lang.String, java.lang.String)
     */
    @Override
    public EventResult sendLunaChatPostJapanizeEvent(String channelName, ChannelMember member, String original,
            String japanized) {

        LunaChatBukkitPostJapanizeEvent event = new LunaChatBukkitPostJapanizeEvent(channelName, member, original, japanized);

        Bukkit.getPluginManager().callEvent(event);
        EventResult result = new EventResult();
        result.setCancelled(event.isCancelled());
        result.setJapanized(event.getJapanized());

        return result;
    }

    /**
     * チャンネルチャットへの発言前に発生するイベント
     * @param channelName チャンネル名
     * @param member 発言したメンバー
     * @param message 発言内容
     * @return イベント実行結果
     * @see com.github.ucchyocean.lc3.event.EventSenderInterface#sendLunaChatPreChatEvent(java.lang.String, com.github.ucchyocean.lc3.fabric.member.ChannelMember, java.lang.String)
     */
    @Override
    public EventResult sendLunaChatPreChatEvent(String channelName, ChannelMember member, String message) {

        LunaChatBukkitPreChatEvent event =
                new LunaChatBukkitPreChatEvent(channelName, member, message);
        Bukkit.getPluginManager().callEvent(event);


        EventResult result = new EventResult();
        result.setCancelled(event.isCancelled());
        result.setMessage(event.getMessage());
        return result;
    }
}
