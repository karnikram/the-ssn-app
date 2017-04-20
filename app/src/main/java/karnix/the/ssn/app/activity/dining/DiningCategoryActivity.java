package karnix.the.ssn.app.activity.dining;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import karnix.the.ssn.app.Fragments.DiningAdapter;
import karnix.the.ssn.ssnmachan.R;

/**
 * Created by adithya321 on 4/20/17.
 */

public class DiningCategoryActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining);
        ButterKnife.bind(this);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getIntent().getStringExtra("place"));

        ListView listView = (ListView) findViewById(R.id.listview);
        final String[] options = getResources().getStringArray(R.array.dining_mess_categories);
        listView.setAdapter(new DiningAdapter(this, options, true));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(DiningCategoryActivity.this, DiningMenuActivity.class)
                        .putExtra("place", getIntent().getStringExtra("place"))
                        .putExtra("category", options[position]));
            }
        });
    }
}
