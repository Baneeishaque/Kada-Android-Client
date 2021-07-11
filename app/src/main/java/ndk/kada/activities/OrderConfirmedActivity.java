package ndk.kada.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;

import ndk.kada.R;
import ndk.kada.objectModels.CartProductModel;
import ndk.kada.objectModels.ConfirmedOrderModel;
import ndk.kada.objectModels.OrderItemModel;
import ndk.kada.recyclerViewAdaptors.CartConfirmRecyclerViewAdaptor;
import ndk.kada.recyclerViewAdaptors.OrderConfirmedRecyclerViewAdaptor;
import ndk.utils_android1.ActivityUtils1;
import ndk.utils_android1.DateUtils1;
import ndk.utils_android14.ButtonUtils14;

public class OrderConfirmedActivity extends KadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmed);

        RecyclerView recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        ArrayList<ConfirmedOrderModel> confirmedOrders = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewOrders.setLayoutManager(linearLayoutManager);

        confirmedOrders.add(new ConfirmedOrderModel("Goergettan's Kada", 125, new Date(), new OrderItemModel[]{new OrderItemModel("Rice", 1, "Kg"), new OrderItemModel("Wheat", 1, "Kg")}));

        confirmedOrders.add(new ConfirmedOrderModel("Deepa's Sweets", 263, new Date(), new OrderItemModel[]{new OrderItemModel("Cake", 2, "Kg"), new OrderItemModel("Laddu", 1, "Kg")}));

        OrderConfirmedRecyclerViewAdaptor orderConfirmedRecyclerViewAdaptor = new OrderConfirmedRecyclerViewAdaptor(currentActivityContext, confirmedOrders);
        recyclerViewOrders.setAdapter(orderConfirmedRecyclerViewAdaptor);
    }
}