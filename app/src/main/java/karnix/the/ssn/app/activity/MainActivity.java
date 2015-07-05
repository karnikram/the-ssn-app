package karnix.the.ssn.app.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;

import com.andexert.library.RippleView;

import io.karim.MaterialTabs;
import karnix.the.ssn.app.Fragments.AlertsFragment;
import karnix.the.ssn.app.Fragments.BusFragment;
import karnix.the.ssn.app.Fragments.DiningFragment;
import karnix.the.ssn.ssnmachan.R;


import com.parse.internal.AsyncCallback;


public class MainActivity extends ActionBarActivity
{


    private Toolbar toolbar;
    private MaterialTabs tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;


    RippleView rippleMessage, rippleInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabs = (MaterialTabs) findViewById(R.id.material_tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        rippleMessage = (RippleView) findViewById(R.id.rippleMessage);
        rippleInfo = (RippleView) findViewById(R.id.rippleInfo);






        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.setCurrentItem(1);
        rippleMessage.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener()
        {
            @Override
            public void onComplete(RippleView rippleView)
            {

                startActivity(new Intent(MainActivity.this, MessageActivity.class));

            }
        });
        rippleInfo.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener()
        {
            @Override
            public void onComplete(RippleView rippleView)
            {

                startActivity(new Intent(MainActivity.this, AboutActivity.class));
            }
        });

    }


    public class MyPagerAdapter extends FragmentPagerAdapter
    {

        private final String[] TITLES = {"Buses", "Alerts", "Dining"};

        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return TITLES[position];
        }

        @Override
        public int getCount()
        {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position)
        {
            Fragment f = null;
            switch (position)
            {
                case 0:
                    f = BusFragment.newInstance();
                    break;
                case 1:
                    f = AlertsFragment.newInstance();
                    break;
                case 2:
                    f = DiningFragment.newInstance();
                    break;
            }
            return f;
        }
    }

}