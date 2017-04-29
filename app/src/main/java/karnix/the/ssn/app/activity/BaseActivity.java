package karnix.the.ssn.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import karnix.the.ssn.ssnmachan.R;

/**
 * Created by adithya321 on 4/25/17.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.action_feed:
                startActivity(new Intent(this, NewsFeedActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
