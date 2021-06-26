package ndk.kada;

import android.os.Bundle;
import android.view.View;

import ndk.utils_android1.ActivityUtils1;
import ndk.utils_android14.ButtonUtils14;

public class StoreDashboardActivity extends KadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_dashboard);

        //TODO : Associate Start Activity
        ButtonUtils14.associateClickAction(currentAppCompatActivity, R.id.buttonAddYour, v -> ActivityUtils1.startActivityForClass(currentActivityContext, AddStoreProductActivity.class));
    }
}