package karnix.the.ssn.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.andexert.library.RippleView;

import karnix.the.ssn.ssnmachan.R;
import karnix.the.ssn.app.Fragments.DatabaseHandler;

public class BusResultActivity extends Activity
{
    private static final String DB_NAME = "seshadb3.sqlite3";
    private ListView localListView;
    private static DatabaseHandler localDatabaseHandler;
    private static RippleView rippleMessage,rippleInfo;

    private void loadBusNo(String paramString)
    {
        localListView = (ListView) findViewById(R.id.listView1);
        String[] arrayOfString = {"stop", "busno", "time"};
        int[] arrayOfInt = {R.id.stop, R.id.bus_no, R.id.time};
        localDatabaseHandler = new DatabaseHandler(getApplicationContext(), "seshadb3.sqlite3");
        localListView.setAdapter(new SimpleAdapter(this, localDatabaseHandler.searareaResu(paramString), R.layout.bus_list_item, arrayOfString, arrayOfInt));
    }

    private void loadArea(String paramString)
    {
        localListView = (ListView) findViewById(R.id.listView1);
        int i = Integer.parseInt(paramString);
        String[] arrayOfString = {"stop", "busno", "time"};
        int[] arrayOfInt = {R.id.stop, R.id.bus_no, R.id.time};
        localDatabaseHandler = new DatabaseHandler(getApplicationContext(), "seshadb3.sqlite3");
        localListView.setAdapter(new SimpleAdapter(this, localDatabaseHandler.searResu(i), R.layout.bus_list_item, arrayOfString, arrayOfInt));
    }

    public void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        requestWindowFeature(1);
        setContentView(R.layout.bus_result);
        Bundle localBundle = getIntent().getExtras();
        String checkValue = localBundle.getString("Value1").trim();
        String params = localBundle.getString("Value2").trim();
        rippleInfo = (RippleView)findViewById(R.id.rippleInfo);
        rippleMessage = (RippleView)findViewById(R.id.rippleMessage);

        if (Integer.parseInt(checkValue) == 1)
        {
            loadArea(params);
            return;
        }
        loadBusNo(params);

        rippleMessage.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener()
        {
            @Override
            public void onComplete(RippleView rippleView)
            {
                startActivity(new Intent(BusResultActivity.this, MessageActivity.class));
            }
        });

        rippleInfo.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView)
            {
                startActivity(new Intent(BusResultActivity.this, AboutActivity.class));
            }
        });

    }

}
