package karnix.the.ssn.app.activity.dining;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import karnix.the.ssn.app.activity.BaseActivity;
import karnix.the.ssn.ssnmachan.R;

public class DiningActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining);

        ListView listView = (ListView) findViewById(R.id.listview);
        final String[] options = getResources().getStringArray(R.array.dining_options);
        listView.setAdapter(new DiningAdapter(this, options, false));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(DiningActivity.this, DiningMenuActivity.class)
                        .putExtra("place", options[position]));
            }
        });
    }
}
