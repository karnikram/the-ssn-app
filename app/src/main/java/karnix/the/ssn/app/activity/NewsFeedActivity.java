package karnix.the.ssn.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import karnix.the.ssn.app.model.posts.Post;
import karnix.the.ssn.app.viewholder.PostViewHolder;
import karnix.the.ssn.ssnmachan.R;

public class NewsFeedActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.postText)
    EditText postText;
    @BindView(R.id.postButton)
    Button postButton;
    @BindView(R.id.feedRecyclerView)
    RecyclerView feedRecyclerView;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("user_posts");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        feedRecyclerView.setLayoutManager(layoutManager);

        setFeedRecyclerViewAdapter();

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long timeStamp = System.currentTimeMillis();
                if (postText.getEditableText().toString().equals("")) return;
                Post post = new Post("1", FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                        FirebaseAuth.getInstance().getCurrentUser().getUid(), timeStamp,
                        FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(),
                        postText.getEditableText().toString());
                String key = databaseReference.push().getKey();
                post.pid = key;
                FirebaseDatabase.getInstance().getReference("user_posts/" + key).setValue(post);
                postText.setText("");
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setFeedRecyclerViewAdapter();
            }
        });
        swipeContainer.setColorSchemeResources(R.color.primaryColor,
                R.color.primaryColorDark,
                R.color.accentColor);
    }

    private void setFeedRecyclerViewAdapter() {
        final FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class,
                R.layout.post_text,
                PostViewHolder.class, databaseReference.orderByChild("postedDate").getRef()) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, Post model, int position) {
                viewHolder.setName(model.userName);
                viewHolder.setPostDate(model.postedDate);
                viewHolder.setText(model.content);
                viewHolder.setPostUserImageURL(model.userProfileURL);
            }
        };
        feedRecyclerView.setAdapter(firebaseRecyclerAdapter);
        swipeContainer.setRefreshing(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
