package com.longtv.zappy.network.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MetaData implements Serializable {
    @SerializedName("totalPage")
    private int totalPage;
    @SerializedName("currentPage")
    private int currentPage;
}
