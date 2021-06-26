package ndk.kada;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ndk.utils_android1.ActivityUtils1;
import ndk.utils_android14.ButtonUtils14;

public class AddStoreProductActivity extends KadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store_product);

        TextView textViewLearn = findViewById(R.id.textViewLearn);
        textViewLearn.setText("Try Catalog builder to \n" +
                "create your catalog faster");

        ButtonUtils14.associateClickAction(currentAppCompatActivity, R.id.buttonAddItem, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityUtils1.startActivityForClass(currentActivityContext, StoreStockActivity.class);
            }
        });
    }
}