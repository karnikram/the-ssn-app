package se.par.amsen.ssn.Fragments;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import se.par.amsen.ssnmachan.R;
import se.par.amsen.ssn.activity.DiningMenuActivity;

public class DiningFragment extends Fragment implements AdapterView.OnItemClickListener
{
    private ListView listView;
    private String[] options;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.dining_frag_layout, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getActivity().findViewById(R.id.listview);
        options = getResources().getStringArray(R.array.dining_options);
        listView.setAdapter(new DiningAdapter(getActivity(), options));
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent menuActivity = new Intent(getActivity(), DiningMenuActivity.class);
        Bundle translateBundle =
                ActivityOptions.makeCustomAnimation(getActivity(),
                        R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
        menuActivity.putExtra("position", position);
        startActivity(menuActivity, translateBundle);
    }
}
