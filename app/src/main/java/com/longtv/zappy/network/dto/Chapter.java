package com.longtv.zappy.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("chap")
    private int chap;
    @SerializedName("imageUrl")
    private List<ChapterWrapper> images = new ArrayList<>();
    @SerializedName("createdAt")
    private String createdAt;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt.split("T")[0].replace("-", "/");
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getChap() {
        return chap;
    }

    public void setChap(int chap) {
        this.chap = chap;
    }

    public List<ChapterWrapper> getImages() {
        return images;
    }

    public void setImages(List<ChapterWrapper> images) {
        this.images = images;
    }
}
