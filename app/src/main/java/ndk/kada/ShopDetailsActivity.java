package ndk.kada;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;

import org.javatuples.Pair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ndk.utils_android1.ToastUtils1;
import ndk.utils_android14.ActivityUtils14;
import ndk.utils_android14.ButtonUtils14;
import ndk.utils_android14.HttpApiSelectTask14;
import ndk.utils_android14.HttpApiSelectTaskWrapper14;
import ndk.utils_android16.SharedPreferenceUtils16;
import ndk.utils_android16.ValidationUtils16;
import ndk.utils_android19.ExceptionUtils19;

public class ShopDetailsActivity extends KadaActivity {

    StringBuilder selectedCategoryIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);

        EditText editTextShopName = findViewById(R.id.editTextShopName);
        if (BuildConfig.DEBUG) {

            editTextShopName.setText("Banee's Kada");
        }
        /*
         * Search MultiSelection Spinner (With Search/Filter Functionality)
         *
         *  Using MultiSpinnerSearch class
         */
        MultiSpinnerSearch multiSelectSpinnerWithSearch = findViewById(R.id.multiSpinnerSearchCategories);
        multiSelectSpinnerWithSearch.setColorSeparation(true);
        multiSelectSpinnerWithSearch.setHintText("Select Shop Categories");
        // Pass true If you want searchView above the list. Otherwise false. default = true.
        multiSelectSpinnerWithSearch.setSearchEnabled(false);

        ArrayList<KeyPairBoolData> categories = new ArrayList<>();
//        categories.add(new KeyPairBoolData("C1", false));
//        categories.add(new KeyPairBoolData("C2", false));
//        categories.add(new KeyPairBoolData("C3", false));
//        categories.add(new KeyPairBoolData("C4", false));
//        categories.add(new KeyPairBoolData("C5", false));

        HttpApiSelectTaskWrapper14.executeNonSplashForegroundPostWithOutParametersAndStatusCheckOnAsyncResponseJsonArrayFirstElement(new KadaApiUtils().getShopCategoriesApiUrl(), currentActivityContext, (View) findViewById(R.id.progressBar), (View) findViewById(R.id.constraintLayout), applicationSpecification.applicationName, jsonArray -> {

            try {

                for (int i = 1; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //TODO : Create Constructor for name,isSelected & Id
                    KeyPairBoolData keyPairBoolData = new KeyPairBoolData(jsonObject.getString("shop_category_name"), false);
                    keyPairBoolData.setId(jsonObject.getLong("shop_category_id"));
                    categories.add(keyPairBoolData);
                }
            } catch (JSONException jsonException) {

                ExceptionUtils19.handleExceptionOnGui(currentApplicationContext, applicationSpecification.applicationName, jsonException);
            }
        });

        // If you want to pass preselected items, you can do it while making listArray,
        // pass true in setSelected of any item that you want to preselect
        multiSelectSpinnerWithSearch.setItems(categories, items -> {

            for (int i = 0; i < items.size(); i++) {

                if (items.get(i).isSelected()) {

                    Log.i("Kada", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                }
            }
        });

        ButtonUtils14.associateClickAction(currentAppCompatActivity, R.id.buttonNext, v -> {

            ArrayList<Pair<EditText, String>> editTextErrorPairs = new ArrayList<>();
            editTextErrorPairs.add(Pair.with(editTextShopName, "Please Enter Your Shop Name..."));
            Pair<Boolean, EditText> validationResult = ValidationUtils16.nonEmptyCheckEditTextPairs(editTextErrorPairs);

            if (validationResult.getValue0()) {

                //TODO : Extract to Utils
                if (multiSelectSpinnerWithSearch.getSelectedItems().size() == 0) {

                    ToastUtils1.longToast(currentApplicationContext, "Please select your shop category...");

                } else {

                    List<Long> selectedCategories = multiSelectSpinnerWithSearch.getSelectedIds();
                    selectedCategoryIds = new StringBuilder();
                    for (int i = 0; i < selectedCategories.size(); i++) {

                        long selectCategoryId = selectedCategories.get(i);
                        if (i == 0) {

                            selectedCategoryIds = new StringBuilder(String.valueOf(selectCategoryId));

                        } else {

                            selectedCategoryIds.append(",").append(selectCategoryId);
                        }
                    }
                    applicationLogUtils.debugOnGui("Selected Category IDs : " + selectedCategoryIds);

                    String userShopName = editTextShopName.getText().toString();
                    String userShopLocationLatitude = applicationSharedPreferences.getString("userLatitude", "0");
                    String userShopLocationLongitude = applicationSharedPreferences.getString("userLongitude", "0");

                    HttpApiSelectTaskWrapper14.executeNonSplashForegroundPostWithParameters(new KadaApiUtils().getInsertShopApiUrl(), new androidx.core.util.Pair[]{new androidx.core.util.Pair<>("shopName", userShopName), new androidx.core.util.Pair<>("shopOwnerId", applicationSharedPreferences.getString("userId", "0")), new androidx.core.util.Pair<>("shopLocationLatitude", userShopLocationLatitude), new androidx.core.util.Pair<>("shopLocationLongitude", userShopLocationLongitude), new androidx.core.util.Pair<>("shopCategories", selectedCategoryIds)}, currentActivityContext, (View) findViewById(R.id.progressBar), (View) findViewById(R.id.constraintLayout), applicationSpecification.applicationName, (HttpApiSelectTask14.AsyncResponseJSONObject) jsonObject -> {

                        try {

                            if (jsonObject.getString("status").equals("0")) {

                                ToastUtils1.longToast(currentApplicationContext, "Shop Added Successfully...");
                                SharedPreferenceUtils16.commitSharedPreferences(applicationSharedPreferences, new androidx.core.util.Pair[]{new androidx.core.util.Pair<>("userShopName", editTextShopName.getText().toString()), new androidx.core.util.Pair<>("userShopLocationLatitude", userShopLocationLatitude), new androidx.core.util.Pair<>("userShopLocationLongitude", userShopLocationLongitude), new androidx.core.util.Pair<>("userShopCategories", selectedCategoryIds), new androidx.core.util.Pair<>("isUserStoreAlreadyAvailable", String.valueOf(true))});

                                ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, StoreDashboardActivity.class);

                            } else if (jsonObject.getString("status").equals("1")) {

                                //TODO : Toast With Logging Process
                                ToastUtils1.longToast(currentApplicationContext, "Server Error, Please Try Again...");
                                applicationLogUtils.debugOnGui("Server Error : " + jsonObject.getString("error"));

                            } else {

                                ToastUtils1.longToast(currentApplicationContext, "Server Error, Please Try Again...");
                            }

                        } catch (JSONException jsonException) {

                            ExceptionUtils19.handleExceptionOnGui(currentApplicationContext, applicationSpecification.applicationName, jsonException);
                        }
                    });
                }
            } else {

                validationResult.getValue1().requestFocus();
            }
        });
    }
}
