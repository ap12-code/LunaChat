package com.github.ucchyocean.lc3.event;

public interface PlatformEventSenderInterface<T extends LunaChatEvent> {

    T emit(T event);
}
