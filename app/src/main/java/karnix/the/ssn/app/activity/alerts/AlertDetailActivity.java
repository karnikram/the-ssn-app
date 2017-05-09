package karnix.the.ssn.app.activity.alerts;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import karnix.the.ssn.app.activity.BaseActivity;
import karnix.the.ssn.app.model.posts.WebConsolePost;
import karnix.the.ssn.app.utils.LogHelper;
import karnix.the.ssn.ssnmachan.R;

public class AlertDetailActivity extends BaseActivity {
    private static final String TAG = LogHelper.makeLogTag(AlertDetailActivity.class);

    @BindView(R.id.content_email1)
    TextView contentEmail1;
    @BindView(R.id.post_imageView)
    ImageView postImageView;

    private String title, content, fileName, date;
    private TextView dispContent, dispNo1, dispUrl1, dispUrl2, dispTitle, dispPdf, dispDate;
    private WebConsolePost post;

    private ArrayList<String> links;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_details);
        ButterKnife.bind(this);

        dispTitle = (TextView) findViewById(R.id.alert_title);
        dispContent = (TextView) findViewById(R.id.content_activity);
        dispNo1 = (TextView) findViewById(R.id.content_contact1);
        dispUrl1 = (TextView) findViewById(R.id.content_url1);
        dispUrl2 = (TextView) findViewById(R.id.content_url2);
        dispPdf = (TextView) findViewById(R.id.content_pdf);
        dispDate = (TextView) findViewById(R.id.postdate);

        post = new Gson().fromJson(getIntent().getStringExtra("post"), WebConsolePost.class);
        title = post.getTitle();
        content = post.getDescription();

        dispTitle.setText(title);

        Date date = new Date(post.getDate());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aaa 'on' EEE, dd MMM");
        dispDate.setText(simpleDateFormat.format(date));

        links = extractUrls();

        dispUrls();
        dispNos();

        dispContent.setText(content);

        if (!post.getFileURL().equals("")) {

            fileName = post.getFileName();

            if(fileName.contains(".pdf"))
            {
                dispPdf.setText(fileName);
                postImageView.setVisibility(View.GONE);
            }

            else if(fileName.contains(".jpg") || fileName.contains(".jpeg") || fileName.contains(".png"))
            {
                Glide.with(this).load(post.getFileURL()).into(postImageView);
                dispPdf.setVisibility(View.GONE);
            }
            else
            {
                dispPdf.setVisibility(View.GONE);
                postImageView.setVisibility(View.GONE);
            }
        }


        if (!post.getEmail().equals(""))
        {
            contentEmail1.setText(post.getEmail());
            contentEmail1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                            Uri.parse("mailto:" + post.getEmail()));
                    startActivity(Intent.createChooser(emailIntent, "Send Email"));
                }
            });
        }

        else
            contentEmail1.setVisibility(View.GONE);
    }


    private void dispUrls() {
        if (links.size() == 2)
        {
            dispUrl1.setText(links.get(0));
            dispUrl2.setText(links.get(1));

            dispUrl1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startUrlIntent(dispUrl1.getText().toString());
                }
            });

            dispUrl2.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startUrlIntent(dispUrl2.getText().toString());
                }
            });
        }

        else if (links.size() == 1)
        {
            dispUrl1.setText(links.get(0));
            dispUrl2.setVisibility(View.GONE);
        }
        else
        {
            dispUrl1.setVisibility(View.GONE);
            dispUrl2.setVisibility(View.GONE);
        }
    }

    private void dispNos()
    {
        if(!post.getContactno().equals(""))
        {
            dispNo1.setText(post.getContactno());

            dispNo1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dispNo1.getText().toString().trim())));
                }
            });
        }

        else
            dispNo1.setVisibility(View.GONE);
    }

    private ArrayList<String> extractUrls() {
        ArrayList<String> result = new ArrayList<String>();

        Pattern pattern = Pattern.compile(
                "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" +
                        "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov|co|in" +
                        "|mil|biz|info|mobi|name|aero|jobs|museum" +
                        "|travel|[a-z]{2}))(:[\\d]{1,5})?" +
                        "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" +
                        "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
                        "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" +
                        "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
                        "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" +
                        "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b");

        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            result.add(matcher.group());
            content = content.replace(matcher.group(), "");
        }
        return result;
    }


    private void startUrlIntent(String textViewString) {
        if (textViewString.equals(fileName)) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(post.getFileURL())));
            return;
        }

        if (!textViewString.startsWith("http://") && !textViewString.startsWith("https://")) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + textViewString.trim())));
        } else {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(textViewString.trim())));
        }
    }
}
