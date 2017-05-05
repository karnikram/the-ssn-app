package karnix.the.ssn.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import karnix.the.ssn.app.adapters.ExamCellPostAdapter;
import karnix.the.ssn.app.model.Node;
import karnix.the.ssn.app.model.posts.ExamCellPost;
import karnix.the.ssn.app.model.posts.Post;
import karnix.the.ssn.app.ViewHolder.PostExamCellViewHolder;
import karnix.the.ssn.app.model.posts.WebConsolePost;
import karnix.the.ssn.ssnmachan.R;

public class ExamCellActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_cell);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Button postButton = (Button) findViewById(R.id.postExamCellButton);
        //final EditText editText = (EditText) findViewById(R.id.postExamCellText);

        final ArrayList<WebConsolePost> postList = new ArrayList<>();
        final ExamCellPostAdapter postAdapter = new ExamCellPostAdapter(getApplicationContext(), postList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.feedExamCellRecyclerView);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("categorywise_posts/examcell");



       /* FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ExamCellPost,
                PostExamCellViewHolder>(ExamCellPost.class, R.layout.exam_cell_post,
                PostExamCellViewHolder.class, databaseReference.orderByChild("postedDate").getRef()) {
            @Override
            protected void populateViewHolder(PostExamCellViewHolder viewHolder, final ExamCellPost model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setPostDate(model.post.getPostedDate());
                viewHolder.setText(model.post.getContent());
                if (model.pdfLink != null && !model.pdfLink.equals("")) {
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
        };*/

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.examCellProgressBar);
        //final EditText examCellTitleEditText = (EditText) findViewById(R.id.postExamCellTitle);

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

        /*postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long timeStamp = System.currentTimeMillis();
                if (editText.getEditableText().toString().equals("")) return;
                if (examCellTitleEditText.getEditableText().toString().equals("")) return;
                Post post = new Post("1", FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                        FirebaseAuth.getInstance().getCurrentUser().getUid(), timeStamp,
                        FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(),
                        editText.getEditableText().toString());
                String key = databaseReference.push().getKey();
                post.pid = key;
                ExamCellPost examCellPost = new ExamCellPost(post, examCellTitleEditText.getEditableText().toString(), "");
                FirebaseDatabase.getInstance().getReference("exam_cell_posts/" + key).setValue(examCellPost);
                editText.setText("");
                examCellTitleEditText.setText("");
            }
        });*/
    }
}
