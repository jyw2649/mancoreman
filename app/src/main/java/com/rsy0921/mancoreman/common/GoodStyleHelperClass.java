package com.rsy0921.mancoreman.common;

import android.graphics.drawable.Drawable;
import android.widget.RelativeLayout;

public class GoodStyleHelperClass {

    int image;

    String title;

    Drawable gradient;

    public GoodStyleHelperClass(int image, String title, Drawable gradient) {
        this.image = image;
        this.title = title;
        this.gradient = gradient;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public Drawable getGradient() {
        return gradient;
    }
}
