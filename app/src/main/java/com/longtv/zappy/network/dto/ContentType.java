package com.longtv.zappy.network.dto;

import com.google.gson.annotations.SerializedName;

public class ContentType {
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private int type;
    @SerializedName("id")
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
