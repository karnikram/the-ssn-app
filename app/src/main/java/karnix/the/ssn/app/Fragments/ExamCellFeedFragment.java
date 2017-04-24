package karnix.the.ssn.app.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.internal.BaselineLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import karnix.the.ssn.app.ViewHolder.PostExamCellViewHolder;
import karnix.the.ssn.app.ViewHolder.PostViewHolder;
import karnix.the.ssn.app.model.ExamCellPost;
import karnix.the.ssn.app.model.Post;
import karnix.the.ssn.ssnmachan.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExamCellFeedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExamCellFeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExamCellFeedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerView;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    LinearLayoutManager layoutManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ExamCellFeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExamCellFeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExamCellFeedFragment newInstance(String param1, String param2) {
        ExamCellFeedFragment fragment = new ExamCellFeedFragment();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_exam_cell_feed, container, false);
        final Button postButton = (Button) rootView.findViewById(R.id.postExamCellButton);
        final EditText editText = (EditText) rootView.findViewById(R.id.postExamCellText);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.feedExamCellRecyclerView);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("exam_cell_posts");
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ExamCellPost, PostExamCellViewHolder>(ExamCellPost.class,
                R.layout.exam_cell_post,
                PostExamCellViewHolder.class, databaseReference.orderByChild("postedDate").getRef()) {
            @Override
            protected void populateViewHolder(PostExamCellViewHolder viewHolder, final ExamCellPost model, int position) {
                viewHolder.setName(model.post.getUserName());
                viewHolder.setPostDate(model.post.getPostedDate());
                viewHolder.setText(model.post.getContent());
                viewHolder.setPostUserImageURL(model.post.getUserProfileURL());
                if(model.pdfLink!=null&&!model.pdfLink.equals("")){
                    viewHolder.mPDFButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(model.getPdfLink()));
                            startActivity(intent);
                        }
                    });
                }
            }
        };
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long timeStamp = System.currentTimeMillis();
                if (editText.getEditableText().toString().equals("")) return;
                Post post = new Post("1", FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), FirebaseAuth.getInstance().getCurrentUser().getUid(), timeStamp, FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(), editText.getEditableText().toString());
                String key = databaseReference.push().getKey();
                post.pid = key;
                ExamCellPost examCellPost = new ExamCellPost(post,"");
                FirebaseDatabase.getInstance().getReference("exam_cell_posts/" + key).setValue(examCellPost);
                editText.setText("");
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
