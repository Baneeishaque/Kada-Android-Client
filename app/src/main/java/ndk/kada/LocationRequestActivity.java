package ndk.kada;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import ndk.utils_android14.ActivityUtils14;
import ndk.utils_android14.ButtonUtils14;

public class LocationRequestActivity extends KadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_request);

        ButtonUtils14.associateClickAction(currentAppCompatActivity, R.id.buttonUseCurrent, v -> ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, LocationDecidedActivity.class));
    }
}