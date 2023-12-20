package com.longtv.zappy.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataListDTO<T> {
    @SerializedName("results")
    private List<T> results;

    @SerializedName("metadata")
    private MetaData metaData;


    public MetaData getMetaData() {
        return metaData;
    }

    public List<T> getResults() {
        return results;
    }
}
