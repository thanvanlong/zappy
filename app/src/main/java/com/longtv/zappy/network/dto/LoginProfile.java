package com.longtv.zappy.network.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginProfile implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("avatar")
    private String avatar;

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
}
