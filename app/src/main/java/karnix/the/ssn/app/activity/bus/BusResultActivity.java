package karnix.the.ssn.app.activity.bus;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import karnix.the.ssn.app.activity.BaseActivity;
import karnix.the.ssn.app.model.DatabaseHandler;
import karnix.the.ssn.ssnmachan.R;

public class BusResultActivity extends BaseActivity {

    private DatabaseHandler localDatabaseHandler;
    private ListView localListView;

    private void loadBusNo(String paramString) {
        localListView = (ListView) findViewById(R.id.listView1);
        String[] arrayOfString = {"stop", "busno", "time"};
        int[] arrayOfInt = {R.id.stop, R.id.bus_no, R.id.time};
        localDatabaseHandler = new DatabaseHandler(getApplicationContext());
        localListView.setAdapter(new SimpleAdapter(this, localDatabaseHandler.searareaResu(paramString), R.layout.bus_list_item, arrayOfString, arrayOfInt));
    }

    private void loadArea(String paramString) {
        localListView = (ListView) findViewById(R.id.listView1);
        int i = Integer.parseInt(paramString);
        String[] arrayOfString = {"stop", "busno", "time"};
        int[] arrayOfInt = {R.id.stop, R.id.bus_no, R.id.time};
        localDatabaseHandler = new DatabaseHandler(getApplicationContext());
        localListView.setAdapter(new SimpleAdapter(this, localDatabaseHandler.searResu(i), R.layout.bus_list_item, arrayOfString, arrayOfInt));
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_bus_result);

        Bundle localBundle = getIntent().getExtras();
        String checkValue = localBundle.getString("Value1").trim();
        String params = localBundle.getString("Value2").trim();

        if (Integer.parseInt(checkValue) == 1) {
            loadArea(params);
            return;
        }
        loadBusNo(params);
    }
}
