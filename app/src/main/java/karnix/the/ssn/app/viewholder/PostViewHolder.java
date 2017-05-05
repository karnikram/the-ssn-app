package karnix.the.ssn.app.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import karnix.the.ssn.ssnmachan.R;

/**
 * Created by vvvro on 4/2/2017.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {
    private final TextView mNameField;
    private final TextView mPostDate;
    private final TextView mPostContent;
    private final CircleImageView mPostUserImageURL;

    public PostViewHolder(View itemView) {
        super(itemView);
        mNameField = (TextView) itemView.findViewById(R.id.postUserName);
        mPostDate = (TextView) itemView.findViewById(R.id.postDate);
        mPostContent = (TextView) itemView.findViewById(R.id.postDescription);
        mPostUserImageURL = (CircleImageView) itemView.findViewById(R.id.postUserImage);
    }

    public void setName(String name) {
        mNameField.setText(name);
    }

    public void setText(String text) {
        mPostContent.setText(text);
    }

    public void setPostDate(Long time) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        mPostDate.setText(simpleDateFormat.format(date));
    }

    public void setPostUserImageURL(String url) {
        Glide.with(mPostUserImageURL.getContext()).load(url).into(mPostUserImageURL);
    }
}
