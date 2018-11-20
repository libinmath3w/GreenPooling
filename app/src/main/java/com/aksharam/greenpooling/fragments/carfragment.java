package com.aksharam.greenpooling.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.aksharam.greenpooling.R;
import com.aksharam.greenpooling.activity.AddvehicleActivity;
import com.aksharam.greenpooling.activity.MainActivity;
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

public class carfragment extends android.support.v4.app.Fragment {
    private Button btnadd;
    private Button btncancel;
    private CheckBox helmet;
    private EditText regno;
    private EditText fare;
    private EditText make;
    private String emailid;
    private ProgressDialog pDialog;
    public carfragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AddvehicleActivity activity = (AddvehicleActivity) getActivity();
        emailid = activity.getMyData();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car, container,false);
        fare = (EditText) view.findViewById(R.id.faress);
        regno = (EditText) view.findViewById(R.id.registerno);
        make = (EditText) view.findViewById(R.id.make_category);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pDialog = new ProgressDialog(this.getActivity());
        pDialog.setCancelable(false);
        btnadd=(Button) getView().findViewById(R.id.btn_add);
        helmet = (CheckBox) getView().findViewById(R.id.check_helmets);
        btncancel = (Button) getView().findViewById(R.id.btn_cancel);
        final Spinner dropdown = getView().findViewById(R.id.model);
        String[] items = new String[]{"SUV", "MUV","Sedan","Hatch Back"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        final Spinner ofr_seat = getView().findViewById(R.id.ofrseats);
        String[] ofritems = new String[]{"1","2","3","4","5"};
        ArrayAdapter<String> ofradapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, ofritems);
        ofr_seat.setAdapter(ofradapter);
        final String seats = ofr_seat.getSelectedItem().toString();
        final String model = dropdown.getSelectedItem().toString();
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!seats.isEmpty() && !getRegno().isEmpty() && !getMake().isEmpty() && !model.isEmpty() && !getRegno().isEmpty()) {

                        submitvalues(emailid,seats,getFare(),getRegno(),getMake(),model);
                    }
                    else {
                        Toast.makeText(getActivity(), "Please fill all required fields", Toast.LENGTH_LONG).show();
                    }
                }
               catch (Exception e){
                   Toast.makeText(getActivity(), "Error Occured", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public String getFare() {
        String fares = fare.getText().toString().trim();
        return fares;
    }

    public String getRegno() {
        String Regno = regno.getText().toString().trim();
        return Regno;
    }

    public String getMake() {
        String makencate = make.getText().toString().trim();
        return makencate;
    }

    /**
     * function to verify login details in mysql db
     * */
    private void submitvalues(final String email, final String seats, final String fares,final String regno,final String makencat,final String model) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        final String type = "Car";

        pDialog.setMessage("Requesting ...");
        showDialog();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADDVEHICLE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        //session.setLogin(true);

                        // Now store the user in SQLite
                        //String uid = jObj.getString("id");
                        //JSONObject user = jObj.getJSONObject("user");
                        // String email = user.getString("email");

                        // Launch login activity
                        Intent intent = new Intent(
                                getActivity(),
                                MainActivity.class);
                        startActivity(intent);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getActivity(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Intent intent = new Intent(
                            getActivity(),
                            MainActivity.class);
                    startActivity(intent);
                    // JSON error
                    e.printStackTrace();
                    //Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to wallet url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("seats",seats);
                params.put("fares",fares);
                params.put("regno",regno);
                params.put("makencat",makencat);
                params.put("model",model);
                params.put("type",type);
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