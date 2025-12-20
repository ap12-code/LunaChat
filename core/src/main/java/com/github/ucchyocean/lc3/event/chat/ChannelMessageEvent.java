package com.github.ucchyocean.lc3.event.chat;

import com.github.ucchyocean.lc3.event.LunaChatEvent;
import com.github.ucchyocean.lc3.member.ChannelMember;

import java.util.List;

public interface ChannelMessageEvent extends LunaChatEvent, ChannelMemberEvent {
    /**
     * 置き換えされたメッセージ
     * @return message メッセージ
     */
    String getMessage();

    /**
     * メッセージを受信するプレイヤーリスト
     * @return recipients プレイヤーリスト
     */
    List<ChannelMember> getRecipients();

    /**
     * 発言者の表示名を取得する
     * @return 発言者の表示名
     */
    String getDisplayName();

    /**
     * オリジナルメッセージ（チャットフォーマットを適用していない状態のメッセージ）を取得する
     * @return オリジナルメッセージ
     */
    String getOriginalMessage();

    /**
     * メッセージを上書き設定する
     * @param message メッセージ
     */
    void setMessage(String message);

    /**
     * メッセージ受信者を上書き設定する
     * @param recipients メッセージ受信者
     */
    void setRecipients(List<ChannelMember> recipients);
}
