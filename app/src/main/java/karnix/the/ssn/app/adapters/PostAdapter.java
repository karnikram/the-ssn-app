package karnix.the.ssn.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import karnix.the.ssn.app.activity.alerts.AlertDetailActivity;
import karnix.the.ssn.app.model.posts.WebConsolePost;
import karnix.the.ssn.ssnmachan.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<WebConsolePost> postList;
    private Context context;

    public PostAdapter(Context context, List<WebConsolePost> postList) {
        this.postList = postList;
        this.context = context;
    }

    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_post, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PostAdapter.ViewHolder viewHolder, int position) {
        final WebConsolePost post = postList.get(position);
        if(post==null) return;
        Date date = new Date(post.getDate());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aaa 'on' EEE, dd MMM");
        viewHolder.dateTV.setText(simpleDateFormat.format(date));
        viewHolder.titleTV.setText(post.getTitle());
        viewHolder.descriptionTV.setText(post.getDescription());
        viewHolder.postLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AlertDetailActivity.class);
                intent.putExtra("post", new Gson().toJson(post));
                context.startActivity(intent);
            }
        });

        if (!post.getFileURL().equals("")) {
            Glide.with(context)
                    .load(post.getFileURL())
                    .placeholder(R.drawable.ic_attachment)
                    .into(viewHolder.imageView);
            viewHolder.imageView.setVisibility(View.VISIBLE);
        } else {
            Glide.clear(viewHolder.imageView);
            viewHolder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout postLL;
        private TextView titleTV;
        private TextView dateTV;
        private TextView descriptionTV;
        private ImageView imageView;

        public ViewHolder(final View itemView) {
            super(itemView);

            postLL = (LinearLayout) itemView.findViewById(R.id.post_ll);
            titleTV = (TextView) itemView.findViewById(R.id.postTitle);
            dateTV = (TextView) itemView.findViewById(R.id.postDate);
            descriptionTV = (TextView) itemView.findViewById(R.id.postDescription);
            imageView = (ImageView) itemView.findViewById(R.id.post_imageView);
        }
    }
}