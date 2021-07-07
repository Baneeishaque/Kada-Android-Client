package ndk.kada;

public class StoreModel {

    String storeName;
    float storeRating;
    float storeAverageDeliveryTime;

    public StoreModel(String storeName, float storeRating, float storeAverageDeliveryTime) {

        this.storeName = storeName;
        this.storeRating = storeRating;
        this.storeAverageDeliveryTime = storeAverageDeliveryTime;
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
