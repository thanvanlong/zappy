package com.longtv.zappy.network.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Content implements Serializable {
    @SerializedName("id")
    private int id;
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
    @SerializedName("views")
    private int views;
    @SerializedName("isLike")
    private boolean isLike;
    @SerializedName("chaptersCount")
    private int chaptersCount;

    public int getChaptersCount() {
        return chaptersCount;
    }

    public void setChaptersCount(int chaptersCount) {
        this.chaptersCount = chaptersCount;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    private List<Content> related = new ArrayList<>();

    public List<Content> getRelated() {
        return related;
    }

    public void setRelated(List<Content> related) {
        this.related = related;
    }

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
        return authors == null ? new ArrayList<>() : authors;
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
        return types == null ? new ArrayList<>() : types;
    }

    public void setTypes(List<ContentType> types) {
        this.types = types;
    }

    public String getUrlStream() {
        return urlStream == null ? "https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_fmp4/master.m3u8" : urlStream;
    }

    public void setUrlStream(String urlStream) {
        this.urlStream = urlStream;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
