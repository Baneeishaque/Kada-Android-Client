package ndk.kada;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.util.Pair;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SingleSpinnerListener;
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ndk.utils_android1.ActivityUtils1;
import ndk.utils_android14.ButtonUtils14;
import ndk.utils_android14.HttpApiSelectTaskWrapper14;
import ndk.utils_android19.ExceptionUtils19;

public class AddStoreProductActivity extends KadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store_product);

        TextView textViewLearn = findViewById(R.id.textViewLearn);
        textViewLearn.setText("Try Catalog builder to \ncreate your catalog faster");

        SingleSpinnerSearch singleSpinnerCategory = findViewById(R.id.singleSpinnerSearchCategories);
        singleSpinnerCategory.setColorseparation(true);

        // Pass true If you want searchView above the list. Otherwise false. default = true.
        singleSpinnerCategory.setSearchEnabled(false);

        ArrayList<KeyPairBoolData> categories = new ArrayList<>();
        HttpApiSelectTaskWrapper14.executeNonSplashForegroundPostWithParametersAndStatusCheckOnAsyncResponseJsonArrayFirstElement(new KadaApiUtils().getCategoriesForShopApiUrl(), new Pair[]{new Pair<>("shopId", applicationSharedPreferences.getString("userShopId", "0"))}, currentActivityContext, (View) findViewById(R.id.progressBar), (View) findViewById(R.id.constraintLayout), applicationSpecification.applicationName, jsonArray -> {

            try {

                for (int i = 1; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //TODO : Create Constructor for name,isSelected & Id
                    KeyPairBoolData keyPairBoolData = new KeyPairBoolData(jsonObject.getString("shop_category_name"), false);
                    keyPairBoolData.setId(jsonObject.getLong("shop_category_id"));
                    keyPairBoolData.setObject(jsonObject.getString("shop_category_type"));
                    categories.add(keyPairBoolData);
                }
            } catch (JSONException jsonException) {

                ExceptionUtils19.handleExceptionOnGui(currentApplicationContext, applicationSpecification.applicationName, jsonException);
            }
        });

        ImageButton imageButtonCamera = findViewById(R.id.imageButtonCamera);

        // If you want to pass preselected items, you can do it while making listArray,
        // pass true in setSelected of any item that you want to preselect
        singleSpinnerCategory.setItems(categories, new SingleSpinnerListener() {

            @Override
            public void onItemsSelected(KeyPairBoolData selectedItem) {

                if (selectedItem.getObject().toString().equals("1")) {

                    imageButtonCamera.setVisibility(View.VISIBLE);

                } else {

                    imageButtonCamera.setVisibility(View.GONE);
                }
            }

            @Override
            public void onClear() {

            }
        });

        ButtonUtils14.associateClickAction(currentAppCompatActivity, R.id.buttonAddItem, new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                ActivityUtils1.startActivityForClass(currentActivityContext, StoreStockActivity.class);
            }
        });
    }
}