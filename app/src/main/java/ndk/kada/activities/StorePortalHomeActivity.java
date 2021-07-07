package ndk.kada.activities;

import android.os.Bundle;
import android.view.View;

import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ndk.kada.utils.KadaApiUtils;
import ndk.kada.recyclerViewAdaptors.ProductCategoryGridRecyclerViewAdaptor;
import ndk.kada.R;
import ndk.kada.recyclerViewAdaptors.StoreListRecyclerViewAdaptor;
import ndk.kada.objectModels.StoreModel;
import ndk.utils_android1.ActivityUtils1;
import ndk.utils_android14.HttpApiSelectTaskWrapper14;
import ndk.utils_android16.SharedPreferenceUtils16;
import ndk.utils_android19.ExceptionUtils19;

public class StorePortalHomeActivity extends KadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_portal_home);

        RecyclerView recyclerViewProductCategoryGrid = findViewById(R.id.recyclerViewProductCategories);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(currentApplicationContext, 3);
        recyclerViewProductCategoryGrid.setLayoutManager(gridLayoutManager);

        ArrayList<String> productCategoryNames = new ArrayList<>();
//        productCategoryNames.add("Grocery");
//        productCategoryNames.add("Vegetables");
//        productCategoryNames.add("Fruits");
//        productCategoryNames.add("Meats");
//        productCategoryNames.add("Fish");
//        productCategoryNames.add("Home Made");

        HttpApiSelectTaskWrapper14.executeNonSplashForegroundPostWithOutParametersAndStatusCheckOnAsyncResponseJsonArrayFirstElement(new KadaApiUtils().getShopCategoriesApiUrl(), currentActivityContext, (View) findViewById(R.id.progressBar), (View) findViewById(R.id.constraintLayout), applicationSpecification.applicationName, jsonArray -> {

            try {

                for (int i = 1; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    productCategoryNames.add(jsonObject.getString("shop_category_name"));
                }
                int categoryIndex = 1;
                while (productCategoryNames.size() < 6) {

                    productCategoryNames.add("Category " + categoryIndex);
                    categoryIndex++;
                }

            } catch (JSONException jsonException) {

                ExceptionUtils19.handleExceptionOnGui(currentApplicationContext, applicationSpecification.applicationName, jsonException);
            }
        });

        recyclerViewProductCategoryGrid.setAdapter(new ProductCategoryGridRecyclerViewAdaptor(currentActivityContext, productCategoryNames));

        RecyclerView recyclerViewStoreList = findViewById(R.id.recyclerViewStores);
        recyclerViewStoreList.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<StoreModel> stores = new ArrayList<>();
//        stores.add(new StoreModel("Georgettante Kada", 4.5F, 1));
//        stores.add(new StoreModel("Georgettante2 Kada", 2.5F, 2));
//        stores.add(new StoreModel("Georgettante Kada", 5, 0.5F));
//        stores.add(new StoreModel("Georgettante Kada", 3.5F, 1));
//        stores.add(new StoreModel("Georgettante Kada", 3, 0.45F));

        HttpApiSelectTaskWrapper14.executeNonSplashForegroundPostWithOutParametersAndStatusCheckOnAsyncResponseJsonArrayFirstElement(new KadaApiUtils().getShopsApiUrl(), currentActivityContext, (View) findViewById(R.id.progressBar), (View) findViewById(R.id.constraintLayout), applicationSpecification.applicationName, jsonArray -> {

            try {

                for (int i = 1; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    stores.add(new StoreModel(jsonObject.getString("shop_name"), 4.5F, 1));
                }
                int storeIndex = 1;
                while (stores.size() < 4) {

                    stores.add(new StoreModel("Store " + storeIndex, 4.5F, 1));
                    storeIndex++;
                }

            } catch (JSONException jsonException) {

                ExceptionUtils19.handleExceptionOnGui(currentApplicationContext, applicationSpecification.applicationName, jsonException);
            }
        });

        recyclerViewStoreList.setAdapter(new StoreListRecyclerViewAdaptor(currentActivityContext, stores));

        FloatingActionButton floatingActionButtonAddStore = findViewById(R.id.floatingActionButtonAddStore);
        floatingActionButtonAddStore.setOnClickListener(v -> {

            HttpApiSelectTaskWrapper14.executeNonSplashForegroundPostWithParametersAndStatusCheckOnAsyncResponseJsonArrayFirstElement(new KadaApiUtils().getShopForUserApiUrl(), new Pair[]{new Pair<>("shopOwnerId", applicationSharedPreferences.getString("userId", "0"))}, currentActivityContext, (View) findViewById(R.id.progressBar), (View) findViewById(R.id.constraintLayout), applicationSpecification.applicationName, jsonArray -> {

                try {

                    JSONObject storeJsonObject = jsonArray.getJSONObject(1);
                    SharedPreferenceUtils16.commitSharedPreferences(applicationSharedPreferences, new androidx.core.util.Pair[]{new androidx.core.util.Pair<>("userShopName", storeJsonObject.getString("shop_name")), new androidx.core.util.Pair<>("userShopLocationLatitude", storeJsonObject.getString("shop_location_latitude")), new androidx.core.util.Pair<>("userShopLocationLongitude", storeJsonObject.getString("shop_location_longitude")), new androidx.core.util.Pair<>("isUserStoreAlreadyAvailable", String.valueOf(true)), new androidx.core.util.Pair<>("userShopId", storeJsonObject.getString("shop_id"))});

                } catch (JSONException jsonException) {

                    ExceptionUtils19.handleExceptionOnGui(currentApplicationContext, applicationSpecification.applicationName, jsonException);
                }
            });

            if (Boolean.parseBoolean(applicationSharedPreferences.getString("isUserStoreAlreadyAvailable", String.valueOf(false)))) {

                ActivityUtils1.startActivityForClass(currentActivityContext, StoreDashboardActivity.class);

            } else {

                ActivityUtils1.startActivityForClass(currentActivityContext, ShopDetailsActivity.class);
            }
        });
    }
}
