package headelement.android.fr.headelement;

import android.app.Activity;
import android.os.Bundle;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new MyActivityLifecycleListener());
    }

}
