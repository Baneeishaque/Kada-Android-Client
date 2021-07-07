package ndk.kada.objectModels;

public class StoreModel {

    int storeId;
    String storeName;
    float storeRating;
    float storeAverageDeliveryTime;

    public StoreModel(int storeId, String storeName, float storeRating, float storeAverageDeliveryTime) {

        this.storeId = storeId;
        this.storeName = storeName;
        this.storeRating = storeRating;
        this.storeAverageDeliveryTime = storeAverageDeliveryTime;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public float getStoreRating() {
        return storeRating;
    }

    public void setStoreRating(float storeRating) {
        this.storeRating = storeRating;
    }

    public float getStoreAverageDeliveryTime() {
        return storeAverageDeliveryTime;
    }

    public void setStoreAverageDeliveryTime(float storeAverageDeliveryTime) {
        this.storeAverageDeliveryTime = storeAverageDeliveryTime;
    }
}
