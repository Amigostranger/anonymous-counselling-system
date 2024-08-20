package com.example.harmony.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harmony.Post_Request;
import com.example.harmony.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import java.util.Random;

public class HomeFragment extends Fragment {

    OkHttpClient client = new OkHttpClient();
    ArrayList<String> Feelings = new ArrayList<String>();
    ArrayList<String> TalkAbout = new ArrayList<String>();
    String username = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Callback Quote_Of_The_Day = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    final String res = response.body().string();
                    JSONArray js = Post_Request.processJSON(res);
                    if(js!=null){
                        Random rand = new Random();
                        int n = 0;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject me = js.getJSONObject(n);

                                    TextView msg = view.findViewById(R.id.lblMessage);
                                    String m = me.getString("q");
                                    msg.setText(m);

                                    TextView aur = view.findViewById(R.id.lblAuthor);
                                    m = me.getString("a");
                                    aur.setText("-"+m);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                    }
                }
            }
        };

        String url = "https://zenquotes.io/api/today";
        Post_Request q = new Post_Request(Quote_Of_The_Day,url);
        q.post();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            String username = args.getString("Username");

            this.username = UserDetails.username;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView t = view.findViewById(R.id.lblusername);
                    t.setText(username);
                }
            });
        }

        Button btnChat = view.findViewById(R.id.btnChat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processNew();
            }
        });

        for(int item = 1; item <= 10;item++){
            ImageView img = view.findViewWithTag("img" + item);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Clicked(view);
                }
            });
        }

        for(int item = 1; item <= 8;item++){
            TextView txt = view.findViewWithTag("txt"+item);
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TalkAbout(view);
                }
            });
        }

    }

    public void processNew(){
        final boolean[] move = {true};
        getActivity().runOnUiThread(new Runnable() {

        @Override
        public void run() {
            if (Feelings.size() == 0){
                Toast.makeText(getActivity(), "Please tell us how are you feeling.", Toast.LENGTH_SHORT).show();
                move[0] = false;
            }
            else if(TalkAbout.size() == 0){
                Toast.makeText(getActivity(), "Please Select what you want to talk about.", Toast.LENGTH_SHORT).show();
                move[0] = false;
            }
        }
        });

        if(!move[0]){
            return;
        }
        Callback call = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    UserDetails.setCounsellor_id();
//                    UserDetails.Counsellor_id = response.body().string();
//                    UserDetails.getCounsellorName();

                }
            }
        };


        addAllQueries(call);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewPager2 viewPager = getActivity().findViewById(R.id.view_pager);
                MyViewPagerAdapter pagerAdapter = new MyViewPagerAdapter(getActivity());
                viewPager.setAdapter(pagerAdapter);
                viewPager.setCurrentItem(1);
            }
        });
    }

    public void addAllQueries(Callback call){
        Post_Request chat = new Post_Request(call,"deal"); //000000000000000000
        String what = TalkAbout.get(0);
        chat.add("Username",this.username);
        chat.add("what",what);
        chat.post(); //====================================================================need to add more counsellors
    }

    public void Clicked(View view) {
        ImageView t = (ImageView) view;
        String name = getResources().getResourceName(t.getId());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(t.getImageAlpha() == 190){
                    t.setImageAlpha(255);
                    Feelings.remove(name.substring(26));
                }
                else {
                    t.setImageAlpha(190);
                    Feelings.add(name.substring(26));
                }
            }
        });
    }

    public void TalkAbout(View view) {
        TextView t = (TextView) view;
        String name = getResources().getResourceName(t.getId());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (t.getBackground().getAlpha() == 190) {
                    t.getBackground().setAlpha(255);
                    TalkAbout.remove(name.substring(26));
                } else {
                    t.getBackground().setAlpha(190);
                    TalkAbout.add(name.substring(26));
                }
            }
        });

        if(TalkAbout.size() > 1){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "You can only select one Topic", Toast.LENGTH_SHORT).show();
                    t.getBackground().setAlpha(255);
                    TalkAbout.remove(name.substring(26));
                }
            });
        }
    }
}