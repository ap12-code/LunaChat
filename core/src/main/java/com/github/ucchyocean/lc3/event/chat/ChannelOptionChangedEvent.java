package com.github.ucchyocean.lc3.event.chat;

import com.github.ucchyocean.lc3.event.LunaChatEvent;

import java.util.Map;

public interface ChannelOptionChangedEvent extends LunaChatEvent, ChannelMemberEvent {
    Map<String, String> getOptions();
    void setOptions(Map<String, String> options);
}
