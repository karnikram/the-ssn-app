package karnix.the.ssn.app.activity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import karnix.the.ssn.ssnmachan.R;

public class AboutActivity extends BaseActivity {
    ImageView fb1, fb2, fb3, fb4, fb5, fb7;
    TextView fb6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        fb1 = (ImageView) findViewById(R.id.fb1);
        fb2 = (ImageView) findViewById(R.id.fb2);
        fb3 = (ImageView) findViewById(R.id.fb3);
        fb4 = (ImageView) findViewById(R.id.fb4);
        fb5 = (ImageView) findViewById(R.id.fb5);
        fb6 = (TextView) findViewById(R.id.fb6);
        fb7 = (ImageView) findViewById(R.id.fb7);

        fb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/678829498")));
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.facebook.com/karnik28")));
                }
            }
        });

        fb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                     startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=http://m.facebook.com/adithya321")));
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.facebook.com/adithya321")));
                }
            }
        });

        fb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                     startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=http://m.facebook.com/varun.ranganathan.1")));
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.facebook.com/varun.ranganathan.1")));
                }
            }
        });

        fb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=http://m.facebook.com/muthuannamalai.chidambaram")));
                }
                catch (Exception e)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.facebook.com/muthuannamalai.chidambaram")));
                }
            }
        });

            fb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=http://m.facebook.com/sowbaghyanathan.karthiknathan")));
                }

                catch (Exception e)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.facebook.com/sowbaghyanathan.karthiknathan")));
                }
            }
        });

        fb6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/893173980726657")));
                }

                catch(Exception e)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.facebook.com/ssnmachan")));
                }
            }
        });

        fb7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=http://m.facebook.com/gj.krrish")));
                }

                catch (Exception e)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.facebook.com/gj.krrish")));
                }
            }
        });

    }
}
