package ndk.kada;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.widget.TextView;

import ndk.utils_android14.ActivityUtils14;

public class LauncherActivity extends KadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        //TODO : Derive Custom Widget - Horizontal Gradient TextView
        TextView textViewInboxSolutions = findViewById(R.id.textViewInboxSolutions);
        TextPaint textPaintInboxSolutions = textViewInboxSolutions.getPaint();

        //TODO : getMultiColorHorizontalLinearGradientForTextView
        Shader shaderInboxSolutions = new LinearGradient(0, 0, textPaintInboxSolutions.measureText(getString(R.string.inbox_solutions)), textViewInboxSolutions.getTextSize(), new int[]{
                getResources().getColor(R.color.red),
                getResources().getColor(R.color.sandyBrown),
                getResources().getColor(R.color.rose)
        }, null, Shader.TileMode.CLAMP);
        textViewInboxSolutions.getPaint().setShader(shaderInboxSolutions);

        //TODO : Timer Activity
        Handler handler = new Handler();
        handler.postDelayed(() -> {

            if (Boolean.parseBoolean(applicationSharedPreferences.getString("isLoggedUserAvailable", String.valueOf(false)))) {

                switch (applicationSharedPreferences.getString("loggedUserCurrentStatus", "")) {

                    case "afterOtp":
                        ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, LocationRequestActivity.class);
                        break;

                    case "afterLocation":
                        ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, StorePortalHomeActivity.class);
                        break;

                    default:
                        ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, LoginActivity.class);
                }
            } else {

                ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, LoginActivity.class);
            }

//            ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, OtpActivity.class);
//            ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, LocationDemoActivity.class);
//            ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, LocationRequestActivity.class);
//            ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, StorePortalHomeActivity.class);
//            ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, ShopDetailsActivity.class);
//            ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, StoreDashboardActivity.class);
//            ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, AddStoreProductActivity.class);
//            ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, StoreStockActivity.class);

        }, 500);
    }
}