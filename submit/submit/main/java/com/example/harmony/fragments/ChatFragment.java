package com.example.harmony.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.harmony.Post_Request;
import com.example.harmony.R;
import com.example.harmony.fragment2.MessageChatModel1;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChatFragment extends Fragment {

    List<MessageChatModel> messageChatModelList =  new ArrayList<MessageChatModel>();
    RecyclerView recyclerView;
    MessageChatAdapter adapter ;

    EditText messageET;
    ImageView sendBtn;

    private Handler handler;
    private Runnable refreshRunnable;
    TextView t;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        messageET = (EditText)view.findViewById(R.id.messageET);
        sendBtn = (ImageView) view.findViewById(R.id.sendBtn);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                t = view.findViewById(R.id.lblusername);
//              UserDetails.setCounsellor_id();
                t.setText(UserDetails.Counsellor_name);
            }
        });

        Callback previous = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    String res = response.body().string();
                    JSONArray json = Post_Request.processJSON(res);
                    if(json != null){
                        try {
                            for(int i = 0; i < json.length(); i++){
                                JSONObject model = json.getJSONObject(i);

                                int type;
                                if(model.getString("Sender").equals(UserDetails.username)){
                                    type = 0;
                                }
                                else {
                                    type = 1;
                                }


                                String[] M = model.getString("Time").split(" ");
                                String [] mm = M[1].split(":");
                                String mmm = mm[0] + ":" + mm[1];
                                MessageChatModel Now = new MessageChatModel(
                                        model.getString("Message"),
                                        mmm,
                                        type
                                );
                                messageChatModelList.add(Now);
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.smoothScrollToPosition(messageChatModelList.size());
                                    adapter = new MessageChatAdapter(messageChatModelList, getActivity());
                                    recyclerView.setAdapter(adapter);
                                }
                            });
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        };

        Post_Request chatting = new Post_Request(previous,"getMessages");
        chatting.add("Sender",UserDetails.username);
        chatting.add("Receiver",UserDetails.Counsellor_id);

        chatting.post();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                String msg = messageET.getText().toString().strip();

                Callback sendmsg = new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(response.isSuccessful()){
                            assert response.body() != null;
                            String res = response.body().string();
                        }
                    }
                };

                Calendar calendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = dateFormat.format(calendar.getTime());

                Post_Request post = new Post_Request(sendmsg,"addMessage");
                post.add("Sender",UserDetails.username);
                post.add("Receiver",UserDetails.Counsellor_id);
                post.add("Message",msg);
                post.add("Time",currentTime);
                post.post();
                String[] M = currentTime.split(" ");
                String [] mm = M[1].split(":");
                String mmm = mm[0] + ":" + mm[1];
                MessageChatModel model = new MessageChatModel(
                        msg,
                        mmm,
                        0
                );

                messageChatModelList.add(model);
//                update messages
                recyclerView.smoothScrollToPosition(messageChatModelList.size());
                adapter.notifyDataSetChanged();
                messageET.setText("");
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler = new Handler();
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        t.setText(UserDetails.Counsellor_name);
                    }
                });
                List<MessageChatModel> messageChatModelList2 =  new ArrayList<MessageChatModel>();

                Callback previous = new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(response.isSuccessful()){
                            assert response.body() != null;
                            String res = response.body().string();
                            JSONArray json = Post_Request.processJSON(res);
                            if(json != null){
                                try {
                                    for(int i = 0; i < json.length(); i++){
                                        JSONObject model = json.getJSONObject(i);

                                        int type;
                                        if(model.getString("Sender").equals(UserDetails.username)){
                                            type = 0;
                                        }
                                        else {
                                            type = 1;
                                        }

                                        String[] M = model.getString("Time").split(" ");
                                        String [] mm = M[1].split(":");
                                        String mmm = mm[0] + ":" + mm[1];
                                        MessageChatModel Now = new MessageChatModel(
                                                model.getString("Message"),
                                                mmm,
                                                type
                                        );
                                        messageChatModelList2.add(Now);
                                    }

                                    if(messageChatModelList2.size() != messageChatModelList.size()){
                                        messageChatModelList = messageChatModelList2;
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                recyclerView.smoothScrollToPosition(messageChatModelList.size());
                                                adapter = new MessageChatAdapter(messageChatModelList, getActivity());
                                                recyclerView.setAdapter(adapter);
                                            }
                                        });
                                    }

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                };

                Post_Request chatting = new Post_Request(previous,"getMessages");
                chatting.add("Sender",UserDetails.username);
                chatting.add("Receiver",UserDetails.Counsellor_id);

                chatting.post();

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
}