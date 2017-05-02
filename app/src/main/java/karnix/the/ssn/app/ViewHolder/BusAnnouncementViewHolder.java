package karnix.the.ssn.app.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import karnix.the.ssn.app.model.BusAnnouncement;
import karnix.the.ssn.ssnmachan.R;

/**
 * Created by vvvro on 5/2/2017.
 */

public class BusAnnouncementViewHolder extends RecyclerView.ViewHolder{
    private final TextView mTitle;
    private final TextView mPostDate;
    private final TextView mPostContent;

    public BusAnnouncementViewHolder(View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.bus_announcement_title);
        mPostDate = (TextView) itemView.findViewById(R.id.bus_announcement_date);
        mPostContent = (TextView) itemView.findViewById(R.id.bus_announcement_description);
    }

    public void setmTitle(String name) {
        mTitle.setText(name);
    }

    public void setText(String text) {
        mPostContent.setText(text);
    }

    public void setPostDate(Long time) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        mPostDate.setText(simpleDateFormat.format(date));
    }

}
