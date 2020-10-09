package com.shery.hblcaller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("baseurl")
    @Expose
    private String baseurl;
    @SerializedName("port")
    @Expose
    private String port;
    @SerializedName("username")
    @Expose
    private String username;

    public String getBaseurl() {
        return baseurl;
    }

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return username;
    }

    public void setUser(String user) {
        this.username = user;
    }
}
