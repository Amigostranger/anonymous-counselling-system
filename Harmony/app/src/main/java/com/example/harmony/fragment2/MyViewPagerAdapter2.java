package com.example.harmony.fragment2;

import static androidx.viewpager.widget.PagerAdapter.POSITION_UNCHANGED;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;



public class MyViewPagerAdapter2 extends FragmentStateAdapter {
    public static String Couns_ID;

    public MyViewPagerAdapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    public MyViewPagerAdapter2(@NonNull FragmentActivity fragmentActivity, String username) {
        super(fragmentActivity);
        MyViewPagerAdapter2.Couns_ID = username;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle args = new Bundle();
        args.putString("Couns_ID", Couns_ID);
        switch (position){
            case 0:
                Patients Home = new Patients();
                Home.setArguments(args);
                return Home;
            case 1:
                Chats Chat = new Chats();
                Chat.setArguments(args);
                return Chat;
            case 2:
               ChangeProblem Settings = new ChangeProblem();
                Settings.setArguments(args);
                return Settings;
            default:
                Patients  Home1 = new  Patients ();
                Home1.setArguments(args);
                return Home1;
        }
    }
    @Override
    public int getItemCount() {
        return 3;
    }
}

