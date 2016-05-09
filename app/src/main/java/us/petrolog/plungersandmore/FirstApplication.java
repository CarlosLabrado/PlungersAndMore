package us.petrolog.plungersandmore;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Vazh on 9/5/2016.
 */
public class FirstApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
