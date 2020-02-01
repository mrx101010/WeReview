package com.example.wereview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.wifi.aware.PeerHandle;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.wereview.ui._fragment.ActivityAdd;
import com.example.wereview.ui._fragment.AddFragment;
import com.example.wereview.ui._fragment.ChatFragment;
import com.example.wereview.ui._fragment.HomeFragment;
import com.example.wereview.ui._fragment.LoveFragment;
import com.example.wereview.ui._fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.inflateMenu(R.menu.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_love:
                            selectedFragment = new LoveFragment();
                            break;
                        case R.id.nav_add:
                            Intent intent = new Intent(MainActivity.this, ActivityAdd.class);
                            startActivity(intent);
                            break;
                        case R.id.nav_chat:
                            selectedFragment = new ChatFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }
                    return loadFragment(selectedFragment);
                }

                private boolean loadFragment(Fragment fragment) {
                    if (fragment != null) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .commit();
                        return true;
                    }
                    return false;
                }};

}


