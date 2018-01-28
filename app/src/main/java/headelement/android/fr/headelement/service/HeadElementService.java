package headelement.android.fr.headelement.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import headelement.android.fr.headelement.R;

public class HeadElementService extends Service {

    private WindowManager windowManager;
    private ImageView imageView;
    private WindowManager.LayoutParams layoutParams;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        int type;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        else {
            type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }


        layoutParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    type,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);

        layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        layoutParams.x = 0;
        layoutParams.y = 100;

        if (Build.VERSION.SDK_INT > 22) {
            if (Settings.canDrawOverlays(getApplicationContext())) {
                createScreen();
                windowManager.addView(imageView, layoutParams);
            }
        } else {
            createScreen();
            windowManager.addView(imageView, layoutParams);
        }

    }

    private void createScreen() {
        imageView = (ImageView) View.inflate(this, R.layout.head_element, null);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = layoutParams.x;
                        initialY = layoutParams.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        layoutParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                        layoutParams.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(imageView, layoutParams);
                        return true;
                }
                return false;
            }

        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (imageView != null && windowManager != null) {
            windowManager.removeView(imageView);
        }
    }
}
