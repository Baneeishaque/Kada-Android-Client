package ndk.kada.objectModels;

public class OrderItemModel {

    String itemName;
    float itemQuantity;
    String itemUnit;

    public OrderItemModel(String itemName, float itemQuantity, String itemUnit) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemUnit = itemUnit;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(float itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }
}
