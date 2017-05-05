package karnix.the.ssn.app.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import karnix.the.ssn.app.adapters.BusAnnouncementAdapter;
import karnix.the.ssn.app.model.BusAnnouncement;
import karnix.the.ssn.app.ViewHolder.BusAnnouncementViewHolder;
import karnix.the.ssn.app.model.Node;
import karnix.the.ssn.app.model.posts.WebConsolePost;
import karnix.the.ssn.ssnmachan.R;


public class BusAnnouncementActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_announcement);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.bus_announcement_recycler_view);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("categorywise_posts/busdept");

        /*FirebaseRecyclerAdapter firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<BusAnnouncement, BusAnnouncementViewHolder>(
                        BusAnnouncement.class, R.layout.bus_announcement,
                        BusAnnouncementViewHolder.class,
                        databaseReference.orderByChild("postedDate").getRef()) {
                    @Override
                    protected void populateViewHolder(BusAnnouncementViewHolder viewHolder,
                                                      BusAnnouncement model, int position) {
                        viewHolder.setTitle(model.getTitle());
                        viewHolder.setPostDate(model.getPostedDate());
                        viewHolder.setText(model.getContent());
                    }
                };*/

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(firebaseRecyclerAdapter);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.busProgressBar);
        final ArrayList<WebConsolePost> postList =  new ArrayList<>();
        final BusAnnouncementAdapter postAdapter = new BusAnnouncementAdapter(getApplicationContext(), postList);
        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                WebConsolePost post = dataSnapshot.getValue(WebConsolePost.class);
                postList.add(post);
                postAdapter.notifyDataSetChanged();
                layoutManager.scrollToPositionWithOffset(postList.size() - 1, 0);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Node node = dataSnapshot.getValue(Node.class);
                DatabaseReference nodesRef = FirebaseDatabase.getInstance().getReference("posts/" + node.getPid());
                nodesRef.addValueEventListener(valueEventListener);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        databaseReference.addChildEventListener(childEventListener);

        recyclerView.setAdapter(postAdapter);

        //Button postButton = (Button) findViewById(R.id.bus_announcement_post_button);
        //final EditText editText = (EditText) findViewById(R.id.bus_announcement_text);
        //final EditText titleEditText = (EditText) findViewById(R.id.bus_announcement_title_editText);
        /*postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getEditableText().toString().equals("")) {
                    Toast.makeText(BusAnnouncementActivity.this, "Please Enter Title", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (titleEditText.getEditableText().toString().equals("")) {
                    Toast.makeText(BusAnnouncementActivity.this, "Please Enter Description", Toast.LENGTH_SHORT).show();
                    return;
                }
                Long timeStamp = System.currentTimeMillis();
                if (editText.getEditableText().toString().equals("")) return;
                BusAnnouncement post = new BusAnnouncement("1",
                        FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                        FirebaseAuth.getInstance().getCurrentUser().getUid(), timeStamp,
                        FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(),
                        editText.getEditableText().toString(),
                        titleEditText.getEditableText().toString());
                String key = databaseReference.push().getKey();
                post.setPid(key);
                FirebaseDatabase.getInstance().getReference("bus_announcements/" + key).setValue(post);
                editText.setText("");
                titleEditText.setText("");
            }
        });*/
    }
}
