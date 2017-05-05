package karnix.the.ssn.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import karnix.the.ssn.app.ViewHolder.BusAnnouncementViewHolder;
import karnix.the.ssn.app.ViewHolder.PostExamCellViewHolder;
import karnix.the.ssn.app.model.BusAnnouncement;
import karnix.the.ssn.app.model.posts.WebConsolePost;
import karnix.the.ssn.ssnmachan.R;

/**
 * Created by vvvro on 5/5/2017.
 */

public class BusAnnouncementAdapter extends RecyclerView.Adapter<BusAnnouncementViewHolder>{
    private ArrayList<WebConsolePost> mDataSet;
    private Context mContext;

    public BusAnnouncementAdapter(Context context, ArrayList<WebConsolePost> myDataSet) {
        mContext = context;
        mDataSet = myDataSet;
    }

    @Override
    public BusAnnouncementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.bus_announcement, parent, false);
        BusAnnouncementViewHolder viewHolder = new BusAnnouncementViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BusAnnouncementViewHolder holder, int position) {
        WebConsolePost model = mDataSet.get(position);
        if(model == null) return;
        holder.setTitle(model.getTitle());
        holder.setPostDate(model.getDate());
        holder.setText(model.getDescription());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
