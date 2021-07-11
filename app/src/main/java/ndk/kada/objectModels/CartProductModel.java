package ndk.kada.objectModels;

import android.graphics.drawable.Drawable;

import java.util.Date;

public class CartProductModel {

    Drawable cartProductImage;
    String shopName, cartProductName;
    Date cartProductAddedOn;
    float cartProductQuantity;

    public CartProductModel(Drawable cartProductImage, String shopName, String cartProductName, Date cartProductAddedOn, float cartProductQuantity) {

        this.cartProductImage = cartProductImage;
        this.shopName = shopName;
        this.cartProductName = cartProductName;
        this.cartProductAddedOn = cartProductAddedOn;
        this.cartProductQuantity = cartProductQuantity;
    }

    public Drawable getCartProductImage() {
        return cartProductImage;
    }

    public void setCartProductImage(Drawable cartProductImage) {
        this.cartProductImage = cartProductImage;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCartProductName() {
        return cartProductName;
    }

    public void setCartProductName(String cartProductName) {
        this.cartProductName = cartProductName;
    }

    public Date getCartProductAddedOn() {
        return cartProductAddedOn;
    }

    public void setCartProductAddedOn(Date cartProductAddedOn) {
        this.cartProductAddedOn = cartProductAddedOn;
    }

    public float getCartProductQuantity() {
        return cartProductQuantity;
    }

    public void setCartProductQuantity(float cartProductQuantity) {
        this.cartProductQuantity = cartProductQuantity;
    }
}
