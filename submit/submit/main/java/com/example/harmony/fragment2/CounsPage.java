package com.example.harmony.fragment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.example.harmony.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class CounsPage extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewpager2;
    MyViewPagerAdapter2 myViewPagerAdapter;
   String Couns_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_page);
        Intent me = getIntent();
        Couns_id = me.getStringExtra("Couns_ID");
        CounsDetails.getProblem = me.getStringExtra("ID");
        CounsDetails.Counsellor_id = Couns_id;

        tabLayout = findViewById(R.id.tab_layout);
        viewpager2 = findViewById(R.id.view_pager);
        myViewPagerAdapter = new MyViewPagerAdapter2(this,Couns_id);
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

    }
}
