package com.aksharam.greenpooling.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aksharam.greenpooling.R;
import com.aksharam.greenpooling.helper.SQLiteHandler;
import com.aksharam.greenpooling.helper.SessionManager;

import java.util.HashMap;

public class ChangepasswordActivity extends AppCompatActivity {

    private EditText chanpass;
    private EditText newpass;
    private EditText curren;
    private Button saves;
    private Button cancels;
    private SQLiteHandler db;
    private SessionManager session;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        chanpass=(EditText)findViewById(R.id.conform_password);
        newpass=(EditText)findViewById(R.id.new_password);
        curren=(EditText)findViewById(R.id.current_password);
        cancels=(Button)findViewById(R.id.btn_cancel);
        saves=(Button)findViewById(R.id.btn_save);
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String names = user.get("name");
        final String emails = user.get("email");
        password = user.get("password");
        EditText ttName = (EditText)findViewById(R.id.txt_name);
        ttName.setText(names);
        saves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldpassword = curren.getText().toString().trim();
                String newpassword = newpass.getText().toString().trim();
                String verifypassword= chanpass.getText().toString().trim();
                if (!oldpassword.isEmpty() && !newpassword.isEmpty() && !verifypassword.isEmpty()){
                    if (newpassword.equals(verifypassword)) {
                        submitvalues (oldpassword,newpassword,emails);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Both passwords are not match",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        cancels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChangepasswordActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
    private void submitvalues(final String oldpasst,final String newpassword,String emaild){

        if (password.equals(newpassword)) {
            db.changepassword(emaild,password);
            Toast.makeText(getApplicationContext(),"Your password has been changed successfully. Please login again.",Toast.LENGTH_LONG).show();
            logoutUser();
        }
        else {
            Toast.makeText(getApplicationContext(),"Old password and New passwords are not match",Toast.LENGTH_LONG).show();
        }

    }
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(ChangepasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
