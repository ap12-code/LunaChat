package com.github.ucchyocean.lc3.velocity;

import com.github.ucchyocean.lc3.event.PlatformEventSenderInterface;
import com.github.ucchyocean.lc3.velocity.event.*;
import com.velocitypowered.api.event.EventManager;

public record LunaChatVelocityEventSender(EventManager eventManager)
        implements PlatformEventSenderInterface<LunaChatVelocityBaseEvent> {

    @Override
    public void emit(LunaChatVelocityBaseEvent event) {
        eventManager.fire(event);
    }
}
