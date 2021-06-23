package ndk.kada;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ndk.utils_android1.ActivityUtils1;
import ndk.utils_android14.ActivityWithContexts;
import ndk.utils_android14.ButtonUtils14;
import ndk.utils_android16.ValidationUtils16;

public class LoginActivity extends ActivityWithContexts {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButtonUtils14.associateClickAction(currentAppCompatActivity, R.id.buttonSendOtp, v -> {

            EditText editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
            org.javatuples.Pair<Boolean, EditText> validationResult = ValidationUtils16.mobileNumberCheckEditTextWithRequestFocus(org.javatuples.Pair.with(editTextPhoneNumber, "Invalid Phone Number..."));
            if (validationResult.getValue0()) {

                ActivityUtils1.startActivityForClass(currentActivityContext, LocationDemoActivity.class);
            }
        });
    }
}