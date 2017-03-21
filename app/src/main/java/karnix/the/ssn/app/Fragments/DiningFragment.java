package karnix.the.ssn.app.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.andexert.library.RippleView;

import karnix.the.ssn.app.activity.DiningMenuActivity;
import karnix.the.ssn.ssnmachan.R;

public class DiningFragment extends Fragment implements AdapterView.OnItemClickListener {
    RippleView rippleItem;
    private ListView listView;
    private String[] options;
    private int position;

    public static DiningFragment newInstance() {
        return new DiningFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dining_frag_layout, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getActivity().findViewById(R.id.listview);
        options = getResources().getStringArray(R.array.dining_options);
        listView.setAdapter(new DiningAdapter(getActivity(), options));
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Intent menuActivity = new Intent(getActivity(), DiningMenuActivity.class);
        menuActivity.putExtra("position", position);
        startActivity(menuActivity);
    }
}
