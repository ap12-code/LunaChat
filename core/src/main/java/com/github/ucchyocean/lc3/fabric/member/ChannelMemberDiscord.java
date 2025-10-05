package com.github.ucchyocean.lc3.fabric.member;

import net.kyori.adventure.text.Component;

public class ChannelMemberDiscord extends ChannelMember {
    private final String displayName;

    public ChannelMemberDiscord(String name) {
        this.displayName = name;
    }

    @Override
    public boolean isOnline() {
        return true;
    }

    @Override
    public String getName() {
        return this.displayName;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String getPrefix() {
        return "";
    }

    @Override
    public String getSuffix() {
        return "";
    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void sendMessage(Component message) {

    }

    @Override
    public String getWorldName() {
        return "";
    }

    @Override
    public String getServerName() {
        return "";
    }

    @Override
    public boolean hasPermission(String node) {
        return false;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean isPermissionSet(String node) {
        return false;
    }

    @Override
    public void chat(String message) {
        
    }
}
