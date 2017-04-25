package karnix.the.ssn.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by adithya321 on 4/25/17.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }
}
