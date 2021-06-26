package ndk.kada;

import android.os.Bundle;
import android.widget.TextView;

public class AddStoreProductActivity extends KadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store_product);

        TextView textViewLearn = findViewById(R.id.textViewLearn);
        textViewLearn.setText("Try Catalog builder to \n" +
                "create your catalog faster");
    }
}