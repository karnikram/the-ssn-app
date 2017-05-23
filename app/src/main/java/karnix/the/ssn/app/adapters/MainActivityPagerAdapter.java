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
        Bundle bundle;
        switch (position) {
            case 0:
                Fragment adminAlertsFragment = new AlertsFragment();
                bundle = new Bundle();
                bundle.putString("type", "admin");
                adminAlertsFragment.setArguments(bundle);
                return adminAlertsFragment;

            case 1:
                Fragment clubsAlertsFragment = new AlertsFragment();
                bundle = new Bundle();
                bundle.putString("type", "clubs");
                clubsAlertsFragment.setArguments(bundle);
                return clubsAlertsFragment;

            default:
            case 2:
                return new DepartmentAlertsFragment();
        }
    }
}