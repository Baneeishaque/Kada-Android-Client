package ndk.kada.objectModels;

import android.graphics.drawable.Drawable;

public class ProductImageModel {

    Drawable imageDrawable;

    public ProductImageModel(Drawable imageDrawable) {

        this.imageDrawable = imageDrawable;
    }

    public Drawable getImageDrawable() {

        return imageDrawable;
    }

    public void setImageDrawable(Drawable imageDrawable) {

        this.imageDrawable = imageDrawable;
    }
}
