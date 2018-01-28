package headelement.android.fr.headelement;

import android.app.*;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import headelement.android.fr.headelement.service.HeadElementService;

public class MyActivityLifecycleListener implements Application.ActivityLifecycleCallbacks {

    private int numStarted;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d("APP", "Started" + numStarted);
        if (numStarted == 0) {
            activity.stopService(new Intent(activity.getApplicationContext(), HeadElementService.class));
        }
        numStarted++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        numStarted--;
        if (numStarted == 0) {
            activity.startService(new Intent(activity.getApplicationContext(), HeadElementService.class));
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
