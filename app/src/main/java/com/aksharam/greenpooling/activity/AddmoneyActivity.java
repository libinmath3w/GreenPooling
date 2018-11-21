package com.aksharam.greenpooling.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import com.aksharam.greenpooling.R;
import com.aksharam.greenpooling.helper.SQLiteHandler;
import com.aksharam.greenpooling.helper.SessionManager;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;
import com.aksharam.greenpooling.app.AppConfig;
import com.aksharam.greenpooling.app.AppController;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddmoneyActivity extends AppCompatActivity {

    private EditText amou;
    private Button ok;
    private Button cancel;
    private RadioGroup gateway;
    private RadioButton gatewaybutton;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_money);
        amou=(EditText)findViewById(R.id.et_dest);
        ok=(Button) findViewById(R.id.btn_ok);
        cancel=(Button)findViewById(R.id.btn_cancel);
        gateway=(RadioGroup)findViewById(R.id.paymentway);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        // session manager
        session = new SessionManager(getApplicationContext());


        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

       // String name = user.get("name");
        final String email = user.get("email");

        ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                try
                {

                String amount=amou.getText().toString().trim();
                int selectId=gateway.getCheckedRadioButtonId();
                gatewaybutton=(RadioButton) findViewById(selectId);
                String payments=gatewaybutton.getText().toString().trim();
                if(!amount.isEmpty() && !payments.isEmpty()) {
                    int amounts = Integer.parseInt(amount);
                    if(amounts >= 1) {
                        submitvalues(email,payments,amounts);
                    }
                   else {
                        Toast.makeText(getApplicationContext(),"Enter a valid amount",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),"please fill all reqiured fields",Toast.LENGTH_LONG).show();
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Error!",Toast.LENGTH_LONG).show();
}
}
 });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(AddmoneyActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });
}

    /**
     * function to verify login details in mysql db
     * */
    private void submitvalues(final String email, final String account,final int amount) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Requesting ...");
        showDialog();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADDMONEY, new Response.Listener<String>() {

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

                        // Launch home activity
                        Intent intent = new Intent(
                                AddmoneyActivity.this,
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
                params.put("amount",String.valueOf(amount));
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