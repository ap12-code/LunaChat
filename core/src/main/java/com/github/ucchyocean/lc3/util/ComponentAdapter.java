package com.github.ucchyocean.lc3.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

@SuppressWarnings("unused")
public class ComponentAdapter {

    public static Component legacy(String message) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }

    public static Component[] toComponent(String message) {
        return new Component[]{legacy(message)};
    }

    public static Component merge(Component... components) {
        return Component.join(JoinConfiguration.noSeparators(), components);
    }
}
