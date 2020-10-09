package com.shery.hblcaller;

public enum ServerInfo {
    BASE_URL("http://192.168.0.110"),
    PORT("8000"),
    USER("/user"),
    USERS("/users/");

    private final String uri;

    ServerInfo(String uri) {
        this.uri  = uri;
    }

    public String getUri() {
        return this.uri;
    }
}
