package ndk.kada.activities;

import android.os.Bundle;
import android.os.Handler;

import ndk.kada.R;
import ndk.utils_android14.ActivityUtils14;

public class LocationDecidedActivity extends KadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_decided);

        Handler handler = new Handler();
        handler.postDelayed(() -> {

//            ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, LoginActivity.class);
            ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, SurroundingsPlottedActivity.class);

        }, 3000);
    }
}