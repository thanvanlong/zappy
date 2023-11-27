package com.longtv.zappy.ui.story;

import java.io.Serializable;

public class PageStory implements Serializable {
    private int image;

    public PageStory(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
