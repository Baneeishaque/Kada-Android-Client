package ndk.kada;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

public class SendSmsFromFast2SmsNetworkTaskForDk extends SendSmsFromFast2SmsNetworkTaskWithResponseParser {

    public SendSmsFromFast2SmsNetworkTaskForDk(String applicationName, Context currentActivityContext, ProgressBar progressBar, View formView) {

        super(applicationName, currentActivityContext, progressBar, formView);
    }

    @Override
    public String configureUserAuthorizationKey() {

        return DkConstants.fast2SmsAuthorizationKey;
    }
}
