package com.longtv.zappy.network.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Profile implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("birthday")
    private Date dob;
    @SerializedName("onScreen")
    private long timeOnScreen;
    @SerializedName("order")
    private int order;

    public Profile(String nickname, Date dob, long timeOnScreen, int order) {
        this.nickname = nickname;
        this.dob = dob;
        this.timeOnScreen = timeOnScreen;
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public long getTimeOnScreen() {
        return timeOnScreen;
    }

    public void setTimeOnScreen(long timeOnScreen) {
        this.timeOnScreen = timeOnScreen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}
