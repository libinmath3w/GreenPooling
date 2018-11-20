package com.aksharam.greenpooling.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import com.aksharam.greenpooling.R;
import com.aksharam.greenpooling.helper.SQLiteHandler;
import com.aksharam.greenpooling.helper.SessionManager;


public class ReviewActivity extends AppCompatActivity {

    private EditText rate;
    private EditText revi;
    private Button bt_ok;
    private Button bt_cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating);
        rate=(EditText)findViewById(R.id.rating);
        revi=(EditText)findViewById(R.id.review);
        bt_ok=(Button)findViewById(R.id.btn_ok);
        bt_cancel=(Button)findViewById(R.id.btn_cancel);

    }
}

