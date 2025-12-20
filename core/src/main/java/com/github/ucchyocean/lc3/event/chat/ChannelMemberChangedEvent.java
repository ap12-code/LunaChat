package com.github.ucchyocean.lc3.event.chat;

import com.github.ucchyocean.lc3.event.LunaChatEvent;
import com.github.ucchyocean.lc3.member.ChannelMember;

import java.util.List;

public interface ChannelMemberChangedEvent extends LunaChatEvent {
    List<ChannelMember> getMembersBefore();
    List<ChannelMember> getMembersAfter();
}
