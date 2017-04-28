package karnix.the.ssn.app.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import karnix.the.ssn.app.model.WebConsolePost;
import karnix.the.ssn.ssnmachan.R;

public class AlertDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String title, content;
    private TextView dispContent, dispNo1, dispNo2, dispUrl1, dispUrl2, dispTitle;
    private WebConsolePost post;

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
        dispUrls();
        dispNos();
        dispContent.setText(content);

        if (dispUrl1.getText() != "None") {
            dispUrl1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!dispUrl1.getText().toString().startsWith("http://") && !dispUrl1.getText().toString().startsWith("https://")) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + dispUrl1.getText().toString().trim())));
                    } else {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(dispUrl1.getText().toString().trim())));
                    }

                }
            });
            if (dispUrl2.getVisibility() != View.INVISIBLE) {
                dispUrl2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!dispUrl2.getText().toString().startsWith("http://") && !dispUrl2.getText().toString().startsWith("https://")) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + dispUrl2.getText().toString().trim())));
                        } else {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(dispUrl1.getText().toString().trim())));
                        }
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
    }

    public void dispUrls() {
        ArrayList<String> urls = new FindUrls().extractUrls();
        urls.add(post.getFileURL());
        if (urls.size() == 2) {
            dispUrl1.setText(urls.get(0));
            dispUrl2.setText(urls.get(1));
        } else if (urls.size() == 1) {
            dispUrl1.setText(urls.get(0));
            dispUrl2.setVisibility(View.INVISIBLE);
        } else {
            dispUrl1.setText("None");
            dispUrl1.setTextColor(this.getResources().getColor(R.color.secondaryTextColor));
            dispUrl2.setVisibility(View.INVISIBLE);
        }
    }

    public void dispNos() {
        ArrayList<String> nos = new FindContactNos().extractNo();
        nos.add(post.getContactno());
        if (nos.size() == 2) {
            dispNo1.setText(nos.get(0));
            dispNo2.setText(nos.get(1));
        } else if (nos.size() == 1) {
            dispNo1.setText(nos.get(0));
            dispNo2.setVisibility(View.INVISIBLE);
        } else {
            dispNo1.setText("None");
            dispNo1.setTextColor(this.getResources().getColor(R.color.secondaryTextColor));
            dispNo2.setVisibility(View.INVISIBLE);
        }
    }

    class FindUrls {
        public ArrayList<String> extractUrls() {
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
    }

    class FindContactNos {
        ArrayList<String> result = new ArrayList<>();

        public ArrayList<String> extractNo() {
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
    }
}
