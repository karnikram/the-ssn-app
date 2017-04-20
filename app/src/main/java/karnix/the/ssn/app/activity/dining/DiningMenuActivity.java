package karnix.the.ssn.app.activity.dining;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import karnix.the.ssn.ssnmachan.R;

public class DiningMenuActivity extends Activity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list)
    RecyclerView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_menu);
        ButterKnife.bind(this);

        String place = getIntent().getStringExtra("place");
        String category = getIntent().getStringExtra("category");

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(place + " ->" + category);

        String[] sections = {"Breakfast", "Lunch", "Snacks", "Dinner"};

        DiningMenuAdapter diningMenuAdapter = new DiningMenuAdapter(sections);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(linearLayoutManager);
        diningMenuAdapter.shouldShowHeadersForEmptySections(false);
        list.setAdapter(diningMenuAdapter);
    }
}
