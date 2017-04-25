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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import karnix.the.ssn.app.model.ExamCellPost;
import karnix.the.ssn.app.model.Post;
import karnix.the.ssn.app.viewholder.PostExamCellViewHolder;
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

        final Button postButton = (Button) findViewById(R.id.postExamCellButton);
        final EditText editText = (EditText) findViewById(R.id.postExamCellText);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.feedExamCellRecyclerView);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("exam_cell_posts");

        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ExamCellPost,
                PostExamCellViewHolder>(ExamCellPost.class, R.layout.exam_cell_post,
                PostExamCellViewHolder.class, databaseReference.orderByChild("postedDate").getRef()) {
            @Override
            protected void populateViewHolder(PostExamCellViewHolder viewHolder, final ExamCellPost model, int position) {
                viewHolder.setName(model.post.getUserName());
                viewHolder.setPostDate(model.post.getPostedDate());
                viewHolder.setText(model.post.getContent());
                viewHolder.setPostUserImageURL(model.post.getUserProfileURL());
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
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(firebaseRecyclerAdapter);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long timeStamp = System.currentTimeMillis();
                if (editText.getEditableText().toString().equals("")) return;
                Post post = new Post("1", FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                        FirebaseAuth.getInstance().getCurrentUser().getUid(), timeStamp,
                        FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(),
                        editText.getEditableText().toString());
                String key = databaseReference.push().getKey();
                post.pid = key;
                ExamCellPost examCellPost = new ExamCellPost(post, "");
                FirebaseDatabase.getInstance().getReference("exam_cell_posts/" + key).setValue(examCellPost);
                editText.setText("");
            }
        });
    }
}
