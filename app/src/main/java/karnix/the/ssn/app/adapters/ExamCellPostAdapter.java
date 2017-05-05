package karnix.the.ssn.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import karnix.the.ssn.app.ViewHolder.PostExamCellViewHolder;
import karnix.the.ssn.app.activity.alerts.AlertDetailActivity;
import karnix.the.ssn.app.model.posts.WebConsolePost;
import karnix.the.ssn.ssnmachan.R;

/**
 * Created by vvvro on 5/5/2017.
 */

public class ExamCellPostAdapter extends RecyclerView.Adapter<PostExamCellViewHolder> {
    private ArrayList<WebConsolePost> mDataSet;
    private Context mContext;

    public ExamCellPostAdapter(Context context, ArrayList<WebConsolePost> myDataSet) {
        mContext = context;
        mDataSet = myDataSet;
    }

    @Override
    public PostExamCellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.exam_cell_post, parent, false);
        PostExamCellViewHolder viewHolder = new PostExamCellViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostExamCellViewHolder holder, int position) {
        final WebConsolePost model = mDataSet.get(position);
        holder.setTitle(model.getTitle());
        holder.setPostDate(model.getDate());
        holder.setText(model.getDescription());
        if (model.getFileURL() != null && !model.getFileURL().equals("")) {
            holder.mPDFButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(model.getFileURL()));
                    mContext.startActivity(intent);
                }
            });
            if(model.getFileName()!=null && !model.getFileName().equals("")) {
                holder.mPDFButton.setText(model.getFileName());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}
