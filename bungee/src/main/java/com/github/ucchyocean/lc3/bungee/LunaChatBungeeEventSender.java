/*
 * @author     ucchy
 * @license    LGPLv3
 * @copyright  Copyright ucchy 2020
 */
package com.github.ucchyocean.lc3.bungee;

import com.github.ucchyocean.lc3.bungee.event.*;
import com.github.ucchyocean.lc3.event.PlatformEventSenderInterface;

import net.md_5.bungee.api.ProxyServer;

/**
 * Bungeeのイベント実行クラス
 *
 * @author ucchy
 */
public record LunaChatBungeeEventSender(
        ProxyServer proxyServer) implements PlatformEventSenderInterface<LunaChatBungeeBaseEvent> {

    @Override
    public void emit(LunaChatBungeeBaseEvent event) {
        proxyServer.getPluginManager().callEvent(event);
    }
}
