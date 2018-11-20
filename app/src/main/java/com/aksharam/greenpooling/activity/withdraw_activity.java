package com.aksharam.greenpooling.activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.aksharam.greenpooling.R;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import com.aksharam.greenpooling.app.AppConfig;
import com.aksharam.greenpooling.app.AppController;
import com.aksharam.greenpooling.helper.SQLiteHandler;
import com.aksharam.greenpooling.helper.SessionManager;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class withdraw_activity extends AppCompatActivity {
    private TextView balan;
    private EditText accou;
    private EditText amou;
    private Button ok;
    private Button cancel;
    private Spinner metho;
    private SQLiteHandler db;
    private ProgressDialog pDialog;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdraw_money);
        try {

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

            balan = findViewById(R.id.balance);
            accou = findViewById(R.id.account);
            amou = (EditText) findViewById(R.id.amount);
            ok = findViewById(R.id.btn_ok);
            cancel = findViewById(R.id.btn_cancel);
            final Spinner dropdown = findViewById(R.id.pay_methods);
            String[] items = new String[]{"Paytm", "Paypal","Amazon Pay","Google Pay"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            dropdown.setAdapter(adapter);
            Bundle bundle = getIntent().getExtras();
            final String balance = bundle.getString("balances");
            balan.setText("₹ " + balance);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String accoun = accou.getText().toString().trim();
                    String amoun = amou.getText().toString().trim();
                    String paymentmeth = dropdown.getSelectedItem().toString();
                    if (!accoun.isEmpty() && !amoun.isEmpty() && !paymentmeth.isEmpty()) {
                        int bal = Integer.parseInt(balance);
                        int amount = Integer.parseInt(amoun);
                        if (bal >= amount) {
                            int total = bal-amount;
                           // String totals =  total;
                            submitvalues(email,accoun, amoun, paymentmeth,total);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Withdraw amount must smaller than balance",Toast.LENGTH_LONG).show();
                        }

                    } else
                        Toast.makeText(getApplicationContext(), "Enter valid details in all fields", Toast.LENGTH_LONG).show();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * function to verify login details in mysql db
     * */
    private void submitvalues(final String email, final String account,final String amount,final String payment,final int total) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Requesting ...");
        showDialog();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_WITHDRAW, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("id");
                        JSONObject user = jObj.getJSONObject("user");
                        String email = user.getString("email");

                        // Launch login activity
                        Intent intent = new Intent(
                                withdraw_activity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to wallet url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("account",account);
                params.put("amount",amount);
                params.put("payment",payment);
                params.put("total",String.valueOf(total));
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
