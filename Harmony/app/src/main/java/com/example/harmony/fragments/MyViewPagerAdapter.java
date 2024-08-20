package com.example.harmony.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class MyViewPagerAdapter extends FragmentStateAdapter {
    public static String username;

    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String username) {
        super(fragmentActivity);
        MyViewPagerAdapter.username = username;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle args = new Bundle();
        args.putString("Username", username);
        switch (position){
            case 0:
                HomeFragment Home = new HomeFragment();
                Home.setArguments(args);
                return Home;
            case 1:
                ChatFragment Chat = new ChatFragment();
                Chat.setArguments(args);
                return Chat;
            default:
                HomeFragment Home1 = new HomeFragment();
                Home1.setArguments(args);
                return Home1;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
