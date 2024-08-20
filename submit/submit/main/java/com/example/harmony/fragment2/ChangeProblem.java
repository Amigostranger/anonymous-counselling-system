package com.example.harmony.fragment2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harmony.Post_Request;
import com.example.harmony.R;
import com.example.harmony.fragments.MyViewPagerAdapter;
import com.example.harmony.fragments.UserDetails;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;


public class ChangeProblem extends Fragment {


    OkHttpClient client = new OkHttpClient();
    ArrayList<String> TalkAbout = new ArrayList<String>();
    String Relationship = "Not";
    String Self = "Not";
    String Academic = "Not";
    String Random = "Not";
    String Depression = "Not";
    String Family = "Not";
    String Loss = "Not";
    String Friends = "Not";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_change_problem, container, false);
        // Inflate the layout for this fragment

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView ttt = view.findViewById(R.id.Counsellor_ID);
        ttt.setText(CounsDetails.Counsellor_id);

        for(int item = 1; item <= 8;item++){
            TextView txt = view.findViewWithTag("txt"+item);
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TalkAbout(view);
                }
            });
        }
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button pro = view.findViewById(R.id.Change);
        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processNew();
            }
        });

        return  view;
    }

    public void processNew() {
        final boolean[] move = {true};
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (TalkAbout.size() == 0) {
                    Toast.makeText(getActivity(), "Please Select what you want to talk about.", Toast.LENGTH_SHORT).show();
                    move[0] = false;
                }
            }
        });

        if (!move[0]) {
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
                    String res = response.body().string();
                    JSONArray js = Post_Request.processJSON(res);
                    if (js != null) {
                        FragmentActivity activity = getActivity();
                        if (activity != null) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(),"Change Successfully",Toast.LENGTH_SHORT).show();
                                    ViewPager2 viewPager = activity.findViewById(R.id.view_pager);

                                    MyViewPagerAdapter2 pagerAdapter = new MyViewPagerAdapter2(activity);

                                    viewPager.setAdapter(pagerAdapter);

                                    viewPager.setCurrentItem(0);
                                }
                            });
                        }
                    }
                }
            }
        };


        addAllQueries(call);
    }

    public void addAllQueries(Callback call){

        Post_Request chat = new Post_Request(call,"Change_prob");
        //
        for(int i= 0;i < TalkAbout.size(); i++){
            String me = TalkAbout.get(i);
            switch (me){
                case "Relationships":
                    Relationship = me;
                    break;
                case "Academics":
                    Academic = me;
                    break;
                case "Loss":
                    Loss = me;
                    break;
                case "Family":
                    Family = me;
                    break;
                case "Depression":
                    Depression = me;
                    break;
                case "Friends":
                    Friends = me;
                    break;
                case "Random":
                    Random = me;
                    break;
                case "Self":
                    Self = me;
                    break;
                default:
                    System.out.println("That's wrong");
            }
        }

        chat.add("Couns_ID",CounsDetails.Counsellor_id);
        chat.add("Relationships",Relationship);
        chat.add("Loss",Loss);
        chat.add("Academics",Academic);
        chat.add("Family",Family);
        chat.add("Depression",Depression);
        chat.add("Friends",Friends);
        chat.add("Self",Self);
        chat.add("Random",Random);
        chat.post(); //====================================================================need to add more counsellors
    }

    public void TalkAbout(View view) {
        TextView t = (TextView) view;
        String name = getResources().getResourceName(t.getId());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //heee
                if (t.getBackground().getAlpha() == 190) {
                    t.getBackground().setAlpha(255);
                    TalkAbout.remove(name.substring(26));
                } else {
                    t.getBackground().setAlpha(190);
                    TalkAbout.add(name.substring(26));
                }
            }
        });
    }




}