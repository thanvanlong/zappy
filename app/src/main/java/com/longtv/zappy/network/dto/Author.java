package com.longtv.zappy.network.dto;

import com.google.gson.annotations.SerializedName;

public class Author {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String desc;
    @SerializedName("image")
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
