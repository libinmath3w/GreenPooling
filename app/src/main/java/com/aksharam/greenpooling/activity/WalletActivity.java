package com.aksharam.greenpooling.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import com.aksharam.greenpooling.R;
import com.aksharam.greenpooling.helper.SQLiteHandler;
import com.aksharam.greenpooling.helper.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import com.aksharam.greenpooling.helper.transactions;
import com.aksharam.greenpooling.helper.transactionsAdaptor;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.aksharam.greenpooling.app.AppConfig;
import com.aksharam.greenpooling.app.AppController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalletActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private TextView text,balan;
    private String balance;
    private SQLiteHandler db;
    private SessionManager session;
    private List<transactions> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private transactionsAdaptor mAdapter;
    private String method;
    private String amount;
    private String account;
    // Creating JSON Parser object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wallet);

        balan=(TextView) findViewById(R.id.show_balance);
        btn1=(Button)findViewById(R.id.btn_addmoney);
        btn2=(Button)findViewById(R.id.btn_withdraw);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new transactionsAdaptor(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
//        prepareMovieData();
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        //call checkbalance function to get balance
        checkbalance(email);
        getTransactions(email);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AddmoneyActivity.class);
                startActivity(i);
                finish();
            }
        });


      btn2.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              Intent i = new Intent(WalletActivity.this,withdraw_activity.class);

              //Create the bundle
              Bundle bundle = new Bundle();
              //Add your data to bundle
              bundle.putString("balances", balance);

              //Add the bundle to the intent
              i.putExtras(bundle);
              //Fire that second activity
              startActivity(i);

             // Intent is = new Intent(getApplicationContext(),withdraw_activity.class);
             // startActivity(is);
              finish();
          }
      });


    }

    /**
     * function to verify login details in mysql db
     * */
    private void checkbalance(final String email) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_WALLET, new Response.Listener<String>() {

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
                         balance = user.getString("balance");

                        // showing balance
                        balan.setText("₹ " + balance);

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
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * function to verify login details in mysql db
     * */
    private void getTransactions(final String email) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_TRANSACTIONS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ARRAY",response);


                try {
                    transactions movie = new transactions(method, account, amount);
                    JSONObject jObj = new JSONObject(response);
                    JSONArray contacts = jObj.getJSONArray("data");
                    Log.d("ARRAY1",contacts.toString());
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);


                        String email = c.getString("email");
                        String method = c.getString("method");
                        String amount = c.getString("amount");
                        String account = c.getString("account");
                        Log.d("ARRAY1",account);
                        // Phone node is JSON Object
                        movie = new transactions(method, amount, account);
                        movieList.add(movie);
                    }
                    mAdapter.notifyDataSetChanged();
          boolean error = jObj.getBoolean("error");

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
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

//    private void prepareMovieData() {
//        transactions movie = new transactions(method, account, amount);
//        movieList.add(movie);
//
//        movie = new transactions("Inside Out", "Animation, Kids & Family", "2015");
//        movieList.add(movie);
//
//        movie = new transactions("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
//        movieList.add(movie);
//
//        movie = new transactions("Shaun the Sheep", "Animation", "2015");
//        movieList.add(movie);
//
//        movie = new transactions("The Martian", "Science Fiction & Fantasy", "2015");
//        movieList.add(movie);
//
//        movie = new transactions("Mission: Impossible Rogue Nation", "Action", "2015");
//        movieList.add(movie);
//
//        movie = new transactions("Up", "Animation", "2009");
//        movieList.add(movie);
//
//        movie = new transactions("Star Trek", "Science Fiction", "2009");
//        movieList.add(movie);
//
//        movie = new transactions("The LEGO Movie", "Animation", "2014");
//        movieList.add(movie);
//
//
//        mAdapter.notifyDataSetChanged();
//    }
}


