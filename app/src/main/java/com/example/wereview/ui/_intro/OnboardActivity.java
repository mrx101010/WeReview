package com.example.wereview.ui._intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wereview.MainActivity;
import com.example.wereview.R;
import com.example.wereview.ui._fragment.HomeFragment;
import com.example.wereview.ui._register.register;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class OnboardActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private OnboardAdapter adapter;
    private TabLayout tabLayout;
    private Button buttonSkip, buttonNext, buttonLogin;
    private List<OnboardModel> list;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // session
        if (isSharedPreference()) {
//            Intent intent = new Intent(OnboardActivity.this, TargetcLass.class);
//            startActivity(intent);
//            finish();
        }

        setContentView(R.layout.activity_onboard);

        // init views
        tabLayout = findViewById(R.id.onboard_tab_indicator);
        buttonSkip = findViewById(R.id.onboard_button_skip);
        buttonNext = findViewById(R.id.onboard_button_next);
        buttonLogin = findViewById(R.id.onboard_button_login);

        list = new ArrayList<>();
        list.add(new OnboardModel(getString(R.string.tvTitleOnboard1), getString(R.string.tvBodyOnboard1), R.drawable.onboard1));
        list.add(new OnboardModel(getString(R.string.tvTitleOnboard2), getString(R.string.tvBodyOnboard2), R.drawable.onboard2));
        list.add(new OnboardModel(getString(R.string.tvTitleOnboard3), getString(R.string.tvBodyOnboard3), R.drawable.onboard3));

        // setup view pager & adapter
        viewPager = findViewById(R.id.onboard_viewpager);
        adapter = new OnboardAdapter(this, list);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // set event button
        buttonSkip.setOnClickListener(new onboardSkipScreen());
        buttonNext.setOnClickListener(new onboardNextScreen());
        buttonLogin.setOnClickListener(new onboardLoginScreen());

        settingTabOnboard();
    }

    /**
     * setup tablayout onboarding screen
     */
    private void settingTabOnboard() {
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == list.size() - 1) {
                    settingLastscreen();
                } else {
                    settingNotlastscreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * setting visibilitas button di halaman onboarding screen: flag halaman terakhir
     */
    private void settingLastscreen() {
        buttonNext.setVisibility(View.INVISIBLE);
        buttonSkip.setVisibility(View.INVISIBLE);
        buttonLogin.setVisibility(View.VISIBLE);
    }

    /**
     * setting visibilitas button di halaman onboarding screen: flag bukan halaman terakhir
     */
    private void settingNotlastscreen() {
        buttonNext.setVisibility(View.VISIBLE);
        buttonSkip.setVisibility(View.VISIBLE);
        buttonLogin.setVisibility(View.INVISIBLE);
    }

    /**
     * setting shared preferences saat app pertama kali dijalankan
     */
    private void saveSharedPreferences() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("APP_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("IS_OPENED_FIRST", true);
        editor.apply();
    }

    /**
     * cek status bahwa app pertama kali dijalankan
     *
     * @return true/false
     */
    private Boolean isSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("APP_PREFERENCES", MODE_PRIVATE);
        Boolean isOnboardOpenedFirst = sharedPreferences.getBoolean("IS_OPENED_FIRST", false);
        return isOnboardOpenedFirst;
    }

    /**
     * event button skip di onboarding screen
     */
    private class onboardSkipScreen implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(list.size());
        }
    }

    /**
     * event button next di onboarding screen
     */
    private class onboardNextScreen implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            position = viewPager.getCurrentItem();
            if (position < list.size()) {
                position++;
                viewPager.setCurrentItem(position);
                settingNotlastscreen();
            }

            if (position == list.size() - 1) {
                settingLastscreen();
            }
        }
    }

    /**
     * event button login di onboarding screen terakhir
     */
    private class onboardLoginScreen implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            gotoPhoneRegister();
            //Toast.makeText(OnboardActivity.this, "OK", Toast.LENGTH_SHORT).show();
            //gotoLogin();
        }

        private void gotoPhoneRegister() {
            Intent intent = new Intent(OnboardActivity.this, register.class);
            startActivity(intent);
            // replace session here
            finish();
        }

    }
}
