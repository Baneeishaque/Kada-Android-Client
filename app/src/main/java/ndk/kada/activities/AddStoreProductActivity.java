package ndk.kada.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.SingleSpinnerListener;
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch;
import com.github.drjacky.imagepicker.ImagePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import ndk.kada.utils.DrawableUtils;
import ndk.kada.utils.KadaApiUtils;
import ndk.kada.objectModels.ProductImageModel;
import ndk.kada.recyclerViewAdaptors.ProductImageRecyclerViewAdaptor;
import ndk.kada.R;
import ndk.utils_android1.ToastUtils1;
import ndk.utils_android14.ActivityUtils14;
import ndk.utils_android14.ButtonUtils14;
import ndk.utils_android14.HttpApiSelectTask14;
import ndk.utils_android14.HttpApiSelectTaskWrapper14;
import ndk.utils_android19.ExceptionUtils19;

public class AddStoreProductActivity extends KadaActivity {

    ArrayList<ProductImageModel> productImages = new ArrayList<>();

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

        String userShopId = applicationSharedPreferences.getString("userShopId", "0");
        ArrayList<KeyPairBoolData> categories = new ArrayList<>();
        HttpApiSelectTaskWrapper14.executeNonSplashForegroundPostWithParametersAndStatusCheckOnAsyncResponseJsonArrayFirstElement(new KadaApiUtils().getCategoriesForShopApiUrl(), new Pair[]{new Pair<>("shopId", userShopId)}, currentActivityContext, (View) findViewById(R.id.progressBar), (View) findViewById(R.id.constraintLayout), applicationSpecification.applicationName, jsonArray -> {

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
        RecyclerView recyclerViewProductImages = (RecyclerView) findViewById(R.id.recyclerViewProductImages);
        ProductImageRecyclerViewAdaptor productImageRecyclerViewAdaptor = new ProductImageRecyclerViewAdaptor(currentActivityContext, productImages);

        // If you want to pass preselected items, you can do it while making listArray,
        // pass true in setSelected of any item that you want to preselect
        singleSpinnerCategory.setItems(categories, new SingleSpinnerListener() {

            @Override
            public void onItemsSelected(KeyPairBoolData selectedItem) {

                if (selectedItem.getObject().toString().equals("1")) {

                    imageButtonCamera.setVisibility(View.VISIBLE);

                } else {

                    imageButtonCamera.setVisibility(View.GONE);
                    productImages.clear();
                    recyclerViewProductImages.setVisibility(View.GONE);
                }
            }

            @Override
            public void onClear() {

            }
        });

        ActivityResultLauncher<Intent> productImagePicker = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {

            if (result.getResultCode() == RESULT_OK) {

                Uri uri = result.getData().getData();

                // Use the uri to load the image
                try {

                    productImages.add(new ProductImageModel(Drawable.createFromStream(getContentResolver().openInputStream(uri), uri.toString())));
                    productImageRecyclerViewAdaptor.notifyDataSetChanged();
                    recyclerViewProductImages.setVisibility(View.VISIBLE);

                } catch (FileNotFoundException e) {

                    e.printStackTrace();
                }

            } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {

                // Use ImagePicker.Companion.getError(result.getData()) to show an error
                ToastUtils1.longToast(currentApplicationContext, ImagePicker.getError(result.getData()));

            } else {

                ToastUtils1.errorToast(currentApplicationContext);
            }
        });

        imageButtonCamera.setOnClickListener(v -> {

            productImagePicker.launch(ImagePicker.with(currentAppCompatActivity)
                    .crop()
                    .galleryOnly() //User can only select image from Gallery
                    .cropFreeStyle()
                    .maxResultSize(256, 256, true)
                    .galleryMimeTypes(new String[]{"image/png", "image/jpg", "image/jpeg"})
                    .createIntent()); //Default Request Code is ImagePicker.REQUEST_CODE
        });

        ButtonUtils14.associateClickAction(currentAppCompatActivity, R.id.buttonAddItem, v -> {

            ArrayList<org.javatuples.Pair<String, String>> httpParametersInJavaTuples = new ArrayList<>();
            httpParametersInJavaTuples.add(org.javatuples.Pair.with("itemName", ((EditText) findViewById(R.id.ediTextItemName)).getText().toString().trim()));
            httpParametersInJavaTuples.add(org.javatuples.Pair.with("itemMaximumRetailPrice", ((EditText) findViewById(R.id.ediTextItemMaximumRetailPrice)).getText().toString().trim()));
            httpParametersInJavaTuples.add(org.javatuples.Pair.with("itemSellingPrice", ((EditText) findViewById(R.id.ediTextItemSellingPrice)).getText().toString().trim()));
            httpParametersInJavaTuples.add(org.javatuples.Pair.with("itemCategory", singleSpinnerCategory.getSelectedIds().get(0).toString()));
            httpParametersInJavaTuples.add(org.javatuples.Pair.with("itemDescription", ((EditText) findViewById(R.id.ediTextItemDescription)).getText().toString().trim()));
            httpParametersInJavaTuples.add(org.javatuples.Pair.with("itemVariants", ((EditText) findViewById(R.id.ediTextItemVariants)).getText().toString().trim()));
            httpParametersInJavaTuples.add(org.javatuples.Pair.with("itemShopId", userShopId));
            httpParametersInJavaTuples.add(org.javatuples.Pair.with("itemImageCount", String.valueOf(productImages.size())));
            for (int i = 0; i < productImages.size(); i++) {

                ByteArrayOutputStream byteArrayOutputStreamObject = new ByteArrayOutputStream();
                Bitmap bitmap = DrawableUtils.drawableToBitmap(productImages.get(i).getImageDrawable());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
                httpParametersInJavaTuples.add(org.javatuples.Pair.with("itemImage" + i, Base64.encodeToString(byteArrayOutputStreamObject.toByteArray(), Base64.DEFAULT)));
            }

            HttpApiSelectTaskWrapper14.executeNonSplashForegroundPostWithParameters(new KadaApiUtils().getInsertShopItemApiUrl(), httpParametersInJavaTuples, currentActivityContext, (View) findViewById(R.id.progressBar), (View) findViewById(R.id.constraintLayout), applicationSpecification.applicationName, (HttpApiSelectTask14.AsyncResponseJSONObject) jsonObject -> {

                try {

                    if (jsonObject.getString("status").equals("0")) {

                        ToastUtils1.longToast(currentApplicationContext, "Item Added Successfully...");
                        ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, AddStoreProductActivity.class);

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
        });

        recyclerViewProductImages.setAdapter(productImageRecyclerViewAdaptor);
        recyclerViewProductImages.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        ButtonUtils14.associateClickAction(currentAppCompatActivity, R.id.buttonProceedToStock, v -> ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, StoreStockActivity.class));
    }
}
