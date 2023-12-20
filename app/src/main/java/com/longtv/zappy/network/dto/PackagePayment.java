package com.longtv.zappy.network.dto;

import com.google.gson.annotations.SerializedName;

public class PackagePayment {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private String price;
    @SerializedName("desc")
    private String description;
    @SerializedName("golds")
    private String golds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGolds() {
        return golds;
    }

    public void setGolds(String golds) {
        this.golds = golds;
    }
}
