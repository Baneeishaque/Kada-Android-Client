package ndk.kada.objectModels;

import java.util.Date;

public class ConfirmedOrderModel {

    String orderShopName;
    int orderId;
    Date orderPlacedOn;
    OrderItemModel[] orderItems;

    public ConfirmedOrderModel(String orderShopName, int orderId, Date orderPlacedOn, OrderItemModel[] orderItems) {
        this.orderShopName = orderShopName;
        this.orderId = orderId;
        this.orderPlacedOn = orderPlacedOn;
        this.orderItems = orderItems;
    }

    public String getOrderShopName() {
        return orderShopName;
    }

    public void setOrderShopName(String orderShopName) {
        this.orderShopName = orderShopName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderPlacedOn() {
        return orderPlacedOn;
    }

    public void setOrderPlacedOn(Date orderPlacedOn) {
        this.orderPlacedOn = orderPlacedOn;
    }

    public OrderItemModel[] getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItemModel[] orderItems) {
        this.orderItems = orderItems;
    }
}

