package karnix.the.ssn.app.activity.alerts;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.customtabs.CustomTabsIntent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import karnix.the.ssn.app.activity.BaseActivity;
import karnix.the.ssn.app.model.posts.WebConsolePost;
import karnix.the.ssn.app.utils.FileDownloader;
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
                dispPdf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Toast.makeText(AlertDetailActivity.this, "Downloading..", Toast.LENGTH_SHORT).show();
                        new DownloadFile().execute(post.getFileURL(), post.getFileName());
                        //startUrlIntent(post.getFileURL());

                   }
                });
                postImageView.setVisibility(View.GONE);
            }

            else if(fileName.contains(".jpg") || fileName.contains(".jpeg") || fileName.contains(".png"))
            {
                Glide.with(this).load(post.getFileURL()).into(postImageView);
                dispPdf.setVisibility(View.GONE);
            }

        }

        else
        {
            dispPdf.setVisibility(View.GONE);
            postImageView.setVisibility(View.GONE);
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

            dispUrl1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startUrlIntent(dispUrl1.getText().toString());
                }
            });

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
                "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.|coe1.|coe2.)" +
                        "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov|co|in" +
                        "|mil|biz|info|mobi|name|aero|jobs|edu|museum" +
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


    private void startUrlIntent(String textViewString)
    {
        if(!(textViewString.contains("http://") || textViewString.contains("https://")))
        {
            textViewString = "http://" + textViewString;
        }
         CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
         builder.setToolbarColor(getResources().getColor(R.color.primaryColor));
         builder.setShowTitle(true);

         CustomTabsIntent customTabsIntent = builder.build();
         customTabsIntent.launchUrl(this, Uri.parse(textViewString));


    }

    private class DownloadFile extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings)
        {
            String fileUrl = strings[0];
            String fileName = strings[1];

            String status;

            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "TheSSNApp");

            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try
            {
                pdfFile.createNewFile();
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }

            status = FileDownloader.downloadFile(fileUrl, pdfFile);
            if(status.equals("Success"))
            return fileName;

            else
                return "Failure";
        }

        @Override
        protected void onPostExecute(String fileName)
        {

            if(fileName.equals("Failure"))
                Toast.makeText(AlertDetailActivity.this, "I/O Error!", Toast.LENGTH_LONG).show();
            else
            {
                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/TheSSNApp/" + fileName);
                Uri path = Uri.fromFile(pdfFile);

                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                pdfIntent.setDataAndType(path, "application/pdf");
                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try
                {
                    startActivity(pdfIntent);
                }

                catch (ActivityNotFoundException e)
                {
                    Toast.makeText(AlertDetailActivity.this, "Cannot find application to open PDF!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }
    }

}


