package com.aksharam.greenpooling.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.aksharam.greenpooling.R;

public class SupportviewActivity extends AppCompatActivity {

    private EditText subj;
    private EditText nam;
    private EditText ema;
    private EditText mess;
    private EditText rep;
    private Button btn;
    private Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supportview);
        subj=(EditText)findViewById(R.id.subject);
        nam=(EditText)findViewById(R.id.name);
        ema=(EditText)findViewById(R.id.email_id);
        mess=(EditText)findViewById(R.id.message);
        rep=(EditText)findViewById(R.id.reply);
        btn=(Button)findViewById(R.id.btn_send);
        btn1=(Button)findViewById(R.id.btn_cancel);
    }
}
