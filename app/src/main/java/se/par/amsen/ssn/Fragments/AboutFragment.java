package se.par.amsen.ssn.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import se.par.amsen.ssnmachan.R;
import se.par.amsen.ssn.gui.view.mainmenu.MainMenuItem;

public class AboutFragment extends Fragment
{
    TextView about, content, vno;
    ImageView credits;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.about_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        content = (TextView) getActivity().findViewById(R.id.about_2);
        about = (TextView)getActivity().findViewById(R.id.about_3);
        about.setTypeface(new MainMenuItem(getActivity()).advFont);
        about.setText(R.string.description);
        vno = (TextView)getActivity().findViewById(R.id.vno);
        credits = (ImageView) getActivity().findViewById(R.id.about_4);
        content.setTypeface(new MainMenuItem(getActivity()).advFont);
        content.setText(R.string.app_name);
        credits.setImageResource(R.drawable.credits);
    }
}
