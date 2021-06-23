package ndk.kada;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;

import ndk.utils_android1.ActivityUtils1;
import ndk.utils_android1.ProgressBarUtils1;
import ndk.utils_android14.ButtonUtils14;
import ndk.utils_android16.ValidationUtils16;

public class LoginActivity extends KadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButtonUtils14.associateClickAction(currentAppCompatActivity, R.id.buttonSendOtp, v -> {

            EditText editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
            org.javatuples.Pair<Boolean, EditText> validationResult = ValidationUtils16.mobileNumberCheckEditTextWithRequestFocus(org.javatuples.Pair.with(editTextPhoneNumber, "Invalid Phone Number..."));
            if (validationResult.getValue0()) {

                //TODO : Random Number Generation
                Random rnd = new Random();
                int otp = 100000 + rnd.nextInt(900000);
                applicationLogUtils.debugOnGui("Otp : " + otp);

                ProgressBar progressBar = findViewById(R.id.progressBar);
                ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
                ProgressBarUtils1.showProgress(true, currentActivityContext, progressBar, constraintLayout);

                new SendSmsFromFast2SmsNetworkTaskWithResponseParserForDk(applicationSpecification.applicationName, currentActivityContext, progressBar, constraintLayout).parseResponseOfSendSmsFromFast2SmsNetworkTask(String.valueOf(otp), editTextPhoneNumber.getText().toString(), "Otp send success...", "Otp send failed, try again...", () -> ActivityUtils1.startActivityForClass(currentActivityContext, LocationDemoActivity.class), () -> {
                });
            }
        });
    }
}