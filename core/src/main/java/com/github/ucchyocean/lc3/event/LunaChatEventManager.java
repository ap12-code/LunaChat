package com.github.ucchyocean.lc3.event;

import com.github.ucchyocean.lc3.LunaChat;
import com.github.ucchyocean.lc3.event.listener.EventHandler;
import com.github.ucchyocean.lc3.event.listener.LunaChatListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class LunaChatEventManager {
    private final Map<Class<? extends LunaChatListener>, LunaChatListener> targetClasses = new HashMap<>();

    public void register(LunaChatListener listener) {
        if (targetClasses.containsKey(listener.getClass())) return;
        targetClasses.put(listener.getClass(), listener);
    }

    public EventResult emitEvent(LunaChatEvent event) {
        for (Class<? extends LunaChatListener> targetClass : targetClasses.keySet()) {
            for (Method method : targetClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(EventHandler.class) && method.getAnnotation(EventHandler.class).target().equals(event.getClass())) {
                    try {
                        return (EventResult) method.invoke(targetClasses.get(targetClass), event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return null;
    }
}
