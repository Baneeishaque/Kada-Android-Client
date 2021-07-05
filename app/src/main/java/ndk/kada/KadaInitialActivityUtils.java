package ndk.kada;

import android.content.Context;
import android.content.SharedPreferences;

import ndk.utils_android14.ActivityUtils14;

public class KadaInitialActivityUtils {

    public static void initialScreenNavigation(SharedPreferences applicationSharedPreferences, Context currentActivityContext) {

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
    }
}
