package ndk.kada;

import android.os.Bundle;

import ndk.utils_android14.ActivityUtils14;
import ndk.utils_android14.ButtonUtils14;

public class SurroundingsPlottedActivity extends KadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surroundings_plotted);

        ButtonUtils14.associateClickAction(currentAppCompatActivity, R.id.buttonOrderNow, v -> ActivityUtils14.startActivityForClassWithFinish(currentActivityContext,StorePortalHomeActivity.class));
    }
}