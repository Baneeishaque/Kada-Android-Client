package ndk.kada.activities;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.javatuples.Pair;
import org.json.JSONException;

import java.util.ArrayList;

import ndk.kada.BuildConfig;
import ndk.kada.constants.KadaSharedPreferenceKeys;
import ndk.kada.utils.KadaApiUtils;
import ndk.utils_android16.LocationUtils;
import ndk.kada.R;
import ndk.utils_android1.ProgressBarUtils1;
import ndk.utils_android1.ToastUtils1;
import ndk.utils_android14.ActivityUtils14;
import ndk.utils_android14.ButtonUtils14;
import ndk.utils_android14.HttpApiSelectTask14;
import ndk.utils_android14.HttpApiSelectTaskWrapper14;
import ndk.utils_android16.SharedPreferenceUtils16;
import ndk.utils_android16.ValidationUtils16;
import ndk.utils_android19.ExceptionUtils19;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LocationRequestActivity extends KadaActivity {

    private static final int PERMISSION_REQUEST_CODE = 0;

    boolean myLocationFlag = false;
    LocationUtils locationUtils = new LocationUtils(applicationSpecification.applicationName);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_request);

        EditText editTextUserName = findViewById(R.id.editTextUserName);
        if (BuildConfig.DEBUG) {

            editTextUserName.setText("Banee Ishaque K");
        }

        ButtonUtils14.associateClickAction(currentAppCompatActivity, R.id.buttonUseCurrent, v -> {

            if (ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION) != 0) {

                ActivityCompat.requestPermissions(currentAppCompatActivity, new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

            } else {

                ProgressBar progressBar = findViewById(R.id.progressBar);
                ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

                LocationUtils.LocationResult locationResult = new LocationUtils.LocationResult() {

                    @Override
                    public void gotLocation(Location userLocation) {

                        //Got the location!
                        applicationLogUtils.debugOnGui("Location : " + userLocation.toString());
                        ProgressBarUtils1.showProgress(false, currentActivityContext, progressBar, constraintLayout);

                        ArrayList<org.javatuples.Pair<EditText, String>> editTextErrorPairs = new ArrayList<>();
                        editTextErrorPairs.add(Pair.with(editTextUserName, "Please Enter Your Name..."));

                        org.javatuples.Pair<Boolean, EditText> validationResult = ValidationUtils16.nonEmptyCheckEditTextPairs(editTextErrorPairs);
                        if (validationResult.getValue0()) {

                            String userMobileNumber = getIntent().getExtras() != null ? getIntent().getStringExtra("userMobileNumber") : applicationSharedPreferences.getString("userMobileNumber", "0");
                            String userName = editTextUserName.getText().toString().trim();
                            String userLatitude = String.valueOf(userLocation.getLatitude());
                            String userLongitude = String.valueOf(userLocation.getLongitude());

                            HttpApiSelectTaskWrapper14.executeNonSplashForegroundPostWithParameters(new KadaApiUtils().getInsertUserAccountApiUrl(), new androidx.core.util.Pair[]{new androidx.core.util.Pair<>("userName", userName), new androidx.core.util.Pair<>("userMobileNumber", userMobileNumber), new androidx.core.util.Pair<>("userLocationLatitude", userLatitude), new androidx.core.util.Pair<>("userLocationLongitude", userLongitude)}, currentActivityContext, (View) findViewById(R.id.progressBar), (View) findViewById(R.id.constraintLayout), applicationSpecification.applicationName, (HttpApiSelectTask14.AsyncResponseJSONObject) jsonObject -> {

                                try {

                                    switch (jsonObject.getString("status")) {

                                        case "0":
                                        case "2":
//                                            ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, LocationDecidedActivity.class);
                                            SharedPreferenceUtils16.commitSharedPreferences(applicationSharedPreferences, new androidx.core.util.Pair[]{new androidx.core.util.Pair<>(KadaSharedPreferenceKeys.userId, jsonObject.getString("user_id")), new androidx.core.util.Pair<>("loggedUserCurrentStatus", "afterLocation")});
                                            SharedPreferenceUtils16.commitSharedPreferences(applicationSharedPreferences, new androidx.core.util.Pair[]{new androidx.core.util.Pair<>("userMobileNumber", userMobileNumber), new androidx.core.util.Pair<>("userName", userName), new androidx.core.util.Pair<>("userLatitude", userLatitude), new androidx.core.util.Pair<>("userLongitude", userLongitude)});
//                                            ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, StorePortalHomeActivity.class);
                                            ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, SurroundingsPlottedActivity.class);
                                            break;

                                        case "3":
//                                            ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, LocationDecidedActivity.class);
                                            SharedPreferenceUtils16.commitSharedPreferences(applicationSharedPreferences, new androidx.core.util.Pair[]{new androidx.core.util.Pair<>("userId", jsonObject.getString("user_id")), new androidx.core.util.Pair<>("loggedUserCurrentStatus", "afterLocation")});
                                            SharedPreferenceUtils16.commitSharedPreferences(applicationSharedPreferences, new androidx.core.util.Pair[]{new androidx.core.util.Pair<>("userMobileNumber", userMobileNumber), new androidx.core.util.Pair<>("userName", userName), new androidx.core.util.Pair<>("userLatitude", userLatitude), new androidx.core.util.Pair<>("userLongitude", userLongitude)});
                                            SharedPreferenceUtils16.commitSharedPreferences(applicationSharedPreferences, new androidx.core.util.Pair[]{new androidx.core.util.Pair<>("userShopName", jsonObject.getString("shop_name")), new androidx.core.util.Pair<>("userShopLocationLatitude", jsonObject.getString("shop_location_latitude")), new androidx.core.util.Pair<>("userShopLocationLongitude", jsonObject.getString("shop_location_longitude")), new androidx.core.util.Pair<>("userShopCategories", jsonObject.getString("userShopCategories")), new androidx.core.util.Pair<>("isUserStoreAlreadyAvailable", String.valueOf(true)), new androidx.core.util.Pair<>("userShopId", jsonObject.getString("shop_id"))});
//                                            ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, StorePortalHomeActivity.class);
                                            ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, SurroundingsPlottedActivity.class);
                                            break;

                                        case "1":
                                            //TODO : Toast With Logging Process
                                            ToastUtils1.longToast(currentApplicationContext, "Server Error, Please Try Again...");
                                            applicationLogUtils.debugOnGui("Server Error : " + jsonObject.getString("error"));
                                            break;

                                        default:
                                            ToastUtils1.longToast(currentApplicationContext, "Server Error, Please Try Again...");

                                    }
                                } catch (JSONException jsonException) {

                                    ExceptionUtils19.handleExceptionOnGui(currentApplicationContext, applicationSpecification.applicationName, jsonException);
                                }

                            });

                        } else {

                            validationResult.getValue1().requestFocus();
                        }
                    }
                };
                if (locationUtils.getLocation(currentActivityContext, locationResult)) {

                    myLocationFlag = true;
                    ProgressBarUtils1.showProgress(true, currentActivityContext, progressBar, constraintLayout);
                }
            }
        });
    }
}
