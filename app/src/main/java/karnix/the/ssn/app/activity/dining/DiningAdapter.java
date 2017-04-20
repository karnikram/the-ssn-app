package karnix.the.ssn.app.activity.dining;

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
    private LayoutInflater inflater;
    private String[] options;
    private Activity context;
    private Calendar calendar;
    private boolean isCategory;

    public DiningAdapter(Activity context, String[] options, boolean isCategory) {
        super();
        this.options = options;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.isCategory = isCategory;
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
        View v = inflater.inflate(R.layout.item_dining, parent, false);
        TextView option = (TextView) v.findViewById(R.id.option);
        TextView open = (TextView) v.findViewById(R.id.open_time);
        option.setText(options[position]);

        if (isCategory) {
            open.setVisibility(View.GONE);
            return v;
        }
        if (!getImage(position)) {
            open.setText("Closed");
            open.setTextColor(context.getResources().getColor(R.color.close));
        }

        return v;
    }


    public boolean getImage(int position) {
        switch (position) {
            case 0:
                Timings.ladies.init();
                return (calendar.after(Timings.ladies.b1) && calendar.before(Timings.ladies.b2))
                        || (calendar.after(Timings.ladies.l1) && calendar.before(Timings.ladies.l2))
                        || (calendar.after(Timings.ladies.s1) && calendar.before(Timings.ladies.s2))
                        || (calendar.after(Timings.ladies.d1) && calendar.before(Timings.ladies.d2));

            case 1:
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    Timings.pgSun.init();
                    return (calendar.after(Timings.pgSun.b1) && calendar.before(Timings.pgSun.b2))
                            || (calendar.after(Timings.pgSun.l1) && calendar.before(Timings.pgSun.l2))
                            || (calendar.after(Timings.pgSun.s1) && calendar.before(Timings.pgSun.s2))
                            || (calendar.after(Timings.pgSun.d1) && calendar.before(Timings.pgSun.d2));
                } else {
                    Timings.pg.init();
                    return (calendar.after(Timings.pg.b1) && calendar.before(Timings.pg.b2))
                            || (calendar.after(Timings.pg.l1) && calendar.before(Timings.pg.l2))
                            || (calendar.after(Timings.pg.s1) && calendar.before(Timings.pg.s2))
                            || (calendar.after(Timings.pg.d1) && calendar.before(Timings.pg.d2));
                }
            case 2:
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    Timings.ugSun.init();
                    return (calendar.after(Timings.ugSun.b1) && calendar.before(Timings.ugSun.b2))
                            || (calendar.after(Timings.ugSun.l1) && calendar.before(Timings.ugSun.l2))
                            || (calendar.after(Timings.ugSun.s1) && calendar.before(Timings.ugSun.s2))
                            || (calendar.after(Timings.ugSun.d1) && calendar.before(Timings.ugSun.d2));
                } else {
                    Timings.ug.init();
                    return (calendar.after(Timings.ug.b1) && calendar.before(Timings.ug.b2))
                            || (calendar.after(Timings.ug.l1) && calendar.before(Timings.ug.l2))
                            || (calendar.after(Timings.ug.s1) && calendar.before(Timings.ug.s2))
                            || (calendar.after(Timings.ug.d1) && calendar.before(Timings.ug.d2));
                }
            case 3:
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    Timings.canteenSun.init();
                    return (calendar.after(Timings.canteenSun.start)) && calendar.before(Timings.canteenSun.end);
                } else {
                    Timings.canteen.init();
                    return (calendar.after(Timings.canteen.start) && calendar.before(Timings.canteen.end));
                }
        }
        return false;
    }
}

