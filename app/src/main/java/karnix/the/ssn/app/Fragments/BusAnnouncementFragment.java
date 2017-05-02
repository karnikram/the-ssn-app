package karnix.the.ssn.app.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import karnix.the.ssn.app.ViewHolder.BusAnnouncementViewHolder;
import karnix.the.ssn.app.ViewHolder.PostViewHolder;
import karnix.the.ssn.app.model.BusAnnouncement;
import karnix.the.ssn.app.model.posts.Post;
import karnix.the.ssn.ssnmachan.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BusAnnouncementFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BusAnnouncementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusAnnouncementFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BusAnnouncementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusAnnouncementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BusAnnouncementFragment newInstance(String param1, String param2) {
        BusAnnouncementFragment fragment = new BusAnnouncementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bus_announcement, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.bus_announcement_recycler_view);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("bus_announcements");

        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BusAnnouncement, BusAnnouncementViewHolder>(BusAnnouncement.class,
                R.layout.bus_announcement,
                BusAnnouncementViewHolder.class, databaseReference.orderByChild("postedDate").getRef()) {
            @Override
            protected void populateViewHolder(BusAnnouncementViewHolder viewHolder, BusAnnouncement model, int position) {
                viewHolder.setmTitle(model.title);
                viewHolder.setPostDate(model.postedDate);
                viewHolder.setText(model.content);
            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(firebaseRecyclerAdapter);

        Button postButton = (Button) rootView.findViewById(R.id.bus_announcement_post_button);
        final EditText editText = (EditText)rootView.findViewById(R.id.bus_announcement_text);
        final EditText titleEditText = (EditText) rootView.findViewById(R.id.bus_announcement_title_editText);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getEditableText().toString().equals("")){
                    Toast.makeText(getContext(), "Please Enter Title", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(titleEditText.getEditableText().toString().equals("")){
                    Toast.makeText(getContext(), "Please Enter Description", Toast.LENGTH_SHORT).show();
                    return;
                }
                Long timeStamp = System.currentTimeMillis();
                if (editText.getEditableText().toString().equals("")) return;
                BusAnnouncement post = new BusAnnouncement("1", FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), FirebaseAuth.getInstance().getCurrentUser().getUid(), timeStamp, FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(), editText.getEditableText().toString(),titleEditText.getEditableText().toString());
                String key = databaseReference.push().getKey();
                post.pid = key;
                FirebaseDatabase.getInstance().getReference("bus_announcements/" + key).setValue(post);
                editText.setText("");
                titleEditText.setText("");
            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
