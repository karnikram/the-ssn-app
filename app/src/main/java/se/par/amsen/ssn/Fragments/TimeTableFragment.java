package se.par.amsen.ssn.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import se.par.amsen.ssnmachan.R;
import se.par.amsen.ssn.gui.view.mainmenu.MainMenuItem;

public class TimeTableFragment extends Fragment
{
    TextView comingSoon,contribute,link;
    String htmlString = "<u>fb.com/ssnmachan</u>";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.timetable,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        comingSoon = (TextView)getActivity().findViewById(R.id.coming);
        contribute = (TextView)getActivity().findViewById(R.id.help);
        link = (TextView)getActivity().findViewById(R.id.linkt);
        link.setText(Html.fromHtml(htmlString));
        comingSoon.setTypeface(new MainMenuItem(getActivity()).advFont);
        contribute.setTypeface(new MainMenuItem(getActivity()).advFont);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/893173980726657")));
                }
                catch(Exception e)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://m.facebook.com/ssnmachan")));
                }
            }
        });


    }
}
