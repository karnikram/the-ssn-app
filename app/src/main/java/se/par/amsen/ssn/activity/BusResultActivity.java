package se.par.amsen.ssn.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import se.par.amsen.ssnmachan.R;
import se.par.amsen.ssn.Fragments.DatabaseHandler;

public class BusResultActivity extends Activity
{
    private static final String DB_NAME = "seshadb3.sqlite3";
    private static ImageView back;
    private ListView localListView;
    private static DatabaseHandler localDatabaseHandler;

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
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        if (Integer.parseInt(checkValue) == 1)
        {
            loadArea(params);
            return;
        }
        loadBusNo(params);

    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

}
