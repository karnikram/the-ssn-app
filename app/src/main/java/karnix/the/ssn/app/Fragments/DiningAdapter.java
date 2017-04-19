package karnix.the.ssn.app.Fragments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;

import karnix.the.ssn.app.utils.Timings;
import karnix.the.ssn.ssnmachan.R;

public class DiningAdapter extends BaseAdapter {
    LayoutInflater inflater;
    TextView option, open, close;
    String[] options;
    Activity context;
    Calendar calendar;

    public DiningAdapter(Activity context, String[] options) {
        super();
        this.options = options;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        calendar = Calendar.getInstance();
    }

    @Override
    public int getCount() {
        return options.length;
    }

    @Override
    public Object getItem(int position) {
        return options[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = inflater.inflate(R.layout.dining_list_tem, parent, false);
        option = (TextView) v.findViewById(R.id.option);
        open = (TextView) v.findViewById(R.id.open_time);
        option.setText(options[position]);
        if (!getImage(position)) {
            open.setText("Closed");
            open.setTextColor(context.getResources().getColor(R.color.close));
        }

        return v;
    }


    public boolean getImage(int position) {
        switch (position) {
            case 0:
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    return false;
                } else {
                    Timings.ccd.init();
                    if ((calendar.after(Timings.ccd.start) && calendar.before(Timings.ccd.end))) {
                        return true;
                    } else return false;
                }

            case 1:
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    Timings.gurunathSun.init();
                    if ((calendar.after(Timings.gurunathSun.start)) && calendar.before(Timings.gurunathSun.end)) {
                        return true;
                    } else return false;
                } else {
                    Timings.gurunath.init();
                    if ((calendar.after(Timings.gurunath.start) && calendar.before(Timings.gurunath.end))) {
                        return true;
                    } else return false;
                }
            case 2:
                Timings.ladies.init();
                if ((calendar.after(Timings.ladies.b1) && calendar.before(Timings.ladies.b2))
                        || (calendar.after(Timings.ladies.l1) && calendar.before(Timings.ladies.l2))
                        || (calendar.after(Timings.ladies.s1) && calendar.before(Timings.ladies.s2))
                        || (calendar.after(Timings.ladies.d1) && calendar.before(Timings.ladies.d2))) {
                    return true;
                } else


                    return false;

            case 3:
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    Timings.pgSun.init();
                    if ((calendar.after(Timings.pgSun.b1) && calendar.before(Timings.pgSun.b2))
                            || (calendar.after(Timings.pgSun.l1) && calendar.before(Timings.pgSun.l2))
                            || (calendar.after(Timings.pgSun.s1) && calendar.before(Timings.pgSun.s2))
                            || (calendar.after(Timings.pgSun.d1) && calendar.before(Timings.pgSun.d2))) {
                        return true;
                    } else return false;

                } else {
                    Timings.pg.init();

                    if ((calendar.after(Timings.pg.b1) && calendar.before(Timings.pg.b2))
                            || (calendar.after(Timings.pg.l1) && calendar.before(Timings.pg.l2))
                            || (calendar.after(Timings.pg.s1) && calendar.before(Timings.pg.s2))
                            || (calendar.after(Timings.pg.d1) && calendar.before(Timings.pg.d2))) {
                        return true;
                    } else return false;
                }
            case 4:
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    Timings.ugSun.init();
                    if ((calendar.after(Timings.ugSun.b1) && calendar.before(Timings.ugSun.b2))
                            || (calendar.after(Timings.ugSun.l1) && calendar.before(Timings.ugSun.l2))
                            || (calendar.after(Timings.ugSun.s1) && calendar.before(Timings.ugSun.s2))
                            || (calendar.after(Timings.ugSun.d1) && calendar.before(Timings.ugSun.d2))) {
                        return true;
                    } else return false;

                } else {
                    Timings.ug.init();
                    if ((calendar.after(Timings.ug.b1) && calendar.before(Timings.ug.b2))
                            || (calendar.after(Timings.ug.l1) && calendar.before(Timings.ug.l2))
                            || (calendar.after(Timings.ug.s1) && calendar.before(Timings.ug.s2))
                            || (calendar.after(Timings.ug.d1) && calendar.before(Timings.ug.d2))) {
                        return true;
                    } else return false;
                }
            case 5:
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    Timings.canteenSun.init();
                    if ((calendar.after(Timings.canteenSun.start)) && calendar.before(Timings.canteenSun.end)) {
                        return true;
                    } else return false;
                } else {
                    Timings.canteen.init();
                    if ((calendar.after(Timings.canteen.start) && calendar.before(Timings.canteen.end))) {
                        return true;
                    } else return false;
                }
        }
        return false;
    }
}

