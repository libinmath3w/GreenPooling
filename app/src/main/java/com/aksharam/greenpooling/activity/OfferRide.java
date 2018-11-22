package com.aksharam.greenpooling.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aksharam.greenpooling.R;
import com.aksharam.greenpooling.helper.SQLiteHandler;
import com.aksharam.greenpooling.helper.SessionManager;

import java.util.HashMap;

public class OfferRide extends AppCompatActivity {
    private Button ok,cancel;
    private Spinner avlseats,my_vehicle;
    private String frommapstemp , localfrom,vehiclesspinner,timestext;
    private EditText from,dest;
    private TextView times;
    private ImageView fromgps,destgps,timechoose;
    private SQLiteHandler db;
    private ProgressDialog pDialog;
    private SessionManager session;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_ride);
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        // session manager
        session = new SessionManager(getApplicationContext());

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        final String email = user.get("email");

        final Spinner dropdown = findViewById(R.id.my_vehicles);
        String[] items = new String[]{"CAR", "BIKE"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        vehiclesspinner = dropdown.getSelectedItem().toString();
        final Spinner seats = findViewById(R.id.available_seats);
        if (vehiclesspinner.equals("CAR")) {
            String[] item = new String[]{"1", "2","3", "4","5", "6"};
            ArrayAdapter<String> adapters = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, item);
            seats.setAdapter(adapters);
        }
        else {
            String[] item = new String[]{"1"};
            ArrayAdapter<String> adapters = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, item);
            dropdown.setAdapter(adapters);
        }
        fromgps = (ImageView)findViewById(R.id.fromgps);
        destgps = (ImageView)findViewById(R.id.destgps);
        timechoose= (ImageView)findViewById(R.id.clocks);
        from = (EditText)findViewById(R.id.et_from);
        dest = (EditText)findViewById(R.id.et_dest);
        times = (TextView) findViewById(R.id.tt_time);
        ok = (Button)findViewById(R.id.btn_ok);
        cancel = (Button)findViewById(R.id.btn_cancel);
        timestext = "test";
        if (timestext != null ) {
            times.setText(timestext);
        }
        else {
            times.setText("");
        }
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

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getApplicationContext(),vehiclesspinner,Toast.LENGTH_LONG).show();
                }
            });

            timechoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(),timepickactivity.class);
                    startActivity(i);
                }
            });



    }

}
