package com.github.ucchyocean.lc3.event.listener;

import com.github.ucchyocean.lc3.event.LunaChatEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
    Class<? extends LunaChatEvent> target();
}
