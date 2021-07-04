package ndk.kada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mukesh.OtpView;

import org.json.JSONException;

import java.util.concurrent.ThreadLocalRandom;

import ndk.utils_android1.ActivityUtils1;
import ndk.utils_android1.ProgressBarUtils1;
import ndk.utils_android14.ActivityUtils14;
import ndk.utils_android16.SharedPreferenceUtils16;
import ndk.utils_android19.SendSmsFromFast2SmsNetworkTaskWithResponseParserForDk;

public class OtpActivity extends KadaActivity {

    String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otp = getIntent().getStringExtra("otp");

        // TODO : Auto Read Otp
        findViewById(R.id.buttonVerify).setOnClickListener(v -> {

            OtpView otpView = findViewById(R.id.otpView);

            if (otpView.getText() != null) {

                if (otpView.getText().toString().equals(otp)) {

                    Toast.makeText(getApplicationContext(), "Otp authentication success", Toast.LENGTH_LONG).show();

                    SharedPreferenceUtils16.commitSharedPreferences(applicationSharedPreferences, new Pair[]{new Pair<>("isLoggedUserAvailable", String.valueOf(true)), new Pair<>("loggedUserCurrentStatus", "afterOtp")});
                    ActivityUtils14.startActivityWithStringExtrasAndFinish(currentActivityContext, LocationRequestActivity.class, new Pair[]{new Pair<>("userMobileNumber", getIntent().getStringExtra("userMobileNumber"))});

                } else {

                    Toast.makeText(getApplicationContext(), "Otp authentication failure, try again...", Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.textViewResendIt).setOnClickListener(v -> {

            otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999 + 1));
            Toast.makeText(getApplicationContext(), "Otp : " + otp, Toast.LENGTH_SHORT).show();

            ProgressBar progressBar = findViewById(R.id.progressBar);
            ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
            ProgressBarUtils1.showProgress(true, currentActivityContext, progressBar, constraintLayout);
            new SendSmsFromFast2SmsNetworkTaskWithResponseParserForDk(applicationSpecification.applicationName, currentActivityContext, progressBar, constraintLayout).parseResponseOfSendSmsFromFast2SmsNetworkTask(String.valueOf(otp), getIntent().getStringExtra("userMobileNumber"), "Otp send success...", "Otp send failed, try again...", () -> {
            }, () -> {
            });
        });
    }
}