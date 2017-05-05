package karnix.the.ssn.app.activity.dining;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import karnix.the.ssn.app.activity.BaseActivity;
import karnix.the.ssn.app.utils.LogHelper;
import karnix.the.ssn.ssnmachan.R;

public class DiningMenuActivity extends BaseActivity {
    private static final String TAG = LogHelper.makeLogTag(DiningMenuActivity.class);

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_diningMenu_category)
    Spinner spinnerDiningMenuCategory;
    @BindView(R.id.cardView_diningMenu_spinner)
    CardView cardViewDiningMenuSpinner;
    @BindView(R.id.recyclerView_diningMenu_items)
    RecyclerView recyclerViewDiningMenuItems;

    private String place;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_menu);
        ButterKnife.bind(this);

        place = getIntent().getStringExtra("place");

        if (!place.contains("Mess")) {
            cardViewDiningMenuSpinner.setVisibility(View.GONE);
            setRecyclerViewAdapter(null);
            return;
        }

        spinnerDiningMenuCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setRecyclerViewAdapter(parent.getAdapter().getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(place);
    }

    private void setRecyclerViewAdapter(String day) {
        DiningMenuAdapter diningMenuAdapter = new DiningMenuAdapter(this, place, day);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewDiningMenuItems.setLayoutManager(linearLayoutManager);
        diningMenuAdapter.shouldShowHeadersForEmptySections(false);
        recyclerViewDiningMenuItems.setAdapter(diningMenuAdapter);
    }
}
