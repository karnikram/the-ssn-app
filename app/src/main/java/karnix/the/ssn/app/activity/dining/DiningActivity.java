package karnix.the.ssn.app.activity.dining;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import karnix.the.ssn.app.Fragments.DiningAdapter;
import karnix.the.ssn.ssnmachan.R;

public class DiningActivity extends AppCompatActivity {

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
                startActivity(new Intent(DiningActivity.this, DiningCategoryActivity.class)
                        .putExtra("place", options[position]));
            }
        });
    }
}
