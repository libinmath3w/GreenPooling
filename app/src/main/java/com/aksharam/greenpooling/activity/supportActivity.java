package com.aksharam.greenpooling.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aksharam.greenpooling.R;

import java.security.PrivilegedAction;

public class supportActivity extends AppCompatActivity {
    private EditText subject1;
    private EditText message1;
    private Button submit1;
    private  Button clear1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support);
        subject1=(EditText) findViewById(R.id.subject);
        message1=(EditText)findViewById(R.id.message);
       submit1=(Button)findViewById(R.id.btn_submit);
       clear1=(Button) findViewById(R.id.btn_clear);
       submit1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              String sub=subject1.getText().toString();
              String mes=message1.getText().toString();
              //write query//
           }
       });
       clear1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               subject1.setText(null);
               message1.setText(null);
           }
       });

    }
}
