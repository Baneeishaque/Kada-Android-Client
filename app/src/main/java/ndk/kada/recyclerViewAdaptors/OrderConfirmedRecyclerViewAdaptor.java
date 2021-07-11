package ndk.kada.recyclerViewAdaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ndk.kada.R;
import ndk.kada.objectModels.ConfirmedOrderModel;
import ndk.kada.objectModels.OrderItemModel;
import ndk.utils_android1.DateUtils1;

public class OrderConfirmedRecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    LayoutInflater layoutInflater;
    ArrayList<ConfirmedOrderModel> confirmedOrders;
    public static SimpleDateFormat orderDateTimeFormat = new SimpleDateFormat("dd MMM. h:mm a", Locale.UK);

    public OrderConfirmedRecyclerViewAdaptor(Context context, ArrayList<ConfirmedOrderModel> confirmedOrders) {

        layoutInflater = LayoutInflater.from(context);
        this.confirmedOrders = confirmedOrders;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ConfirmedOrderViewHolder(layoutInflater.inflate(R.layout.list_item_order_confirmed, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ConfirmedOrderModel confirmedOrder = confirmedOrders.get(position);
        ConfirmedOrderViewHolder confirmedOrderViewHolder = (ConfirmedOrderViewHolder) holder;
        confirmedOrderViewHolder.textViewOrderShopName.setText(confirmedOrder.getOrderShopName());
        confirmedOrderViewHolder.textViewOrderId.setText("Order ID : " + confirmedOrder.getOrderId());
        confirmedOrderViewHolder.textViewOrderPlacedDateTime.setText("Ordered On " + DateUtils1.dateTimeToCustomDateTimeString(confirmedOrder.getOrderPlacedOn(), orderDateTimeFormat));
        confirmedOrderViewHolder.textViewOrderItems.setText("Order Items : " + orderItemsToString(confirmedOrder.getOrderItems()));
    }

    public String orderItemsToString(OrderItemModel[] orderItems) {

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < orderItems.length; i++) {

            OrderItemModel orderItem = orderItems[i];

            if (i == 0) {

                result = new StringBuilder(orderItem.getItemName() + " * " + orderItem.getItemQuantity() + orderItem.getItemUnit());

            } else {

                result.append("\n\t\t\t\t\t\t\t\t\t\t\t\t ").append(orderItem.getItemName()).append(" * ").append(orderItem.getItemQuantity()).append(orderItem.getItemUnit());
            }
        }
        return result.toString();
    }

    @Override
    public int getItemCount() {

        return confirmedOrders.size();
    }

//    public void updateList(ArrayList<CartProductModel> orders) {
//
//        this.cartProducts = orders;
//        notifyDataSetChanged();
//    }

    static class ConfirmedOrderViewHolder extends RecyclerView.ViewHolder {

        TextView textViewOrderShopName, textViewOrderId, textViewOrderPlacedDateTime, textViewOrderItems;

        public ConfirmedOrderViewHolder(@NonNull View itemView) {

            super(itemView);
            textViewOrderShopName = itemView.findViewById(R.id.textViewOrderShopName);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewOrderPlacedDateTime = itemView.findViewById(R.id.textViewOrderPlacedDateTime);
            textViewOrderItems = itemView.findViewById(R.id.textViewOrderItems);
        }
    }
}
