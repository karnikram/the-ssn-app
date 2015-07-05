package karnix.the.ssn.app.activity;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;

import karnix.the.ssn.ssnmachan.R;

public class AboutActivity extends Activity
{
    RippleView rippleMessage;
    ImageView fb1,fb2;
    TextView fb3;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        rippleMessage = (RippleView)findViewById(R.id.rippleMessage);
        fb1 = (ImageView) findViewById(R.id.fb1);
        fb2 = (ImageView)findViewById(R.id.fb2);
        fb3 = (TextView) findViewById(R.id.fb3);

        rippleMessage.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView)
            {
                startActivity(new Intent(AboutActivity.this,MessageActivity.class));
            }
        });

        fb1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/678829498")));
                }
                catch (Exception e)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.facebook.com/karnik28")));
                }
            }
        });

        fb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100006893923566")));
                }
                catch(Exception e)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://m.facebook.com/krishna.gj.54")));
                }
            }
        });

        fb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/893173980726657")));
                }
                catch(Exception e)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://m.facebook.com/ssnmachan")));
                }
            }
        });


    }
}
