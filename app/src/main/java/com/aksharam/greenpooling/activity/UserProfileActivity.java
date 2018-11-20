package com.aksharam.greenpooling.activity;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.aksharam.greenpooling.helper.SQLiteHandler;
import com.aksharam.greenpooling.helper.SessionManager;
import com.aksharam.greenpooling.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.w3c.dom.Text;

import java.util.HashMap;

public class UserProfileActivity extends AppCompatActivity {
    private TextView rating;
    private TextView name;
    private TextView emai;
    private TextView gend;
    private TextView phoneno;
    private Button review;
    private SQLiteHandler db;
    private SessionManager session;
    private ActionBar toolbar;
    private ImageView editprofile;
    private ImageView logout;
    private ShimmerFrameLayout mShimmerViewContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        //mShimmerViewContainer = findViewById(R.id.shimmer_view_container);

        //rating=(TextView) findViewById(R.id.txt_rating);
        name=(TextView)findViewById(R.id.txt_name);
        emai=(TextView)findViewById(R.id.txt_email);
        phoneno=(TextView)findViewById(R.id.txt_phone);
        gend=(TextView)findViewById(R.id.txt_gender);
        editprofile= (ImageView)findViewById(R.id.edit_profile);
        logout = (ImageView)findViewById(R.id.logout);

        //revie=(Button)findViewById(R.id.btn_review);
        //review=(Button)findViewById(R.id.btn_reviews);
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String names = user.get("name");
        String email = user.get("email");
        String phone = user.get("phone");
        String gender = user.get("gender");

        //rating.setText("rating");
        name.setText(names);
        emai.setText(email);
        phoneno.setText(phone);
        gend.setText(gender);

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfileActivity.this,ChangepasswordActivity.class);
                startActivity(i);
                finish();
            }
        });
       /* revie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //write code for intent
            }
        });
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //write code for intent
            }
        });

        */

        //mShimmerViewContainer.stopShimmerAnimation();
        //mShimmerViewContainer.setVisibility(View.GONE);
    }

   /* @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    } */

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
