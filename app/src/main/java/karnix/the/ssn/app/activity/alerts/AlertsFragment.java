package karnix.the.ssn.app.activity.alerts;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import karnix.the.ssn.app.adapters.PostAdapter;
import karnix.the.ssn.app.model.Node;
import karnix.the.ssn.app.model.posts.WebConsolePost;
import karnix.the.ssn.app.utils.LogHelper;
import karnix.the.ssn.ssnmachan.R;

public class AlertsFragment extends Fragment {
    private static final String TAG = LogHelper.makeLogTag(AlertsFragment.class);

    @BindView(R.id.postsRecyclerView)
    RecyclerView postsRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alerts, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        final String type = getArguments().getString("type");
        if (type.equals("examcell")) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.GONE);
        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        final List<WebConsolePost> postList = new ArrayList<>();
        final PostAdapter postAdapter = new PostAdapter(getActivity(), postList);
        final ArrayList<Node> nodesList = new ArrayList<Node>();
        final HashMap<String, WebConsolePost> postHashMap = new HashMap<String, WebConsolePost>();
        postsRecyclerView.setLayoutManager(layoutManager);
        postsRecyclerView.setAdapter(postAdapter);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference nodesRef = database.getReference("categorywise_posts/" + type);

        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                WebConsolePost post = dataSnapshot.getValue(WebConsolePost.class);
                if (post == null) {
                    postList.remove(postHashMap.get(dataSnapshot.getKey()));
                    postHashMap.remove(dataSnapshot.getKey());
                    postAdapter.notifyDataSetChanged();
                    return;
                }
                if (postHashMap.containsKey(dataSnapshot.getKey())) {
                    if (postList.contains(postHashMap.get(dataSnapshot.getKey()))) {
                        postList.remove(postHashMap.get(dataSnapshot.getKey()));
                        postAdapter.notifyDataSetChanged();
                    }
                }
                postHashMap.put(dataSnapshot.getKey(), post);
                postList.add(post);
                postAdapter.notifyDataSetChanged();
                layoutManager.scrollToPositionWithOffset(postList.size() - 1, 0);
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Node node = dataSnapshot.getValue(Node.class);
                nodesList.add(node);
                FirebaseDatabase.getInstance().getReference("posts/" + node.getPid())
                        .addValueEventListener(valueEventListener);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (nodesList.contains(dataSnapshot.getValue(Node.class))) {
                    nodesList.remove(dataSnapshot.getValue(Node.class));
                    FirebaseDatabase.getInstance().getReference("posts/" +
                            dataSnapshot.getValue(Node.class).getPid())
                            .removeEventListener(valueEventListener);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        nodesRef.addChildEventListener(childEventListener);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.ssnexamcell.in/fe.php";

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(getResources().getColor(R.color.primaryColor));
                builder.setShowTitle(true);

                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(getActivity(), Uri.parse(url));
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
