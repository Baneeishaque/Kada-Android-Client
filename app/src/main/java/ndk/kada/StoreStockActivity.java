package ndk.kada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ndk.utils_android1.ToastUtils1;

public class StoreStockActivity extends KadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_stock);

        EditText editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearch.setText("Search products");

        ConstraintLayout constraintLayoutShareToWhatsapp = findViewById(R.id.constraintLayoutShareToWhatsapp);
        constraintLayoutShareToWhatsapp.setOnClickListener(v -> {

            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Kada");
            try {

                startActivity(whatsappIntent);

            } catch (android.content.ActivityNotFoundException ex) {

                ToastUtils1.shortToast(currentApplicationContext, "Whatsapp have not been installed!...");
            }
        });

        RecyclerView recyclerViewStockList = findViewById(R.id.recyclerViewStock);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewStockList.setLayoutManager(linearLayoutManager);

        ArrayList<StockModal> stocks = new ArrayList<>();
        stocks.add(new StockModal("ISe", 45, 50, true));
        stocks.add(new StockModal("h³]bÀ", 35, 40, true));
        stocks.add(new StockModal("sNdp]bÀ", 25, 30, true));
        stocks.add(new StockModal("D¸v", 5, 10, false));
        stocks.add(new StockModal("sNdnb DÅn", 15, 20, false));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewStockList.getContext(), linearLayoutManager.getOrientation());
        recyclerViewStockList.addItemDecoration(dividerItemDecoration);

        StockListRecyclerViewAdaptor stockListRecyclerViewAdaptor = new StockListRecyclerViewAdaptor(currentActivityContext, stocks);
        stockListRecyclerViewAdaptor.setOnSwitchItemStockOnOffClickListener((switchItemStockOnOff, textViewItemOnOff) -> {

            if (switchItemStockOnOff.isChecked()) {

                textViewItemOnOff.setText("In Stock");
                textViewItemOnOff.setTextColor(getResources().getColor(R.color.black));

            } else {

                textViewItemOnOff.setText("Out of Stock");
                textViewItemOnOff.setTextColor(getResources().getColor(R.color.red2));
            }
        });
        recyclerViewStockList.setAdapter(stockListRecyclerViewAdaptor);
    }
}