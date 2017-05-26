package karnix.the.ssn.app.activity.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;

import karnix.the.ssn.app.activity.MainActivity;
import karnix.the.ssn.ssnmachan.R;

public class IntroActivity extends AppIntro2 {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(IntroSlideFragment.newInstance(getString(R.string.slide1_title), R.drawable.entrance, getString(R.string.slide1_description)));
        addSlide(IntroSlideFragment.newInstance(getString(R.string.slide2_title), R.drawable.slide2, getString(R.string.slide2_description)));
        addSlide(IntroSlideFragment.newInstance(getString(R.string.slide3_title), R.drawable.slide3, getString(R.string.slide3_description)));
        addSlide(IntroSlideFragment.newInstance(getString(R.string.slide4_title), R.drawable.slide4, getString(R.string.slide4_description)));
        addSlide(IntroSlideFragment.newInstance(getString(R.string.slide5_title), R.drawable.slide5, getString(R.string.slide5_description)));
        addSlide(IntroSlideFragment.newInstance(getString(R.string.slide6_title), R.drawable.slide6, getString(R.string.slide6_description)));
        addSlide(IntroSlideFragment.newInstance(getString(R.string.slide7_title), R.drawable.slide7, getString(R.string.slide7_description)));

        showSkipButton(false);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
