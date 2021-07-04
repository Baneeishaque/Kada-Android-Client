package ndk.kada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.javatuples.Pair;

import java.util.ArrayList;

import ndk.utils_android1.ProgressBarUtils1;
import ndk.utils_android14.ActivityUtils14;
import ndk.utils_android14.ButtonUtils14;
import ndk.utils_android16.ValidationUtils16;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LocationRequestActivity extends KadaActivity {

    private static final int PERMISSION_REQUEST_CODE = 0;

    boolean myLocationFlag = false;
    LocationUtils locationUtils = new LocationUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_request);

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

                        EditText editTextUserName = findViewById(R.id.editTextUserName);

                        ArrayList<org.javatuples.Pair<EditText, String>> editTextErrorPairs = new ArrayList<>();
                        editTextErrorPairs.add(Pair.with(editTextUserName, "Please Enter Your Name..."));

                        org.javatuples.Pair<Boolean, EditText> validationResult = ValidationUtils16.nonEmptyCheckEditTextPairs(editTextErrorPairs);
                        if (validationResult.getValue0()) {

                            applicationLogUtils.debugOnGui("API Data : User - " + getIntent().getStringExtra("mobileNumber") + ", Name : " + editTextUserName.getText().toString() + ", Latitude : " + userLocation.getLatitude() + ", Longitude : " + userLocation.getLongitude());

                            //            ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, LocationDecidedActivity.class);
//                        ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, StorePortalHomeActivity.class);

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