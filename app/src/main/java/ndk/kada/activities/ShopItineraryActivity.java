package ndk.kada.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.github.drjacky.imagepicker.ImagePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import ndk.kada.KadaServerJsonFields;
import ndk.kada.KadaSharedPreferenceKeys;
import ndk.kada.R;
import ndk.kada.recyclerViewAdaptors.ImageViewRecyclerViewAdaptor;
import ndk.kada.recyclerViewAdaptors.ItineraryRecyclerViewAdaptor;
import ndk.kada.utils.KadaApiUtils;
import ndk.utils_android1.ToastUtils1;
import ndk.utils_android14.HttpApiSelectTaskWrapper14;
import ndk.utils_android19.ExceptionUtils19;

public class ShopItineraryActivity extends KadaActivity {

    ArrayList<String> selectedItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_itinerary);

        EditText editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearch.setText("Search essentials, groceries,and more");

        TextView textViewStoreName = findViewById(R.id.textViewStoreName);
        textViewStoreName.setText(applicationSharedPreferences.getString(KadaSharedPreferenceKeys.selectedStoreName, "Store X"));

        TextView textViewStoreRating = findViewById(R.id.textViewStoreRating);
        textViewStoreRating.setText(applicationSharedPreferences.getString(KadaSharedPreferenceKeys.selectedStoreRating, "4.5"));

        TextView textViewStoreAverageDeliveryTime = findViewById(R.id.textViewStoreAverageDeliveryTime);
        textViewStoreAverageDeliveryTime.setText("Fast Delivery Within " + applicationSharedPreferences.getString(KadaSharedPreferenceKeys.selectedStoreAverageDeliveryTime, "1") + " Hour");

        MultiSpinnerSearch multiSelectSpinnerItems = findViewById(R.id.multiSpinnerSearchItems);
        multiSelectSpinnerItems.setColorSeparation(true);
        multiSelectSpinnerItems.setEmptyTitle("No Items");
        multiSelectSpinnerItems.setSearchHint("Type your shopping list");

        Button buttonScanList = findViewById(R.id.buttonScanList);
        TextView textViewOr = findViewById(R.id.textViewOr);
        Button buttonTypeList = findViewById(R.id.buttonTypeList);

        ArrayList<KeyPairBoolData> productItems = new ArrayList<>();
        HttpApiSelectTaskWrapper14.executeNonSplashForegroundPostWithParametersStatusCheckOnAsyncResponseJsonArrayFirstElementCustomizedNoEntriesMessageAndNoEntriesActions(new KadaApiUtils().getItemsForShopApiUrl(), new Pair[]{new Pair<>("shopId", applicationSharedPreferences.getString(KadaSharedPreferenceKeys.selectedStoreId, "0"))}, currentActivityContext, (View) findViewById(R.id.progressBar), (View) findViewById(R.id.constraintLayout), applicationSpecification.applicationName, jsonArray -> {

            try {

                for (int i = 1; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    KeyPairBoolData keyPairBoolData = new KeyPairBoolData(jsonObject.getString(KadaServerJsonFields.itemName), false);
                    keyPairBoolData.setId(jsonObject.getLong(KadaServerJsonFields.itemId));
                    productItems.add(keyPairBoolData);
                }
            } catch (JSONException jsonException) {

                ExceptionUtils19.handleExceptionOnGui(currentApplicationContext, applicationSpecification.applicationName, jsonException);
            }
        }, "No Items...", () -> {

            buttonScanList.setVisibility(View.GONE);
            textViewOr.setVisibility(View.GONE);
            buttonTypeList.setVisibility(View.GONE);
            multiSelectSpinnerItems.setVisibility(View.GONE);
        });

        RecyclerView recyclerViewUserItems = findViewById(R.id.recyclerViewItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewUserItems.setLayoutManager(linearLayoutManager);

        Button buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);

        multiSelectSpinnerItems.setItems(productItems, items -> {

//            for (int i = 0; i < items.size(); i++) {
//
//                if (items.get(i).isSelected()) {
//
//                    Log.i("Kada", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
//                }
//            }

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewUserItems.getContext(), linearLayoutManager.getOrientation());
            recyclerViewUserItems.addItemDecoration(dividerItemDecoration);

            recyclerViewUserItems.setVisibility(View.VISIBLE);
            buttonPlaceOrder.setVisibility(View.VISIBLE);

            buttonScanList.setVisibility(View.GONE);
            textViewOr.setVisibility(View.GONE);
            buttonTypeList.setVisibility(View.GONE);

            selectedItems = new ArrayList<>();
            if (multiSelectSpinnerItems.getSelectedItems().size() > 0) {

                for (KeyPairBoolData selectedItem : multiSelectSpinnerItems.getSelectedItems()) {

                    selectedItems.add(selectedItem.getName());
                }
            } else {

                buttonPlaceOrder.setVisibility(View.GONE);
            }
            ItineraryRecyclerViewAdaptor itineraryRecyclerViewAdaptor = new ItineraryRecyclerViewAdaptor(currentActivityContext, selectedItems);
            recyclerViewUserItems.setAdapter(itineraryRecyclerViewAdaptor);
        });

        ActivityResultLauncher<Intent> productImagePicker = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {

            if (result.getResultCode() == RESULT_OK) {

                Uri uri = result.getData().getData();

                // Use the uri to load the image
                try {

//                    ArrayList<Drawable> images = new ArrayList<>();
//                    images.add(Drawable.createFromStream(getContentResolver().openInputStream(uri), uri.toString()));
//                    ImageViewRecyclerViewAdaptor imageViewRecyclerViewAdaptor = new ImageViewRecyclerViewAdaptor(currentActivityContext, images);
//                    recyclerViewUserItems.setAdapter(imageViewRecyclerViewAdaptor);
//
//                    recyclerViewUserItems.setVisibility(View.VISIBLE);

                    ImageView imageViewScannedList = findViewById(R.id.imageViewScannedList);
                    imageViewScannedList.setImageDrawable(Drawable.createFromStream(getContentResolver().openInputStream(uri), uri.toString()));
                    imageViewScannedList.setVisibility(View.VISIBLE);

                    ConstraintLayout.LayoutParams buttonPlaceOrderLayoutParams = (ConstraintLayout.LayoutParams) buttonPlaceOrder.getLayoutParams();
                    buttonPlaceOrderLayoutParams.topToBottom = imageViewScannedList.getId();
                    buttonPlaceOrder.setLayoutParams(buttonPlaceOrderLayoutParams);
                    buttonPlaceOrder.setVisibility(View.VISIBLE);

                    textViewOr.setVisibility(View.GONE);
                    buttonTypeList.setVisibility(View.GONE);
                    multiSelectSpinnerItems.setVisibility(View.GONE);

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

        buttonScanList.setOnClickListener(v -> {

            productImagePicker.launch(ImagePicker.with(currentAppCompatActivity)
                    .crop()
                    .cameraOnly()
                    .cropFreeStyle()
                    .maxResultSize(linearLayoutManager.getWidth(), 512, true)
                    .galleryMimeTypes(new String[]{"image/png", "image/jpg", "image/jpeg"})
                    .createIntent()); //Default Request Code is ImagePicker.REQUEST_CODE
        });
    }
}
