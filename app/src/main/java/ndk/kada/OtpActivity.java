package ndk.kada;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;

import com.mukesh.OtpView;

import org.json.JSONException;

import java.util.concurrent.ThreadLocalRandom;

import ndk.utils_android1.ProgressBarUtils1;
import ndk.utils_android1.ToastUtils1;
import ndk.utils_android14.HttpApiSelectTask14;
import ndk.utils_android14.HttpApiSelectTaskWrapper14;
import ndk.utils_android16.SharedPreferenceUtils16;
import ndk.utils_android19.ExceptionUtils19;
import ndk.utils_android19.SendSmsFromFast2SmsNetworkTaskWithResponseParserForDk;

public class OtpActivity extends KadaActivity {

    String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otp = getIntent().getStringExtra("otp");

        ProgressBar progressBar = findViewById(R.id.progressBar);
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

        // TODO : Auto Read Otp
        findViewById(R.id.buttonVerify).setOnClickListener(v -> {

            OtpView otpView = findViewById(R.id.otpView);

            if (otpView.getText() != null) {

                if (otpView.getText().toString().equals(otp)) {

                    Toast.makeText(getApplicationContext(), "Otp authentication success", Toast.LENGTH_LONG).show();

                    SharedPreferenceUtils16.commitSharedPreferences(applicationSharedPreferences, new Pair[]{new Pair<>("isLoggedUserAvailable", String.valueOf(true)), new Pair<>("loggedUserCurrentStatus", "afterOtp"), new Pair<>("userMobileNumber", getIntent().getStringExtra("userMobileNumber"))});

                    HttpApiSelectTaskWrapper14.executeNonSplashForegroundPostWithParameters(new KadaApiUtils().getUserForMobileNumberApiUrl(), new androidx.core.util.Pair[]{new androidx.core.util.Pair<>("userMobileNumber", getIntent().getStringExtra("userMobileNumber"))}, currentActivityContext, progressBar, constraintLayout, applicationSpecification.applicationName, (HttpApiSelectTask14.AsyncResponseJSONObject) jsonObject -> {

                        try {

                            switch (jsonObject.getString("status")) {

                                case "0":
                                    break;

                                case "1":
                                    SharedPreferenceUtils16.commitSharedPreferences(applicationSharedPreferences, new androidx.core.util.Pair[]{new androidx.core.util.Pair<>("userId", jsonObject.getString("user_id")), new androidx.core.util.Pair<>("loggedUserCurrentStatus", "afterLocation")});
                                    SharedPreferenceUtils16.commitSharedPreferences(applicationSharedPreferences, new androidx.core.util.Pair[]{new androidx.core.util.Pair<>("userName", jsonObject.getString("user_name")), new androidx.core.util.Pair<>("userLatitude", jsonObject.getString("user_location_latitude")), new androidx.core.util.Pair<>("userLongitude", jsonObject.getString("user_location_longitude"))});
                                    break;

                                case "2":
                                    SharedPreferenceUtils16.commitSharedPreferences(applicationSharedPreferences, new androidx.core.util.Pair[]{new androidx.core.util.Pair<>("userId", jsonObject.getString("user_id")), new androidx.core.util.Pair<>("loggedUserCurrentStatus", "afterLocation")});
                                    SharedPreferenceUtils16.commitSharedPreferences(applicationSharedPreferences, new androidx.core.util.Pair[]{new androidx.core.util.Pair<>("userName", jsonObject.getString("user_name")), new androidx.core.util.Pair<>("userLatitude", jsonObject.getString("user_location_latitude")), new androidx.core.util.Pair<>("userLongitude", jsonObject.getString("user_location_longitude"))});
                                    SharedPreferenceUtils16.commitSharedPreferences(applicationSharedPreferences, new androidx.core.util.Pair[]{new androidx.core.util.Pair<>("userShopName", jsonObject.getString("shop_name")), new androidx.core.util.Pair<>("userShopLocationLatitude", jsonObject.getString("shop_location_latitude")), new androidx.core.util.Pair<>("userShopLocationLongitude", jsonObject.getString("shop_location_longitude")), new androidx.core.util.Pair<>("userShopCategories", jsonObject.getString("userShopCategories")), new androidx.core.util.Pair<>("isUserStoreAlreadyAvailable", String.valueOf(true)), new androidx.core.util.Pair<>("userShopId", jsonObject.getString("shop_id"))});
                                    break;

                                default:
                                    ToastUtils1.longToast(currentApplicationContext, "Server Error, Please Try Again...");
                            }
                        } catch (JSONException jsonException) {

                            ExceptionUtils19.handleExceptionOnGui(currentApplicationContext, applicationSpecification.applicationName, jsonException);
                        }
                    });

                    KadaInitialActivityUtils.initialScreenNavigation(applicationSharedPreferences, currentActivityContext);

                } else {

                    Toast.makeText(getApplicationContext(), "Otp authentication failure, try again...", Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.textViewResendIt).setOnClickListener(v -> {

            otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999 + 1));
            Toast.makeText(getApplicationContext(), "Otp : " + otp, Toast.LENGTH_SHORT).show();

            ProgressBarUtils1.showProgress(true, currentActivityContext, progressBar, constraintLayout);
            new SendSmsFromFast2SmsNetworkTaskWithResponseParserForDk(applicationSpecification.applicationName, currentActivityContext, progressBar, constraintLayout).parseResponseOfSendSmsFromFast2SmsNetworkTask(String.valueOf(otp), getIntent().getStringExtra("userMobileNumber"), "Otp send success...", "Otp send failed, try again...", () -> {
            }, () -> {
            });
        });
    }
}