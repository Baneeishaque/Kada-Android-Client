package ndk.kada;

import android.os.Bundle;
import android.widget.EditText;

import java.util.Random;

import ndk.utils_android1.ActivityUtils1;
import ndk.utils_android14.ButtonUtils14;
import ndk.utils_android16.ValidationUtils16;

public class LoginActivity extends KadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButtonUtils14.associateClickAction(currentAppCompatActivity, R.id.buttonSendOtp, v -> {

            EditText editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
            org.javatuples.Pair<Boolean, EditText> validationResult = ValidationUtils16.mobileNumberCheckEditTextWithRequestFocus(org.javatuples.Pair.with(editTextPhoneNumber, "Invalid Phone Number..."));
            if (validationResult.getValue0()) {

                //TODO : Random Number Generation
                Random rnd = new Random();
                int otp = 100000 + rnd.nextInt(900000);
                applicationLogUtils.debugOnGui("Otp : " + otp);

//                SendOtpNetworkTask.showProgress(true, activityContext, progressBar, constraintLayout);
//
//                //TODO :Check for SMS balance
//                new SendOtpNetworkTask(String.valueOf(otp), voterMobile, activityContext, progressBar, constraintLayout, jsonObject -> {
//
//                    try {
//                        if (jsonObject.getString("return").equals("true")) {
//
//                            Toast.makeText(getApplicationContext(), "Otp send success...", Toast.LENGTH_SHORT).show();
//
//                            Intent intent = new Intent(VoterAuthenticationActivity.this, VoterOtpAuthenticationActivity.class);
//
//                            intent.putExtra("otp", otp);
//                            intent.putExtra("voterMobile", voterMobile);
//                            intent.putExtra("response", response);
//                            intent.putExtra("voterJsonObject", voterJsonObject.toString());
//
//                            String assembly = voterJsonObject.getString("assemblyName");
////                                            editor.putString("assemblyName", assembly);
//                            String parliment = voterJsonObject.getString("parliamentName");
////                                            editor.putString("parliment", parliment);
//                            String voterId = voterJsonObject.getString("voterId");
////                                            editor.putString("voterId", voterId);
////                                            editor.apply();
//
//                            Log.d(ApplicationSpecification.name, "Assembly : " + assembly + ", Parliment : " + parliment + ", Voter : " + voterId);
//
//                            Toast.makeText(getApplicationContext(), "Assembly : " + assembly, Toast.LENGTH_LONG).show();
//                            Toast.makeText(getApplicationContext(), "Parliment : " + parliment, Toast.LENGTH_LONG).show();
//                            Toast.makeText(getApplicationContext(), "Voter ID : " + voterId, Toast.LENGTH_LONG).show();
//
//                            intent.putExtra("assembly", assembly);
//                            intent.putExtra("parliment", parliment);
//                            intent.putExtra("voterId", voterId);
//
//                            startActivity(intent);
//
//                        } else {
//
//                            Toast.makeText(getApplicationContext(), "Otp send failed, try again...", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (JSONException e) {
//
//                        Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }).execute();

                ActivityUtils1.startActivityForClass(currentActivityContext, LocationDemoActivity.class);
            }
        });
    }
}