package com.github.ucchyocean.lc3.event.chat;

import com.github.ucchyocean.lc3.event.LunaChatEvent;
import com.github.ucchyocean.lc3.member.ChannelMember;

public interface PostJapanizeEvent extends LunaChatEvent, ChannelMemberEvent {
    String getJapanized();
    void setJapanized(String japanized);
    String getOriginal();
}
