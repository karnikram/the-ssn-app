package karnix.the.ssn.app.activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import karnix.the.ssn.ssnmachan.R;

public class MessageActivity extends Activity {
    EditText messageField;
    Button button;
    TextView counter;
    String message;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);

        messageField = (EditText) findViewById(R.id.message);
        counter = (TextView) findViewById(R.id.counter);

        messageField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                counter.setText(Integer.toString(1 + start));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        button = (Button) findViewById(R.id.send_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnectionExecute();
            }
        });
    }

    private void checkConnectionExecute() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            message = messageField.getText().toString();
            if (message.isEmpty()) {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
            } else if (Integer.parseInt(counter.getText().toString()) > 350) {
                Toast.makeText(this, "Exceeds character limit. We don't have our own server.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Message sent.", Toast.LENGTH_LONG).show();
                messageField.setText("");
            }
        } else {
            Toast.makeText(this, "Connect to the internet!", Toast.LENGTH_LONG).show();
        }
    }
}
