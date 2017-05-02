package karnix.the.ssn.app.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import karnix.the.ssn.app.activity.alerts.AlertsFragment;
import karnix.the.ssn.app.activity.alerts.DepartmentAlertsFragment;

public class MainActivityPagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES = {"ADMIN", "CLUBS", "DEPARTMENT"};

    public MainActivityPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        Bundle bundle;
        switch (position) {
            case 0:
                fragment = new AlertsFragment();
                bundle = new Bundle();
                bundle.putString("type", "admin");
                fragment.setArguments(bundle);
                return fragment;
            case 1:
                fragment = new AlertsFragment();
                bundle = new Bundle();
                bundle.putString("type", "clubs");
                fragment.setArguments(bundle);
                return fragment;
            default:
            case 2:
                return new DepartmentAlertsFragment();
        }
    }
}