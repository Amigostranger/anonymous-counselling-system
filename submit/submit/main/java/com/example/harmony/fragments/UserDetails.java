package com.example.harmony.fragments;

import androidx.annotation.NonNull;

import com.example.harmony.Post_Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserDetails {

    static String username = "";
    public static String Counsellor_id = "";
    public static String Counsellor_name= "No Counsellor";
    public static void setCounsellor_id(){
        final String[] name = {""};
        Callback getUser = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    final String res = response.body().string();
                    JSONArray js_user = Post_Request.processJSON(res);
                    if(js_user != null){
                        try {
                            for(int i = 0; i < js_user.length(); i++){
                                JSONObject user = js_user.getJSONObject(i);
                                if(user.getString("Username").equals(UserDetails.username)){
                                    name[0] = user.getString("Couns_ID");

                                    return ;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Post_Request user = new Post_Request(getUser, "all_users");
        user.post();

        while (name[0].equals("")){
            continue;
        }

        if(name[0].equals("-1")){
            return;
        }
        System.out.println(name[0] + ": in user details 1");
        UserDetails.Counsellor_id =  name[0];
        UserDetails.Counsellor_name = getCounsellorName();
        System.out.println(UserDetails.Counsellor_name + ": in user details2");
    }
    public static String getCounsellorName(){
        final String[] name = {""};
        Callback getCounselor = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    final String res = response.body().string();
                    JSONArray js_user = Post_Request.processJSON(res);
                    if(js_user != null){
                        try {
                            for(int i = 0; i < js_user.length(); i++){
                                JSONObject user = js_user.getJSONObject(i);
                                if(user.getString("Couns_ID").equals(UserDetails.Counsellor_id)){
                                    name[0] = user.getString("Fname");
                                    return ;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Post_Request counselor = new Post_Request(getCounselor, "all_counsellors");
        counselor.post();

        while (name[0].equals("")) continue; ///it must wait for the name

        return name[0];
    }

}
