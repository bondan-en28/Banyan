package com.bintang.banyan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.bintang.banyan.TabMainFragment.TabBerandaFragment;
import com.bintang.banyan.TabMainFragment.TabKebunFragment;
import com.bintang.banyan.TabMainFragment.TabProfileFragment;
import com.bintang.banyan.TabMainFragment.TabSocialFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_beranda:
                    fragment = new TabBerandaFragment();
                    break;
                case R.id.navigation_social:
                    fragment = new TabSocialFragment();
                    break;
                case R.id.navigation_kebun:
                    fragment = new TabKebunFragment();
                    break;
                case R.id.navigation_profile:
                    fragment = new TabProfileFragment();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new TabBerandaFragment());
    }

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
