package ndk.kada;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ndk.utils_android14.ActivityUtils14;
import ndk.utils_android14.ButtonUtils14;

public class SurroundingsPlottedActivity extends KadaActivity {

    GoogleMap currentGoogleMap;
    MapView mapView;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surroundings_plotted);

        mapView = findViewById(R.id.mapView);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {

            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapViewBundle);

        mapView.getMapAsync(googleMap -> {

            currentGoogleMap = googleMap;
            currentGoogleMap.setMinZoomPreference(15);
            currentGoogleMap.setIndoorEnabled(true);

            double userLatitude = Double.parseDouble(applicationSharedPreferences.getString("userLatitude", "0"));
            double userLongitude = Double.parseDouble(applicationSharedPreferences.getString("userLongitude", "0"));

            LatLng userLocation = new LatLng(userLatitude, userLongitude);
            currentGoogleMap.addMarker(new MarkerOptions().position(userLocation).title("Me").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            currentGoogleMap.addMarker(new MarkerOptions().position(new LatLng(userLatitude + 0.00082, userLongitude + 0.00082)).title("Kada 1"));
//            currentGoogleMap.addMarker(new MarkerOptions().position(new LatLng(userLatitude + 0.00164, userLongitude + 0.00164)).title("Kada 2"));
            currentGoogleMap.addMarker(new MarkerOptions().position(new LatLng(userLatitude + 0.00243, userLongitude + 0.00243)).title("Kada 3"));
            currentGoogleMap.addMarker(new MarkerOptions().position(new LatLng(userLatitude + 0.00410, userLongitude + 0.00603)).title("Kada 4"));
            currentGoogleMap.addMarker(new MarkerOptions().position(new LatLng(userLatitude + 0.00343, userLongitude + 0.00322)).title("Kada 5"));
            currentGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

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

            CircleOptions userSurroundingsRange = new CircleOptions();
            userSurroundingsRange.center(userLocation);
            userSurroundingsRange.radius(500);
            userSurroundingsRange.strokeColor(Color.GREEN);
            userSurroundingsRange.strokeWidth(2);
            currentGoogleMap.addCircle(userSurroundingsRange);

            MarkerOptions options = new MarkerOptions().position(new LatLng(userLatitude - 0.00162, userLongitude - 0.00162));
            Bitmap bitmap = createUserBitmap();
            if (bitmap != null) {

                options.title("George");
                options.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                options.anchor(0.5f, 0.907f);
                Marker marker = currentGoogleMap.addMarker(options);
//                currentGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(20), 2000, new GoogleMap.CancelableCallback() {
//
//                    @Override
//                    public void onFinish() {
//
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//                });
            }
        });

        ButtonUtils14.associateClickAction(currentAppCompatActivity, R.id.buttonOrderNow, v -> ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, StorePortalHomeActivity.class));
    }

    public int dp(float value) {
        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(getResources().getDisplayMetrics().density * value);
    }

    private Bitmap createUserBitmap() {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(dp(62), dp(76), Bitmap.Config.ARGB_8888);
            result.eraseColor(Color.TRANSPARENT);
            Canvas canvas = new Canvas(result);
            Drawable drawable = getDrawable(R.drawable.livepin);
            drawable.setBounds(0, 0, dp(62), dp(76));
            drawable.draw(canvas);

            Paint roundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            RectF bitmapRect = new RectF();
            canvas.save();

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
            //Bitmap bitmap = BitmapFactory.decodeFile(path.toString()); /*generate bitmap here if your image comes from any url*/
            if (bitmap != null) {
                BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Matrix matrix = new Matrix();
                float scale = dp(52) / (float) bitmap.getWidth();
                matrix.postTranslate(dp(5), dp(5));
                matrix.postScale(scale, scale);
                roundPaint.setShader(shader);
                shader.setLocalMatrix(matrix);
                bitmapRect.set(dp(5), dp(5), dp(52 + 5), dp(52 + 5));
                canvas.drawRoundRect(bitmapRect, dp(26), dp(26), roundPaint);
            }
            canvas.restore();
            try {
                canvas.setBitmap(null);
            } catch (Exception e) {
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
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