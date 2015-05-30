package se.par.amsen.ssn.utils;

import android.app.Application;
import android.util.Log;


import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import se.par.amsen.ssnmachan.R;
import se.par.amsen.ssn.activity.MainActivity;

public class SSNMachan extends Application
{


    @Override
    public void onCreate()
    {
        Parse.initialize(this, getResources().getString(R.string.aplication_id), getResources().getString(R.string.client_key));
        ParsePush.subscribeInBackground("",new SaveCallback() {
            @Override
            public void done(ParseException e)
            {
                if(e == null)
                {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel");
                }
                else
                {
                    Log.e("com.parse.push", "failed to subscribe for push",e);
                }
            }
        });
    }
}
