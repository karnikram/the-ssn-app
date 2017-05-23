package karnix.the.ssn.app.activity.alerts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import karnix.the.ssn.app.activity.BaseActivity;
import karnix.the.ssn.ssnmachan.R;


public class AlertsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        Fragment fragment;

        if (getIntent().getStringExtra("type").equals("examcell")) {
            fragment = new AlertsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", getIntent().getStringExtra("type"));
            fragment.setArguments(bundle);
        } else {
            fragment = new AlertsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", getIntent().getStringExtra("type"));
            fragment.setArguments(bundle);
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }
}
