package ndk.kada;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ShopItemAdditionActivity extends AppCompatActivity {

    GoogleMap currentGoogleMap;
    MapView mapView;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_item_addition);

        mapView = findViewById(R.id.mapView);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {

            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapViewBundle);

        mapView.getMapAsync(googleMap -> {

            currentGoogleMap = googleMap;
//            currentGoogleMap.setMinZoomPreference(12);
            currentGoogleMap.setIndoorEnabled(true);

            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(-34, 151);
            currentGoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            currentGoogleMap.addMarker(new MarkerOptions().position(new LatLng(-39, 145)).title("Marker in Sydney2"));
            currentGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            UiSettings uiSettings = currentGoogleMap.getUiSettings();
            uiSettings.setIndoorLevelPickerEnabled(true);
            uiSettings.setMyLocationButtonEnabled(true);
            uiSettings.setMapToolbarEnabled(true);
            uiSettings.setCompassEnabled(true);
            uiSettings.setZoomControlsEnabled(true);

//            uiSettings.setRotateGesturesEnabled(false);
//            uiSettings.setScrollGesturesEnabled(false);
//            uiSettings.setTiltGesturesEnabled(false);
//            uiSettings.setZoomGesturesEnabled(false);
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {

            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {

        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {

        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {

        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {

        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {

        super.onLowMemory();
        mapView.onLowMemory();
    }
}
