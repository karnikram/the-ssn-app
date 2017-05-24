package karnix.the.ssn.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;

import karnix.the.ssn.ssnmachan.R;

public class IntroActivity extends AppIntro2 {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(SampleSlide.newInstance(R.layout.first_slide));
        addSlide(SampleSlide.newInstance(R.layout.second_slide));
        addSlide(SampleSlide.newInstance(R.layout.third_slide));
        addSlide(SampleSlide.newInstance(R.layout.fourth_slide));
        addSlide(SampleSlide.newInstance(R.layout.fifth_slide));
        addSlide(SampleSlide.newInstance(R.layout.sixth_slide));

        showSkipButton(false);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
