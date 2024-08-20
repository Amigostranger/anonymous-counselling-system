package com.example.harmony.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.example.harmony.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class UserPage extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewpager2;
    MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        Intent me = getIntent();
        String username = me.getStringExtra("Username");



        tabLayout = findViewById(R.id.tab_layout);
        viewpager2 = findViewById(R.id.view_pager);
        myViewPagerAdapter = new MyViewPagerAdapter(this,username);
        viewpager2.setAdapter(myViewPagerAdapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Objects.requireNonNull(tabLayout.getTabAt(position)).select();
            }
        });
        UserDetails.username = username;
        System.out.println(UserDetails.username + ":    at userpage");
        UserDetails.setCounsellor_id();
    }



}