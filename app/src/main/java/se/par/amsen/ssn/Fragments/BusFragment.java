package se.par.amsen.ssn.Fragments;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import se.par.amsen.ssnmachan.R;
import se.par.amsen.ssn.activity.BusResultActivity;

public class BusFragment extends Fragment
{
    private static Button numSubmit, areaSubmit;
    private static EditText number;
    private static TextView t1, t2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.bus_frag_fayout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        numSubmit = (Button) getActivity().findViewById(R.id.num_submit);
        areaSubmit = (Button) getActivity().findViewById(R.id.area_submit);
        number = (EditText) getActivity().findViewById(R.id.number);
        ArrayAdapter localAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, new DatabaseHandler(getActivity(), "seshadb3.sqlite3").getAllLabels());
        t1 = (TextView) getActivity().findViewById(R.id.textView);
        Typeface advFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Adventure.otf");
        t1.setTypeface(advFont);
        t2 = (TextView) getActivity().findViewById(R.id.textView2);
        t2.setTypeface(advFont);

        areaSubmit.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    // Perform action on key press
                    Toast.makeText(getActivity(), areaSubmit.getText(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        final AutoCompleteTextView localAutoCompleteTextView = (AutoCompleteTextView) getActivity().findViewById(R.id.area);
        localAutoCompleteTextView.setThreshold(1);
        localAutoCompleteTextView.setAdapter(localAdapter);
        numSubmit.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                String str = number.getText().toString();
                number.setText("");
                if (str.trim().length() > 0)
                {
                    Intent localIntent = new Intent(getActivity(), BusResultActivity.class);
                    localIntent.putExtra("Value1", "1");
                    localIntent.putExtra("Value2", str);
                    Bundle translateBundle =
                            ActivityOptions.makeCustomAnimation(getActivity(),
                                    R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
                    startActivity(localIntent, translateBundle);
                    return;
                }
                Toast.makeText(getActivity(), "Invalid input", Toast.LENGTH_LONG).show();
            }
        });
        areaSubmit.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                number.setText("");
                String str = localAutoCompleteTextView.getText().toString();
                localAutoCompleteTextView.setText("");
                if (str.trim().length() > 0)
                {
                    Intent localIntent = new Intent(getActivity(), BusResultActivity.class);
                    localIntent.putExtra("Value1", "2");
                    localIntent.putExtra("Value2", str);
                    Bundle translateBundle =
                            ActivityOptions.makeCustomAnimation(getActivity(),
                                    R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
                    startActivity(localIntent, translateBundle);
                    return;
                }
                Toast.makeText(getActivity(), "Invalid input", Toast.LENGTH_LONG).show();
            }
        });

    }

}
