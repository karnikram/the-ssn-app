package se.par.amsen.ssn.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

import java.util.Calendar;

import se.par.amsen.ssnmachan.R;

public class DiningMenuActivity extends Activity implements  OnPageChangeListener
{
    private static int position;
    private static ImageView close;
    private static TextView menu,pgNo;
    private static PDFView pdfView;
    private static Typeface advFont;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        advFont = Typeface.createFromAsset(getAssets(), "fonts/Adventure.otf");
        pdfView = (PDFView) findViewById(R.id.pdfview);
        menu = (TextView)findViewById(R.id.menu_name);
        menu.setTypeface(advFont);
        pgNo = (TextView)findViewById(R.id.pg_no);
        pgNo.setTypeface(advFont);
        position = getIntent().getIntExtra("position", 0);
        close = (ImageView) findViewById(R.id.close_menu);
        close.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        setMenu();
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public void setMenu()
    {
        switch(position)
        {
            case 0: pdfView.fromAsset("ccdmenu.pdf").
                    enableSwipe(true).
                    defaultPage(1).
                    enableSwipe(true).
                    onPageChange(this).
                    load();
                menu.setText("CCD Menu");
                break;
            case 1: pdfView.fromAsset("gurunathmenu.pdf").
                    enableSwipe(true).
                    defaultPage(1).
                    enableSwipe(true).
                    onPageChange(this).
                    load();
                menu.setText("Gurunath menu");
                break;
            case 2: pdfView.fromAsset("ladiesmenu.pdf").
                    enableSwipe(true).
                    defaultPage(dayPage()).
                    onPageChange(this).
                    load();
                menu.setText("Ladies mess menu");
                break;
            case 3: pdfView.fromAsset("pgmenu.pdf").
                    enableSwipe(true).
                    pages(0,1,2,3,4,6,5).
                    defaultPage(dayPage()).
                    onPageChange(this).
                    load();
                menu.setText("PG mess Menu");
                break;

            case 4:pdfView.fromAsset("ugmenu.pdf").
                    enableSwipe(true).
                    defaultPage(dayPage()).
                    onPageChange(this).
                    load();
                menu.setText("UG mess Menu");
                break;
            case 5:pdfView.fromAsset("storesmenu.pdf").
                    enableSwipe(true).
                    defaultPage(1).
                    onPageChange(this).
                    load();
                menu.setText("Stores Menu");
                break;
             }

    }


    public int dayPage()
    {
        switch(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))
        {
            case Calendar.SUNDAY: return 7;
            case Calendar.MONDAY: return 1;
            case Calendar.TUESDAY: return 2;
            case Calendar.WEDNESDAY: return 3;
            case Calendar.THURSDAY: return 4;
            case Calendar.FRIDAY: return 5;
            case Calendar.SATURDAY: return 6;
        }
        return 0;
    }
    @Override
    public void onPageChanged(int i, int i2)
    {
        pgNo.setText(i + " / " + i2);
    }
}
