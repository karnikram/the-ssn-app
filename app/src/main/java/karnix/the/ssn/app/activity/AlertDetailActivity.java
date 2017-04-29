package karnix.the.ssn.app.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import karnix.the.ssn.app.model.WebConsolePost;
import karnix.the.ssn.app.utils.LogHelper;
import karnix.the.ssn.ssnmachan.R;

public class AlertDetailActivity extends BaseActivity {
    private static final String TAG = LogHelper.makeLogTag(AlertDetailActivity.class);

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.content_email1)
    TextView contentEmail1;
    @BindView(R.id.post_imageView)
    ImageView postImageView;

    private String title, content, fileName;
    private TextView dispContent, dispNo1, dispNo2, dispUrl1, dispUrl2, dispTitle;
    private WebConsolePost post;

    private ArrayList<String> links, contactNumberList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_details);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dispTitle = (TextView) findViewById(R.id.alert_title);
        dispContent = (TextView) findViewById(R.id.content_activity);
        dispNo1 = (TextView) findViewById(R.id.content_contact1);
        dispNo2 = (TextView) findViewById(R.id.content_contact2);
        dispUrl2 = (TextView) findViewById(R.id.content_url2);
        dispUrl1 = (TextView) findViewById(R.id.content_url1);

        post = new Gson().fromJson(getIntent().getStringExtra("post"), WebConsolePost.class);
        title = post.getTitle();
        content = post.getDescription();

        dispTitle.setText(title);

        links = extractUrls();
        contactNumberList = extractNumbers();
        dispUrls();
        dispNos();

        dispContent.setText(content);

        if (!post.getFileURL().equals("")) {
            Glide.with(this).load(post.getFileURL()).into(postImageView);

            fileName = URLUtil.guessFileName(post.getFileURL(), null, null);
            if (fileName.equals("downloadfile.bin")) {
                links.add(post.getFileURL());
            } else {
                links.add(fileName);
            }
            dispUrls();
        }

        if (dispUrl1.getText() != "None") {
            dispUrl1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startUrlIntent(dispUrl1.getText().toString());
                }
            });
            if (dispUrl2.getVisibility() != View.INVISIBLE) {
                dispUrl2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startUrlIntent(dispUrl2.getText().toString());
                    }
                });
            }
        }

        if (dispNo1.getText() != "None") {
            dispNo1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dispNo1.getText().toString().trim())));
                }
            });
            if (dispNo2.getVisibility() != View.INVISIBLE) {
                dispNo2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dispNo2.getText().toString().trim())));
                    }
                });
            }
        }

        if (!post.getEmail().equals("")) {
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
    }

    private void dispUrls() {
        if (links.size() == 2) {
            dispUrl1.setText(links.get(0));
            dispUrl2.setText(links.get(1));
        } else if (links.size() == 1) {
            dispUrl1.setText(links.get(0));
            dispUrl2.setVisibility(View.INVISIBLE);
        } else {
            dispUrl1.setText("None");
            dispUrl2.setVisibility(View.INVISIBLE);
        }
    }

    private void dispNos() {
        if (!post.getContactno().equals("")) {
            contactNumberList.add(post.getContactno());
        }
        if (contactNumberList.size() == 2) {
            dispNo1.setText(contactNumberList.get(0));
            dispNo2.setText(contactNumberList.get(1));
        } else if (contactNumberList.size() == 1) {
            dispNo1.setText(contactNumberList.get(0));
            dispNo2.setVisibility(View.INVISIBLE);
        } else {
            dispNo1.setText("None");
            dispNo1.setTextColor(this.getResources().getColor(R.color.secondaryTextColor));
            dispNo2.setVisibility(View.INVISIBLE);
        }
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

    private ArrayList<String> extractNumbers() {
        ArrayList<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String no = matcher.group();
            if (no.length() >= 10) {
                result.add(no);
                content = content.replace(matcher.group(), "");
            }
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
