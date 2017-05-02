package karnix.the.ssn.app.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import karnix.the.ssn.ssnmachan.R;

/**
 * Created by vvvro on 4/25/2017.
 */

public class PostExamCellViewHolder extends RecyclerView.ViewHolder {
    public final Button mPDFButton;
    private final TextView mTitle;
    private final TextView mPostDate;
    private final TextView mPostContent;

    public PostExamCellViewHolder(View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.postExamCellTitle);
        mPostDate = (TextView) itemView.findViewById(R.id.postExamCellDate);
        mPostContent = (TextView) itemView.findViewById(R.id.postExamCellDescription);
        mPDFButton = (Button) itemView.findViewById(R.id.postExamCellPDFButton);
    }

    public void setTitle(String name) {
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