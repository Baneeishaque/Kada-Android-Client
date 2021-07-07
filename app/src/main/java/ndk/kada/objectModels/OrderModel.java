package ndk.kada.objectModels;

import java.util.Date;

public class OrderModel {

    int orderId;
    Date orderDateTime;
    String shopOrUserName;
    String orderImageUrl;

    public OrderModel(int orderId, Date orderDateTime, String shopOrUserName, String orderImageUrl) {

        this.orderId = orderId;
        this.orderDateTime = orderDateTime;
        this.shopOrUserName = shopOrUserName;
        this.orderImageUrl = orderImageUrl;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(Date orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getShopOrUserName() {
        return shopOrUserName;
    }

    public void setShopOrUserName(String shopOrUserName) {
        this.shopOrUserName = shopOrUserName;
    }

    public String getOrderImageUrl() {
        return orderImageUrl;
    }

    public void setOrderImageUrl(String orderImageUrl) {
        this.orderImageUrl = orderImageUrl;
    }
}
