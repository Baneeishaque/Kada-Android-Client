package ndk.kada;

public class StockModal {

    String itemName;
    float itemMaximumRetailPrice, itemSellingPrice;
    boolean isInStock;

    public StockModal(String itemName, float itemMaximumRetailPrice, float itemSellingPrice, boolean isInStock) {

        this.itemName = itemName;
        this.itemMaximumRetailPrice = itemMaximumRetailPrice;
        this.itemSellingPrice = itemSellingPrice;
        this.isInStock = isInStock;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getItemMaximumRetailPrice() {
        return itemMaximumRetailPrice;
    }

    public void setItemMaximumRetailPrice(float itemMaximumRetailPrice) {
        this.itemMaximumRetailPrice = itemMaximumRetailPrice;
    }

    public float getItemSellingPrice() {
        return itemSellingPrice;
    }

    public void setItemSellingPrice(float itemSellingPrice) {
        this.itemSellingPrice = itemSellingPrice;
    }

    public boolean isInStock() {
        return isInStock;
    }

    public void setInStock(boolean inStock) {
        isInStock = inStock;
    }
}
