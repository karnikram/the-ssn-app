package karnix.the.ssn.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.feedRecyclerView);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user_posts");

        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class,
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(firebaseRecyclerAdapter);

        Button postButton = (Button) findViewById(R.id.postButton);
        final EditText editText = (EditText) findViewById(R.id.postText);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long timeStamp = System.currentTimeMillis();
                if (editText.getEditableText().toString().equals("")) return;
                Post post = new Post("1", FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), FirebaseAuth.getInstance().getCurrentUser().getUid(), timeStamp, FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(), editText.getEditableText().toString());
                String key = databaseReference.push().getKey();
                post.pid = key;
                FirebaseDatabase.getInstance().getReference("user_posts/" + key).setValue(post);
                editText.setText("");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
