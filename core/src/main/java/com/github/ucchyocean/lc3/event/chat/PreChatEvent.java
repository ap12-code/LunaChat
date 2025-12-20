package com.github.ucchyocean.lc3.event.chat;

import com.github.ucchyocean.lc3.channel.Channel;
import com.github.ucchyocean.lc3.event.LunaChatEvent;

public interface PreChatEvent extends LunaChatEvent, ChannelMemberEvent {
    String getMessage();
    void setMessage(String message);

    void setChannelName(String channelName);
    void setChannel(Channel channel);
}
