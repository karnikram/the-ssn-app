package karnix.the.ssn.app.activity.bus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import karnix.the.ssn.app.activity.BaseActivity;
import karnix.the.ssn.app.model.DatabaseHandler;
import karnix.the.ssn.ssnmachan.R;

public class BusActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button numSubmit = (Button) findViewById(R.id.num_submit);
        Button areaSubmit = (Button) findViewById(R.id.area_submit);
        final EditText number = (EditText) findViewById(R.id.number);
        ArrayAdapter localAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                new DatabaseHandler(this).getAllLabels());

        final AutoCompleteTextView localAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.area);
        localAutoCompleteTextView.setThreshold(1);
        localAutoCompleteTextView.setAdapter(localAdapter);
        numSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = number.getText().toString();
                number.setText("");
                if (str.trim().length() > 0) {
                    Intent localIntent = new Intent(BusActivity.this, BusResultActivity.class);
                    localIntent.putExtra("Value1", "1");
                    localIntent.putExtra("Value2", str);
                    startActivity(localIntent);
                    return;
                }
                Toast.makeText(BusActivity.this, "Invalid input", Toast.LENGTH_LONG).show();
            }
        });

        areaSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setText("");
                String str = localAutoCompleteTextView.getText().toString();
                localAutoCompleteTextView.setText("");
                if (str.trim().length() > 0) {
                    Intent localIntent = new Intent(BusActivity.this, BusResultActivity.class);
                    localIntent.putExtra("Value1", "2");
                    localIntent.putExtra("Value2", str);
                    startActivity(localIntent);
                    return;
                }
                Toast.makeText(BusActivity.this, "Invalid input", Toast.LENGTH_LONG).show();
            }
        });
    }
}
