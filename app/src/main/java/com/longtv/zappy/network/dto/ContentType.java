package com.longtv.zappy.network.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ContentType implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private int type;
    @SerializedName("id")
    private int id;
    @SerializedName("medias")
    private List<Content> contents;
    @SerializedName("comics")
    private List<Content> comics;

    public List<Content> getComics() {
        return comics;
    }

    public void setComics(List<Content> comics) {
        this.comics = comics;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

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
