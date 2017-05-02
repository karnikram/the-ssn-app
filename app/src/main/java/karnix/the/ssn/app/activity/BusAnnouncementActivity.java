package karnix.the.ssn.app.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import karnix.the.ssn.app.Fragments.BusAnnouncementFragment;
import karnix.the.ssn.ssnmachan.R;



public class BusAnnouncementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_announcement);

        getSupportFragmentManager().beginTransaction().replace(R.id.bus_announcement_frame_layout,new BusAnnouncementFragment()).commit();

    }
}
