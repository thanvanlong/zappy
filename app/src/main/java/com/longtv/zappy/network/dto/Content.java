package com.longtv.zappy.network.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Content implements Serializable {
    @SerializedName("title")
    private String name;
    @SerializedName("thumbnail")
    private String coverImage;
    @SerializedName("desc")
    private String desc;
    @SerializedName("isAccess")
    private boolean isAccess;
    @SerializedName("authors")
    private List<Author> authors;
    @SerializedName("golds")
    private int golds;
    @SerializedName("genres")
    private List<ContentType> types;
    @SerializedName("url")
    private String urlStream;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isAccess() {
        return isAccess;
    }

    public void setAccess(boolean access) {
        isAccess = access;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public int getGolds() {
        return golds;
    }

    public void setGolds(int golds) {
        this.golds = golds;
    }

    public List<ContentType> getTypes() {
        return types;
    }

    public void setTypes(List<ContentType> types) {
        this.types = types;
    }

    public String getUrlStream() {
        return urlStream;
    }

    public void setUrlStream(String urlStream) {
        this.urlStream = urlStream;
    }
}
