package karnix.the.ssn.app;

import android.app.Application;
import android.os.StrictMode;

import com.facebook.stetho.Stetho;

import karnix.the.ssn.ssnmachan.BuildConfig;

/**
 * Created by adithya321 on 4/20/17.
 */

public class TheSsnApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults();

            Stetho.initializeWithDefaults(this);
        }
    }
}
