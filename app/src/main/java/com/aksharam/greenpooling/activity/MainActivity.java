package com.aksharam.greenpooling.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import com.aksharam.greenpooling.R;
import com.aksharam.greenpooling.helper.SQLiteHandler;
import com.aksharam.greenpooling.helper.SessionManager;
import com.aksharam.greenpooling.helper.BottomNavigationBehavior;
import com.facebook.shimmer.ShimmerFrameLayout;

public class MainActivity extends AppCompatActivity {

	private TextView txtName;
	private TextView txtEmail;
	private Button btnLogout;
	private ShimmerFrameLayout mShimmerViewContainer;
	private SQLiteHandler db;
	private SessionManager session;
	private ActionBar toolbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		toolbar = getSupportActionBar();
		mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

		toolbar.setTitle("Home");
		//txtName = (TextView) findViewById(R.id.name);
		//txtEmail = (TextView) findViewById(R.id.email);
	     btnLogout = (Button) findViewById(R.id.btnLogout);

		// SqLite database handler
		db = new SQLiteHandler(getApplicationContext());

		// session manager
		session = new SessionManager(getApplicationContext());

		if (!session.isLoggedIn()) {
			logoutUser();
		}


		// Fetching user details from SQLite
		HashMap<String, String> user = db.getUserDetails();

		String name = user.get("name");
		String email = user.get("email");

		// Displaying the user details on the screen
		//txtName.setText(name);
		//txtEmail.setText(email);

		// Logout button click event
		btnLogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				logoutUser();
			}
		});
	}

	/**
	 * Logging out the user. Will set isLoggedIn flag to false in shared
	 * preferences Clears the user data from sqlite users table
	 * */
	private void logoutUser() {
		session.setLogin(false);

		db.deleteUsers();

		// Launching the login activity
		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener() {

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			Fragment fragment;
			switch (item.getItemId()) {
				case R.id.navigation_shop:
				{
					toolbar.setTitle("Shop");
					Intent intent = new Intent(MainActivity.this, maptestactivity.class);
					startActivity(intent);
					finish();
					return true;
				}
				case R.id.navigation_gifts:
				{
					toolbar.setTitle("Offer");
					Intent intent = new Intent(MainActivity.this, WalletActivity.class);
					startActivity(intent);
					finish();
					return true;
				}
				case R.id.navigation_cart: {
					toolbar.setTitle("Add");
					Intent intent = new Intent(MainActivity.this, AddvehicleActivity.class);
					startActivity(intent);
					finish();
					return true;
				}
				case R.id.navigation_profile: {
					toolbar.setTitle("Profile");
					Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
					startActivity(intent);
					finish();
					return true;
				}
			}
			return false;
		}
	};

	private void loadFragment(Fragment fragment) {
		// load fragment
		//FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		//transaction.replace(R.id.frame_container, fragment);
		//transaction.addToBackStack(null);
		//transaction.commit();
	}
	@Override
	public void onResume() {
		super.onResume();
		mShimmerViewContainer.startShimmerAnimation();
	}

	@Override
	public void onPause() {
		mShimmerViewContainer.stopShimmerAnimation();
		super.onPause();
	}
}
