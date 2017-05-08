/*
 * Copyright (C) 2017  Arun T S
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package karnix.the.ssn.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    public void onBindViewHolder(PostAdapter.ViewHolder viewHolder, int position) {
        final WebConsolePost post = postList.get(position);

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

        public ViewHolder(final View itemView) {
            super(itemView);

            postLL = (LinearLayout) itemView.findViewById(R.id.post_ll);
            titleTV = (TextView) itemView.findViewById(R.id.postTitle);
            dateTV = (TextView) itemView.findViewById(R.id.postDate);
            descriptionTV = (TextView) itemView.findViewById(R.id.postDescription);
        }
    }
}