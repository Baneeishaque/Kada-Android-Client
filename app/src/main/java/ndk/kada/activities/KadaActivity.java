package ndk.kada.activities;

import ndk.kada.utils.KadaApiUtils;
import ndk.utils_android14.ApplicationActivity14;

public class KadaActivity extends ApplicationActivity14 {

    @Override
    public String configureCurrentApplicationName() {

        return "Kada";
    }

    KadaApiUtils kadaApiUtils = new KadaApiUtils();
}
