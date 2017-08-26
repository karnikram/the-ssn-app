package karnix.the.ssn.app.activity.alerts;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

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
import karnix.the.ssn.app.utils.NetworkUtils;
import karnix.the.ssn.ssnmachan.R;

public class DepartmentAlertsFragment extends Fragment {
    private static final String TAG = LogHelper.makeLogTag(DepartmentAlertsFragment.class);

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.postsRecyclerView)
    RecyclerView postsRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.spinner_department)
    Spinner spinnerDepartment;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    private Unbinder unbinder;

    private LinearLayoutManager linearLayoutManager;
    private List<WebConsolePost> postList;
    private PostAdapter postAdapter;

    private SharedPreferences sharedPreferences;
    private String[] departmentKeys;
    private String departmentKey;

    private ArrayList<Node> nodesList = new ArrayList<>();
    private HashMap<String, WebConsolePost> postHashMap = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alerts_department, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        departmentKeys = getResources().getStringArray(R.array.department_category_keys);

        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                progressBar.setVisibility(View.VISIBLE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("department", spinnerDepartment.getSelectedItem().toString());
                editor.apply();

                departmentKey = departmentKeys[spinnerDepartment.getSelectedItemPosition()];

                if (sharedPreferences.getBoolean("notifications_departments", false)) {
                    switchTopicSubscription(departmentKey);
                }

                linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setStackFromEnd(true);
                linearLayoutManager.setReverseLayout(true);

                postList = new ArrayList<>();
                postAdapter = new PostAdapter(getActivity(), postList);
                postsRecyclerView.setLayoutManager(linearLayoutManager);
                postsRecyclerView.setAdapter(postAdapter);

                nodesList = new ArrayList<>();
                postHashMap = new HashMap<>();

                checkConnectionStatus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.department_categories, android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(arrayAdapter);

        sharedPreferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        String department = sharedPreferences.getString("department", arrayAdapter.getItem(0).toString());
        spinnerDepartment.setSelection(arrayAdapter.getPosition(department));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("notifications_department_" + departmentKeys[arrayAdapter.getPosition(department)], true);
        editor.apply();

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getActivity(), postList);
        checkConnectionStatus();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPosts();
            }
        });
        swipeContainer.setColorSchemeResources(R.color.primaryColor,
                R.color.primaryColorDark,
                R.color.accentColor);

        return rootView;
    }

    private void checkConnectionStatus() {
        if (NetworkUtils.isConnectedToInternet(getActivity())) {
            progressBar.setVisibility(View.VISIBLE);

            getPosts();
        } else {
            progressBar.setVisibility(View.GONE);

            getPosts();

            Snackbar.make(coordinatorLayout, R.string.connection_failed, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.alerts_retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkConnectionStatus();
                        }
                    })
                    .show();
        }
    }

    private void getPosts() {
        postList.clear();
        postAdapter.notifyDataSetChanged();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference nodesRef = database.getReference("categorywise_posts/" + departmentKey);
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
                linearLayoutManager.scrollToPositionWithOffset(postList.size() - 1, 0);
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                if (swipeContainer != null) {
                    swipeContainer.setRefreshing(false);
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
                DatabaseReference nodesRef = database.getReference("posts/" + node.getPid());
                nodesRef.addValueEventListener(valueEventListener);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void switchTopicSubscription(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic);
        LogHelper.d(TAG, "Subscribed to " + topic);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("notifications_department_" + topic, true);

        for (String departmentKey : departmentKeys) {
            if (departmentKey.equals(topic)) {
                continue;
            }

            FirebaseMessaging.getInstance().unsubscribeFromTopic(departmentKey);
            LogHelper.d(TAG, "Unsubscribed from " + departmentKey);
            editor.putBoolean("notifications_department_" + departmentKey, false);
        }

        editor.apply();
    }
}
