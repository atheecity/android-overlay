package headelement.android.fr.headelement;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        if (Build.VERSION.SDK_INT > 22) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                }
            });
        } else {
            textView2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT > 22) {
            button.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);

            if (!Settings.canDrawOverlays(getApplicationContext())) {
                button.setText(getResources().getString(R.string.activate));
                textView.setText(getResources().getString(R.string.activate_overlay));
                textView2.setVisibility(View.GONE);
            } else {
                button.setText(getResources().getString(R.string.deactivate));
                textView.setText(getResources().getString(R.string.deactivate_overlay));
                textView2.setVisibility(View.VISIBLE);
            }
        } else {
            textView2.setVisibility(View.VISIBLE);
        }
    }
}
