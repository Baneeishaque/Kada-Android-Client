package ndk.kada;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        TextView textViewInboxSolutions = findViewById(R.id.textViewInboxSolutions);
        TextPaint textPaintInboxSolutions = textViewInboxSolutions.getPaint();
        Shader shaderInboxSolutions = new LinearGradient(0, 0, textPaintInboxSolutions.measureText(getString(R.string.inbox_solutions)), textViewInboxSolutions.getTextSize(), new int[]{
                getColor(R.color.kada_launcher_gradient_start),
                getColor(R.color.kada_launcher_gradient_center),
                getColor(R.color.kada_launcher_gradient_end)
        }, null, Shader.TileMode.CLAMP);
        textViewInboxSolutions.getPaint().setShader(shaderInboxSolutions);
    }
}