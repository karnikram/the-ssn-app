package karnix.the.ssn.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;

import io.fabric.sdk.android.Fabric;
import karnix.the.ssn.ssnmachan.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_splash);

        ImageView hand = (ImageView) findViewById(R.id.hand);

        Animation handAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        handAnim.setInterpolator(new AccelerateInterpolator());
        handAnim.setRepeatCount(Animation.INFINITE);

        hand.startAnimation(handAnim);

        handAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                new Thread(new MyRunnable()).start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(SplashActivity.this, GoogleSignInActivity.class));
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }

            finish();
        }
    }
}