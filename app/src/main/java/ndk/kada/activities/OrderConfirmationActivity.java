package ndk.kada.activities;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ndk.kada.R;
import ndk.kada.objectModels.CartProductModel;
import ndk.kada.recyclerViewAdaptors.CartConfirmRecyclerViewAdaptor;
import ndk.utils_android1.ActivityUtils1;
import ndk.utils_android1.DateUtils1;
import ndk.utils_android14.ButtonUtils14;

public class OrderConfirmationActivity extends KadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        RecyclerView recyclerViewCart = findViewById(R.id.recyclerViewCart);
        ArrayList<CartProductModel> cartProducts = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewCart.setLayoutManager(linearLayoutManager);

        cartProducts.add(new CartProductModel(getDrawable(R.drawable.george), "Georgettan's Kada", "Rice", DateUtils1.normalDateTimeStringToDate("05-10-2018").getValue1(), 5));
        cartProducts.add(new CartProductModel(getDrawable(R.drawable.george), "Georgettan's Kada", "Rice 2", DateUtils1.normalDateTimeStringToDate("05-10-2018").getValue1(), 15));
        cartProducts.add(new CartProductModel(getDrawable(R.drawable.george), "Georgettan's Kada", "Rice 3", DateUtils1.normalDateTimeStringToDate("05-10-2018").getValue1(), 10));

        cartProducts.add(new CartProductModel(getDrawable(R.drawable.george), "George's Kada", "Rice", DateUtils1.normalDateTimeStringToDate("05-10-2018").getValue1(), 5));
        cartProducts.add(new CartProductModel(getDrawable(R.drawable.george), "George's Kada", "Rice 2", DateUtils1.normalDateTimeStringToDate("05-10-2018").getValue1(), 15));

        CartConfirmRecyclerViewAdaptor cartRecyclerViewAdaptor = new CartConfirmRecyclerViewAdaptor(currentActivityContext, cartProducts);
        recyclerViewCart.setAdapter(cartRecyclerViewAdaptor);

        ButtonUtils14.associateClickAction(currentAppCompatActivity, R.id.buttonConfirmOrder, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityUtils1.startActivityForClass(currentActivityContext, OrderConfirmedActivity.class);
            }
        });
    }
}