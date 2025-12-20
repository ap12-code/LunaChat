/*
 * @author     ucchy
 * @license    LGPLv3
 * @copyright  Copyright ucchy 2020
 */
package com.github.ucchyocean.lc3.bukkit;

import java.util.List;
import java.util.Map;

import com.github.ucchyocean.lc3.bukkit.event.*;
import com.github.ucchyocean.lc3.event.LunaChatEvent;
import com.github.ucchyocean.lc3.event.PlatformEventSenderInterface;
import org.bukkit.Bukkit;

import com.github.ucchyocean.lc3.event.EventResult;
import com.github.ucchyocean.lc3.event.EventSenderInterface;
import com.github.ucchyocean.lc3.member.ChannelMember;
import org.bukkit.event.Event;

/**
 * Bukkitのイベント実行クラス
 * @author ucchy
 */
public class LunaChatBukkitEventSender implements PlatformEventSenderInterface<LunaChatBukkitBaseEvent> {
    @Override
    public void emit(LunaChatBukkitBaseEvent event) {
        Bukkit.getPluginManager().callEvent(event);
    }
}
