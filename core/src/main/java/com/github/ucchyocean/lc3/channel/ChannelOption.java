package com.github.ucchyocean.lc3.channel;

import com.github.ucchyocean.lc3.LunaChat;
import com.github.ucchyocean.lc3.LunaChatConfig;
import com.github.ucchyocean.lc3.japanize.JapanizeType;
import com.github.ucchyocean.lc3.member.ChannelMember;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelOption {
    private final Channel channel;

    private final List<ChannelMember> members;
    private final List<ChannelMember> moderator;
    private final List<ChannelMember> banned;
    private final List<ChannelMember> muted;
    private final List<ChannelMember> hidden;
    private String alias;
    private String description;
    private String password;
    private boolean visible;
    private String colorCode;
    private String format;
    private boolean broadcastChannel;
    private boolean isWorldRange;
    private int chatRange;
    private final Map<ChannelMember, Long> banExpires;
    private final Map<ChannelMember, Long> muteExpires;
    private ChannelMember privateMessageTo;
    private boolean allowcc;
    private JapanizeType japanizeType;

    public ChannelOption(Channel channel) {
        this.channel = channel;

        this.alias = "";
        this.description = "";
        this.members = new ArrayList<>();
        this.banned = new ArrayList<>();
        this.muted = new ArrayList<>();
        this.hidden = new ArrayList<>();
        this.moderator = new ArrayList<>();
        this.password = "";
        this.visible = true;
        this.colorCode = "";
        this.broadcastChannel = false;
        this.isWorldRange = false;
        this.chatRange = 0;
        this.banExpires = new HashMap<>();
        this.muteExpires = new HashMap<>();
        this.privateMessageTo = null;
        this.allowcc = true;

        this.format = getDefaultFormat();
        this.japanizeType = LunaChat.getConfig().getJapanizeType();
    }

    private String getDefaultFormat() {
        LunaChatConfig config = LunaChat.getConfig();
        if (channel.isPersonalChat()) {
            return config.getDefaultFormatForPrivateMessage();
        } else {
            return config.getDefaultFormat();
        }
    }

    public List<ChannelMember> getMembers() {
        return members;
    }

    public List<ChannelMember> getModerator() {
        return moderator;
    }

    public List<ChannelMember> getBanned() {
        return banned;
    }

    public List<ChannelMember> getMuted() {
        return muted;
    }

    public List<ChannelMember> getHidden() {
        return hidden;
    }

    public String getAlias() {
        return alias;
    }

    public String getDescription() {
        return description;
    }

    public String getPassword() {
        return password;
    }

    public boolean isVisible() {
        return visible;
    }

    public String getColorCode() {
        return colorCode;
    }

    public String getFormat() {
        return format;
    }

    public boolean isBroadcastChannel() {
        return broadcastChannel;
    }

    public boolean isWorldRange() {
        return isWorldRange;
    }

    public int getChatRange() {
        return chatRange;
    }

    public Map<ChannelMember, Long> getBanExpires() {
        return banExpires;
    }

    public Map<ChannelMember, Long> getMuteExpires() {
        return muteExpires;
    }

    public ChannelMember getPrivateMessageTo() {
        return privateMessageTo;
    }

    public boolean isAllowcc() {
        return allowcc;
    }

    public JapanizeType getJapanizeType() {
        return japanizeType;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setBroadcastChannel(boolean broadcastChannel) {
        this.broadcastChannel = broadcastChannel;
    }

    public void setWorldRange(boolean worldRange) {
        isWorldRange = worldRange;
    }

    public void setChatRange(int chatRange) {
        this.chatRange = chatRange;
    }

    public void setPrivateMessageTo(ChannelMember privateMessageTo) {
        this.privateMessageTo = privateMessageTo;
    }

    public void setAllowcc(boolean allowcc) {
        this.allowcc = allowcc;
    }

    public void setJapanizeType(JapanizeType japanizeType) {
        this.japanizeType = japanizeType;
    }
}
