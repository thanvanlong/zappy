package com.longtv.zappy.network.dto;

import com.google.gson.annotations.SerializedName;

public class ChapterWrapper {
    @SerializedName("url")
    private String url;
    @SerializedName("index")
    private int index;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
