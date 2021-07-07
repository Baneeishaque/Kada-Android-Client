package ndk.kada.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ndk.kada.R;
import ndk.utils_android1.ActivityUtils1;
import ndk.utils_android14.ActivityUtils14;
import ndk.utils_android14.ButtonUtils14;

public class StoreDashboardActivity extends KadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_dashboard);

        TextView textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewWelcome.setText("Welcome to " + applicationSharedPreferences.getString("userShopName", "Kada X"));

        //TODO : Associate Start Activity
        ButtonUtils14.associateClickAction(currentAppCompatActivity, R.id.buttonManageStock, v -> ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, StoreStockActivity.class));

        ButtonUtils14.associateClickAction(currentAppCompatActivity, R.id.buttonPendingOrders, v -> ActivityUtils1.startActivityForClass(currentActivityContext, PendingOrdersActivity.class));
    }
}