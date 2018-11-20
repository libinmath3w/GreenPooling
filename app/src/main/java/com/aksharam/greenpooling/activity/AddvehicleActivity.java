package com.aksharam.greenpooling.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.aksharam.greenpooling.fragments.bikefragment;
import com.aksharam.greenpooling.fragments.carfragment;
import com.aksharam.greenpooling.helper.SQLiteHandler;
import com.aksharam.greenpooling.helper.SessionManager;
import com.aksharam.greenpooling.R;

public class AddvehicleActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SQLiteHandler db;
    private SessionManager session;
    private String emails;
    private ActionBar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_vehicle_activity);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        toolbar = getSupportActionBar();

        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar.setTitle("Add Vehicle");


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());


        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        emails = user.get("email");
        Bundle bundle = new Bundle();


        // bundle.putString("emails", emails);
// set Fragmentclass Arguments
        //bikefragment fragobj = new bikefragment();
        //fragobj.setArguments(bundle);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new bikefragment(), "Bike");
        adapter.addFragment(new carfragment(), "CAR");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    public String getMyData() {
        return emails;
    }
   /* private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_shop:
                    toolbar.setTitle("Search");
                    return true;
                case R.id.navigation_gifts:
                    toolbar.setTitle("Offer");
                    return true;
                case R.id.navigation_cart: {
                    toolbar.setTitle("Add");
                    Intent intent = new Intent(AddvehicleActivity.this, AddvehicleActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                case R.id.navigation_profile: {
                    toolbar.setTitle("Profile");
                    Intent intent = new Intent(AddvehicleActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
            }
            return false;
        }
    }; */
}
