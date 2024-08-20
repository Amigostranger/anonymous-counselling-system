package com.example.harmony.fragment2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harmony.Post_Request;
import com.example.harmony.R;
import com.example.harmony.fragments.MyViewPagerAdapter;
import com.example.harmony.fragments.UserDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttp;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class Patients extends Fragment {
    ArrayList<String> names = new ArrayList<>();

    String counsId;
    LinearLayout scrolly;
    String getjson = "nne";

    static boolean changed = false;
    private Handler handler;
    private Runnable refreshRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patients, container, false);


        Bundle args = getArguments();
        if (args != null) {
            counsId = args.getString("Couns_Id");
            if(CounsDetails.Counsellor_id.equals("-1")){
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Get the ViewPager2 reference
                            ViewPager2 viewPager = activity.findViewById(R.id.view_pager);

                            // Create the adapter for the ViewPager2
                            MyViewPagerAdapter2 pagerAdapter = new MyViewPagerAdapter2(activity);

                            // Set the adapter on the ViewPager2
                            viewPager.setAdapter(pagerAdapter);

                            // Change to the desired fragment within the ViewPager2
                            viewPager.setCurrentItem(2); // Change to the fragment at position 1
                        }
                    });
                }
            }
        }

        scrolly = view.findViewById(R.id.all);

        post();



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        handler = new Handler();
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                post();
                System.out.println(CounsDetails.Counsellor_id.equals("-1"));
                if(CounsDetails.getProblem.equals("-1")){
                    Toast.makeText(getActivity(),"Go to change problems and edit",Toast.LENGTH_LONG).show();
                }

                handler.postDelayed(refreshRunnable,500);
            }
        };

        handler.post(refreshRunnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(refreshRunnable);
    }

    int moo = 0;
    public void processJson(String json) throws JSONException {
        ArrayList<String> names2 = new ArrayList<>();
        JSONArray ja=new JSONArray(json);
        for(int i=0;i<ja.length();i++){
            JSONObject jo=ja.getJSONObject(i);
            String name=jo.getString("Username");
            names2.add(i, name);
        }

        if(names2.size() == names.size()){
            changed = false;
            moo++;
        }
        else{
            moo = 0;
            names.clear();
            names.addAll(names2);
            changed = true;
        }
    }

    public void setNames(ArrayList<String> names){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<names.size();i++){
                    System.out.println(i);
                    // Inflate the custom_layout.xml
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View customView = inflater.inflate(R.layout.showuser, scrolly, false);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    TextView t = (TextView) customView.findViewById(R.id.username);
                    t.setText(names.get(i));
                    t.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClicked(v);
                        }
                    });
                    t.setTag(names.get(i));
                    LinearLayout l = (LinearLayout) customView.findViewById(R.id.line1);
                    l.setTag(names.get(i));
                    l.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClicked(v);
                        }
                    });

                    // Add the inflated layout to the parent LinearLayout
                    scrolly.addView(customView);
                }
            }
        });
    }


    public void post(){
        Callback me = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                String json = response.body().string();
                try {
                    processJson(json);
                    if(moo == 0){
                        setNames(names);
                        moo++;
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

        };
        Post_Request noww = new Post_Request(me,getjson);
        noww.add("Couns_ID",MyViewPagerAdapter2.Couns_ID);
        noww.post();
    }


    public void onClicked(View v){
        String t = (String) v.getTag();
//        System.out.println(t);
        CounsDetails.Patient = t;

        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Get the ViewPager2 reference
                    ViewPager2 viewPager = activity.findViewById(R.id.view_pager);

                    // Create the adapter for the ViewPager2
                    MyViewPagerAdapter2 pagerAdapter = new MyViewPagerAdapter2(activity);

                    // Set the adapter on the ViewPager2
                    viewPager.setAdapter(pagerAdapter);

                    // Change to the desired fragment within the ViewPager2
                    viewPager.setCurrentItem(1); // Change to the fragment at position 1
                }
            });
        }

    }
}