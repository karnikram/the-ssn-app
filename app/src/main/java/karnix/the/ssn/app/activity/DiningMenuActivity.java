package karnix.the.ssn.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;

import karnix.the.ssn.ssnmachan.R;

public class DiningMenuActivity extends Activity {
    private int position;
    private TextView menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        menu = (TextView) findViewById(R.id.menu_name);
        position = getIntent().getIntExtra("position", 0);
        setMenu();
    }

    public void setMenu() {
        switch (position) {
            case 0:
                menu.setText("Ladies mess menu");
                break;
            case 1:
                menu.setText("PG mess Menu");
                break;

            case 2:
                menu.setText("UG mess Menu");
                break;
            case 3:
                menu.setText("Stores Menu");
                break;
        }
    }

    public int dayPage() {
        switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                return 7;
            case Calendar.MONDAY:
                return 1;
            case Calendar.TUESDAY:
                return 2;
            case Calendar.WEDNESDAY:
                return 3;
            case Calendar.THURSDAY:
                return 4;
            case Calendar.FRIDAY:
                return 5;
            case Calendar.SATURDAY:
                return 6;
        }
        return 0;
    }
}
