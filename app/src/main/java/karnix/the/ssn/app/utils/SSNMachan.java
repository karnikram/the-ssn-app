package karnix.the.ssn.app.utils;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

import karnix.the.ssn.app.activity.MainActivity;
import karnix.the.ssn.ssnmachan.R;

public class SSNMachan extends Application {

    @Override
    public void onCreate() {
        Parse.initialize(this, getResources().getString(R.string.application_id), getResources().getString(R.string.client_key));
        PushService.setDefaultPushCallback(this, MainActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
