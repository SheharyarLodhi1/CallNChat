package com.shery.hblcaller.chat;

public enum MessageType {

    CHAT_MINE(0), CHAT_PARTNER(1), USER_JOIN(2), USER_LEAVE(3);

    public final Integer index;

    MessageType(Integer index) {

        this.index = index;
    }

    public Integer getUri() {
        return this.index;
    }
}
