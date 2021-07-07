package ndk.kada.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.javatuples.Triplet;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import ndk.kada.R;
import ndk.kada.constants.KadaHttpApiParameters;
import ndk.kada.constants.KadaServerJsonFields;
import ndk.kada.constants.KadaSharedPreferenceKeys;
import ndk.kada.objectModels.OrderModel;
import ndk.kada.recyclerViewAdaptors.OrderRecyclerViewAdaptor;
import ndk.utils_android1.DateUtils1;
import ndk.utils_android14.HttpApiSelectTaskWrapper14;
import ndk.utils_android19.ExceptionUtils19;

public class PendingOrdersActivity extends KadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_orders);

        RecyclerView recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        ArrayList<OrderModel> orders = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewOrders.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewOrders.getContext(), linearLayoutManager.getOrientation());
        recyclerViewOrders.addItemDecoration(dividerItemDecoration);

        OrderRecyclerViewAdaptor orderRecyclerViewAdaptor = new OrderRecyclerViewAdaptor(currentActivityContext, orders);
        recyclerViewOrders.setAdapter(orderRecyclerViewAdaptor);

        HttpApiSelectTaskWrapper14.executeNonSplashForegroundPostWithParametersStatusCheckOnAsyncResponseJsonArrayFirstElementCustomizedNoEntriesMessageAndNoEntriesActions(getOrdersApiUrl(), getOrderApiParameters(), currentActivityContext, (View) findViewById(R.id.progressBar), (View) findViewById(R.id.constraintLayout), applicationSpecification.applicationName, jsonArray -> {

            try {

                for (int i = 1; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Triplet<Boolean, Date, String> mySqlDateTimeStringToCustomDateTimeStringResult = DateUtils1.mySqlDateTimeStringToCustomDateTime(jsonObject.getString(KadaServerJsonFields.orderDateTime), OrderRecyclerViewAdaptor.orderDateTimeFormat);
                    if (mySqlDateTimeStringToCustomDateTimeStringResult.getValue0()) {

                        int orderId = jsonObject.getInt(KadaServerJsonFields.orderId);
                        orders.add(new OrderModel(orderId, mySqlDateTimeStringToCustomDateTimeStringResult.getValue1(), getShopOrUserNameFromJsonObject(jsonObject), kadaApiUtils.getOrderImageUrl(orderId)));

                    } else {

                        applicationLogUtils.debugOnGui(mySqlDateTimeStringToCustomDateTimeStringResult.getValue2());
                    }
                }
                orderRecyclerViewAdaptor.notifyDataSetChanged();

            } catch (JSONException jsonException) {

                ExceptionUtils19.handleExceptionOnGui(currentApplicationContext, applicationSpecification.applicationName, jsonException);
            }
        }, "No Orders...", () -> {
        });

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                performRecyclerViewOrdersFiltration(query, orderRecyclerViewAdaptor, orders);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                performRecyclerViewOrdersFiltration(newText, orderRecyclerViewAdaptor, orders);
                return false;
            }
        });
    }

    @NonNull
    public String getShopOrUserNameFromJsonObject(JSONObject jsonObject) throws JSONException {

        return jsonObject.getString(KadaServerJsonFields.userName);
    }

    @NonNull
    public Pair[] getOrderApiParameters() {

        return new Pair[]{new Pair<>(KadaHttpApiParameters.shopId, applicationSharedPreferences.getString(KadaSharedPreferenceKeys.userShopId, "0"))};
    }

    public String getOrdersApiUrl() {

        return kadaApiUtils.getOrdersForShopApiUrl();
    }

    private void performRecyclerViewOrdersFiltration(String query, OrderRecyclerViewAdaptor orderRecyclerViewAdaptor, ArrayList<OrderModel> orders) {

        if (query.isEmpty()) {

            orderRecyclerViewAdaptor.updateList(orders);

        } else {

            ArrayList<OrderModel> filteredOrders = new ArrayList<>();
            for (OrderModel order : orders) {

                if (order.getShopOrUserName().contains(query) || String.valueOf(order.getOrderId()).contains(query) || DateUtils1.dateTimeToCustomDateTimeString(order.getOrderDateTime(), OrderRecyclerViewAdaptor.orderDateTimeFormat).contains(query)) {

                    filteredOrders.add(order);
                }
            }
            orderRecyclerViewAdaptor.updateList(filteredOrders);
        }
    }
}