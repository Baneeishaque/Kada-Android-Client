package ndk.kada;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ndk.utils_android1.ToastUtils1;
import ndk.utils_android14.ActivityUtils14;
import ndk.utils_android14.ButtonUtils14;
import ndk.utils_android14.HttpApiSelectTask14;
import ndk.utils_android14.HttpApiSelectTaskWrapper14;
import ndk.utils_android19.ExceptionUtils19;

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
//        stocks.add(new StockModal("ISe", 45, 50, true));
//        stocks.add(new StockModal("h³]bÀ", 35, 40, true));
//        stocks.add(new StockModal("sNdp]bÀ", 25, 30, true));
//        stocks.add(new StockModal("D¸v", 5, 10, false));
//        stocks.add(new StockModal("sNdnb DÅn", 15, 20, false));

        HttpApiSelectTaskWrapper14.executeNonSplashForegroundPostWithParametersAndStatusCheckOnAsyncResponseJsonArrayFirstElement(new KadaApiUtils().getItemsForShopApiUrl(), new Pair[]{new Pair<>("shopId", applicationSharedPreferences.getString("userShopId", "0"))}, currentActivityContext, (View) findViewById(R.id.progressBar), (View) findViewById(R.id.constraintLayout), applicationSpecification.applicationName, jsonArray -> {

            try {

                for (int i = 1; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    stocks.add(new StockModal(jsonObject.getInt("item_id"), jsonObject.getString("item_name"), Float.parseFloat(jsonObject.getString("item_maximum_retail_price")), Float.parseFloat(jsonObject.getString("item_selling_price")), jsonObject.getString("item_in_stock").equals("1")));
                }
            } catch (JSONException jsonException) {

                ExceptionUtils19.handleExceptionOnGui(currentApplicationContext, applicationSpecification.applicationName, jsonException);
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewStockList.getContext(), linearLayoutManager.getOrientation());
        recyclerViewStockList.addItemDecoration(dividerItemDecoration);

        StockListRecyclerViewAdaptor stockListRecyclerViewAdaptor = new StockListRecyclerViewAdaptor(currentActivityContext, stocks);
        stockListRecyclerViewAdaptor.setOnSwitchItemStockOnOffClickListener((switchItemStockOnOff, textViewItemOnOff) -> {

            if (switchItemStockOnOff.isChecked()) {

                HttpApiSelectTaskWrapper14.executeNonSplashForegroundPostWithParameters(new KadaApiUtils().getUpdateStockForItemApiUrl(), new Pair[]{new Pair<>("itemId", switchItemStockOnOff.getTag().toString()), new Pair<>("itemInStock", "1")}, currentActivityContext, (View) findViewById(R.id.progressBar), (View) findViewById(R.id.constraintLayout), applicationSpecification.applicationName, (HttpApiSelectTask14.AsyncResponseJSONObject) jsonObject -> {

                    try {

                        if (jsonObject.getString("status").equals("0")) {

                            ToastUtils1.longToast(currentApplicationContext, "Item Updates Successfully...");
                            textViewItemOnOff.setText("In Stock");
                            textViewItemOnOff.setTextColor(getResources().getColor(R.color.black));

                        } else if (jsonObject.getString("status").equals("1")) {

                            //TODO : Toast With Logging Process
                            ToastUtils1.longToast(currentApplicationContext, "Server Error, Please Try Again...");
                            applicationLogUtils.debugOnGui("Server Error : " + jsonObject.getString("error"));
                            switchItemStockOnOff.setChecked(false);

                        } else {

                            ToastUtils1.longToast(currentApplicationContext, "Server Error, Please Try Again...");
                            switchItemStockOnOff.setChecked(false);
                        }

                    } catch (JSONException jsonException) {

                        ExceptionUtils19.handleExceptionOnGui(currentApplicationContext, applicationSpecification.applicationName, jsonException);
                    }
                });

            } else {

                HttpApiSelectTaskWrapper14.executeNonSplashForegroundPostWithParameters(new KadaApiUtils().getUpdateStockForItemApiUrl(), new Pair[]{new Pair<>("itemId", switchItemStockOnOff.getTag().toString()), new Pair<>("itemInStock", "0")}, currentActivityContext, (View) findViewById(R.id.progressBar), (View) findViewById(R.id.constraintLayout), applicationSpecification.applicationName, (HttpApiSelectTask14.AsyncResponseJSONObject) jsonObject -> {

                    try {

                        if (jsonObject.getString("status").equals("0")) {

                            ToastUtils1.longToast(currentApplicationContext, "Item Updates Successfully...");
                            textViewItemOnOff.setText("Out of Stock");
                            textViewItemOnOff.setTextColor(getResources().getColor(R.color.red2));

                        } else if (jsonObject.getString("status").equals("1")) {

                            //TODO : Toast With Logging Process
                            ToastUtils1.longToast(currentApplicationContext, "Server Error, Please Try Again...");
                            applicationLogUtils.debugOnGui("Server Error : " + jsonObject.getString("error"));
                            switchItemStockOnOff.setChecked(true);

                        } else {

                            ToastUtils1.longToast(currentApplicationContext, "Server Error, Please Try Again...");
                            switchItemStockOnOff.setChecked(true);
                        }
                    } catch (JSONException jsonException) {

                        ExceptionUtils19.handleExceptionOnGui(currentApplicationContext, applicationSpecification.applicationName, jsonException);
                    }
                });
            }
        });
        recyclerViewStockList.setAdapter(stockListRecyclerViewAdaptor);

        ButtonUtils14.associateClickAction(currentAppCompatActivity, R.id.buttonAddYour, v -> ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, AddStoreProductActivity.class));
    }
}
