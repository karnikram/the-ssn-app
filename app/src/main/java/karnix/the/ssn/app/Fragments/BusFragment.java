package karnix.the.ssn.app.Fragments;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.andexert.library.RippleView;

import karnix.the.ssn.ssnmachan.R;
import karnix.the.ssn.app.activity.BusResultActivity;

public class BusFragment extends Fragment
{
    private static RippleView numSubmit, areaSubmit;
    private static EditText number;
    private static TextView t1, t2;

    public static BusFragment newInstance()
    {
        return new BusFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.bus_frag_fayout, container, false);
        ViewCompat.setElevation(rootView,50);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        numSubmit = (RippleView) getActivity().findViewById(R.id.num_submit);
        areaSubmit = (RippleView) getActivity().findViewById(R.id.area_submit);
        number = (EditText) getActivity().findViewById(R.id.number);
        ArrayAdapter localAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, new DatabaseHandler(getActivity(), "seshadb3.sqlite3").getAllLabels());
        t1 = (TextView) getActivity().findViewById(R.id.textView);
        t2 = (TextView) getActivity().findViewById(R.id.textView2);


        final AutoCompleteTextView localAutoCompleteTextView = (AutoCompleteTextView) getActivity().findViewById(R.id.area);
        localAutoCompleteTextView.setThreshold(1);
        localAutoCompleteTextView.setAdapter(localAdapter);
        numSubmit.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener()
        {
            @Override
            public void onComplete(RippleView rippleView)
            {
                String str = number.getText().toString();
                number.setText("");
                if (str.trim().length() > 0)
                {
                    Intent localIntent = new Intent(getActivity(), BusResultActivity.class);
                    localIntent.putExtra("Value1", "1");
                    localIntent.putExtra("Value2", str);
                    startActivity(localIntent);
                    return;
                }
                Toast.makeText(getActivity(), "Invalid input", Toast.LENGTH_LONG).show();
            }
        });

        areaSubmit.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView)
            {
                number.setText("");
                String str = localAutoCompleteTextView.getText().toString();
                localAutoCompleteTextView.setText("");
                if (str.trim().length() > 0)
                {
                    Intent localIntent = new Intent(getActivity(), BusResultActivity.class);
                    localIntent.putExtra("Value1", "2");
                    localIntent.putExtra("Value2", str);
                    startActivity(localIntent);
                    return;
                }
                Toast.makeText(getActivity(), "Invalid input", Toast.LENGTH_LONG).show();
            }
        });

    }

}
