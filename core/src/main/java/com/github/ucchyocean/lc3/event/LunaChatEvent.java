package com.github.ucchyocean.lc3.event;

import com.github.ucchyocean.lc3.channel.Channel;

public interface LunaChatEvent {
    String getChannelName();
    Channel getChannel();
}
