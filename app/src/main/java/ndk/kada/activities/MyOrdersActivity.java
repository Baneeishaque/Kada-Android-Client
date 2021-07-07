package ndk.kada.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import ndk.kada.R;
import ndk.kada.constants.KadaHttpApiParameters;
import ndk.kada.constants.KadaServerJsonFields;
import ndk.kada.constants.KadaSharedPreferenceKeys;

public class MyOrdersActivity extends PendingOrdersActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        TextView textViewPendingOrders = findViewById(R.id.textViewPendingOrders);
        textViewPendingOrders.setText("My Orders");
    }

    @Override
    public String getOrdersApiUrl() {

        return kadaApiUtils.getOrdersGivenByUserApiUrl();
    }

    @NonNull
    @Override
    public Pair[] getOrderApiParameters() {

        return new Pair[]{new Pair<>(KadaHttpApiParameters.userId, applicationSharedPreferences.getString(KadaSharedPreferenceKeys.userId, "0"))};
    }

    @NonNull
    @Override
    public String getShopOrUserNameFromJsonObject(JSONObject jsonObject) throws JSONException {

        return jsonObject.getString(KadaServerJsonFields.shopName);
    }
}
