package com.aksharam.greenpooling.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.aksharam.greenpooling.R;

public class OfferRide extends AppCompatActivity {
    private Button ok,cancel;
    private Spinner avlseats;
    private String frommapstemp , localfrom;
    private EditText from,dest,times;
    private ImageView fromgps,destgps;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_ride);
        fromgps = (ImageView)findViewById(R.id.fromgps);
        destgps = (ImageView)findViewById(R.id.destgps);
        from = (EditText)findViewById(R.id.et_from);
        dest = (EditText)findViewById(R.id.et_dest);
        times = (EditText)findViewById(R.id.et_time);
        ok = (Button)findViewById(R.id.btn_ok);
        cancel = (Button)findViewById(R.id.btn_cancel);
        fromgps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MapActivity.class);
                startActivity(i);
            }
        });
        destgps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MapActivity2.class);
                startActivity(i);
            }
        });

       String frommap = getIntent().getStringExtra("from");
        from.setText(frommap);
        dest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MapActivity.class);
                startActivity(i);
            }
        });
        String destmap = getIntent().getStringExtra("dest");
            dest.setText(destmap);



    }

}
