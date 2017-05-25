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

        addSlide(SampleSlide.newInstance(R.layout.intro_first_slide));
        addSlide(SampleSlide.newInstance(R.layout.intro_second_slide));
        addSlide(SampleSlide.newInstance(R.layout.intro_third_slide));
        addSlide(SampleSlide.newInstance(R.layout.intro_fourth_slide));
        addSlide(SampleSlide.newInstance(R.layout.intro_fifth_slide));
        addSlide(SampleSlide.newInstance(R.layout.intro_sixth_slide));
        addSlide(SampleSlide.newInstance(R.layout.intro_seventh_slide));

        showSkipButton(false);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
