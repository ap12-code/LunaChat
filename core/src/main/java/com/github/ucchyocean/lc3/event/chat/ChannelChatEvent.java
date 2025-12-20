package com.github.ucchyocean.lc3.event.chat;

import com.github.ucchyocean.lc3.event.LunaChatEvent;

public interface ChannelChatEvent extends LunaChatEvent, ChannelMemberEvent {
    String getPreReplaceMessage();
    String getNgMaskedMessage();
    String getMessageFormat();
    void setNgMaskedMessage(String ngMaskedMessage);
    void setMessageFormat(String messageFormat);
}
