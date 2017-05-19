package com.daman.farmify.GridHome;

import android.graphics.Bitmap;

/**
 * Created by Daman on 11-05-2017.
 */

public class ImageItem {
    private int image;
    private String title;

    public ImageItem(int image, String title) {
        super();
        this.image = image;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
