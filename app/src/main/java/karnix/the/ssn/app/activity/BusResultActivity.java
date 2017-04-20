package karnix.the.ssn.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import karnix.the.ssn.app.Fragments.DatabaseHandler;
import karnix.the.ssn.ssnmachan.R;

public class BusResultActivity extends Activity {
    private DatabaseHandler localDatabaseHandler;
    private ListView localListView;

    private void loadBusNo(String paramString) {
        localListView = (ListView) findViewById(R.id.listView1);
        String[] arrayOfString = {"stop", "busno", "time"};
        int[] arrayOfInt = {R.id.stop, R.id.bus_no, R.id.time};
        localDatabaseHandler = new DatabaseHandler(getApplicationContext(), "seshadb3.sqlite3");
        localListView.setAdapter(new SimpleAdapter(this, localDatabaseHandler.searareaResu(paramString), R.layout.bus_list_item, arrayOfString, arrayOfInt));
    }

    private void loadArea(String paramString) {
        localListView = (ListView) findViewById(R.id.listView1);
        int i = Integer.parseInt(paramString);
        String[] arrayOfString = {"stop", "busno", "time"};
        int[] arrayOfInt = {R.id.stop, R.id.bus_no, R.id.time};
        localDatabaseHandler = new DatabaseHandler(getApplicationContext(), "seshadb3.sqlite3");
        localListView.setAdapter(new SimpleAdapter(this, localDatabaseHandler.searResu(i), R.layout.bus_list_item, arrayOfString, arrayOfInt));
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        requestWindowFeature(1);
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
