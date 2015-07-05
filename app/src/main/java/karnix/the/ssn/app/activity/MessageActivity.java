package karnix.the.ssn.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.dd.CircularProgressButton;
import com.parse.ParseObject;

import karnix.the.ssn.ssnmachan.R;

public class MessageActivity extends Activity
{
    EditText messageField;
    CircularProgressButton circularButton;
    TextView counter;
    String message;
    RippleView rippleInfo;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);
        messageField = (EditText) findViewById(R.id.message);
        counter = (TextView) findViewById(R.id.counter);
        rippleInfo = (RippleView)findViewById(R.id.rippleInfo);
        messageField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                counter.setText(Integer.toString(1 + start));
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        circularButton = (CircularProgressButton) findViewById(R.id.circularButton1);
        circularButton.setIndeterminateProgressMode(true);
        circularButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (circularButton.getProgress() == 0)
                {
                    checkConnectionExecute();
                }
                else
                    if (circularButton.getProgress() == 100 || circularButton.getProgress() == -1 )
                    {
                        circularButton.setProgress(0);
                    }
            }
        });

        rippleInfo.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener()
        {
            @Override
            public void onComplete(RippleView rippleView)
            {
                startActivity(new Intent(MessageActivity.this,AboutActivity.class));
            }
        });

    }

    private void checkConnectionExecute()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            message = messageField.getText().toString();
            if(message.isEmpty())
            {
                Toast.makeText(this,"Invalid input",Toast.LENGTH_SHORT).show();
                circularButton.setProgress(-1);
            }
            else if (Integer.parseInt(counter.getText().toString())>350)
            {
                Toast.makeText(this,"Exceeds character limit. We don't have our own server.",Toast.LENGTH_LONG).show();
                circularButton.setProgress(-1);
            }
            else
            {
                circularButton.setProgress(50);
                ParseObject testObject = new ParseObject("Messages");
                testObject.put("message", message);
                testObject.saveInBackground();
                Toast.makeText(this, "Message sent.", Toast.LENGTH_LONG).show();
                circularButton.setProgress(100);
                messageField.setText("");
            }
        }

        else
        {
            Toast.makeText(this, "Connect to the internet!", Toast.LENGTH_LONG).show();
            circularButton.setProgress(-1);
        }
    }
}
