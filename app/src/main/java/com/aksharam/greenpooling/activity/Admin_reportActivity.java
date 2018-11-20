package com.aksharam.greenpooling.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.aksharam.greenpooling.R;

public class Admin_reportActivity extends AppCompatActivity {

    private Button review1;
    private Button review2;
    private Button review3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report);
        review1=(Button)findViewById(R.id.btnusr_review1);
        review2=(Button)findViewById(R.id.btnusr_review2);
        review3=(Button)findViewById(R.id.btnusr_review3);
    }
}
