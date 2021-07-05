package ndk.kada;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;

import java.util.Random;

import ndk.utils_android1.ActivityUtils1;
import ndk.utils_android1.ProgressBarUtils1;
import ndk.utils_android1.SharedPreferencesUtils1;
import ndk.utils_android14.ActivityUtils14;
import ndk.utils_android14.ButtonUtils14;
import ndk.utils_android16.SharedPreferenceUtils16;
import ndk.utils_android16.ValidationUtils16;
import ndk.utils_android19.SendSmsFromFast2SmsNetworkTaskWithResponseParserForDk;

public class LoginActivity extends KadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        if (BuildConfig.DEBUG) {

            editTextPhoneNumber.setText("9446827218");
        }

        ButtonUtils14.associateClickAction(currentAppCompatActivity, R.id.buttonSendOtp, v -> {

            String userMobileNumber = editTextPhoneNumber.getText().toString().trim();

            org.javatuples.Pair<Boolean, EditText> validationResult = ValidationUtils16.mobileNumberCheckEditTextWithRequestFocus(org.javatuples.Pair.with(editTextPhoneNumber, "Invalid Phone Number..."));
            if (validationResult.getValue0()) {

                //TODO : Random Number Generation
                Random rnd = new Random();
                int otp = 100000 + rnd.nextInt(900000);
                applicationLogUtils.debugOnGui("Otp : " + otp);

                //TODO : Extract to Utils
                ProgressBar progressBar = findViewById(R.id.progressBar);
                ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
                ProgressBarUtils1.showProgress(true, currentActivityContext, progressBar, constraintLayout);

                new SendSmsFromFast2SmsNetworkTaskWithResponseParserForDk(applicationSpecification.applicationName, currentActivityContext, progressBar, constraintLayout).parseResponseOfSendSmsFromFast2SmsNetworkTask(String.valueOf(otp), userMobileNumber, "Otp send success...", "Otp send failed, try again...", () -> ActivityUtils14.startActivityWithStringExtrasAndFinish(currentActivityContext, OtpActivity.class, new Pair[]{new Pair<>("otp", otp), new Pair<>("userMobileNumber", userMobileNumber)}), () -> {
                });

            }
        });
    }
}