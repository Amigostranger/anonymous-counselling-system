package com.example.harmony;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.harmony.fragments.UserPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignUp extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        TextView login = (TextView) findViewById(R.id.lblLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent form = new Intent(SignUp.this, Login.class);
                startActivity(form);
            }
        });
        ((Button) findViewById(R.id.btnSignUp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process1();
            }
        });
    }

    public void process1(){

        String username = ((EditText) findViewById(R.id.txtUsername)).getText().toString().trim();
        String email = ((EditText) findViewById(R.id.txtEmail)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.txtPassword)).getText().toString().trim();
        String password_re = ((EditText) findViewById(R.id.txtPassword_re)).getText().toString().trim();

        Callback add_user = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    Intent form = new Intent(SignUp.this, UserPage.class);
                    form.putExtra("Username",username);
                    startActivity(form);
                }
            }
        };


        Callback user_only_callback = new Callback() {
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

                    if(js_user!=null){
                        try {
                            for(int i = 0; i < js_user.length(); i++){
                                JSONObject me = js_user.getJSONObject(i);
                                String user_name = me.getString("Username");
                                if(user_name.equals(username)){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((TextView) findViewById(R.id.lblError)).setVisibility(View.VISIBLE);
                                        }
                                    });
                                    return;
                                }
                            }

                            String url_add = "add_user";
                            Post_Request add_user_in = new Post_Request(add_user,url_add);
                            add_user_in.add("Username",username);
                            add_user_in.add("Password",password);
                            add_user_in.add("Email",email);
                            add_user_in.add("Couns_ID","-1");
                            add_user_in.add("Problem_ID","-1");
                            add_user_in.post();
                        }
                        catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        if(!(username.equals("") || password.equals("")) && password_re.equals(password)){
            String url = "all_users";
            Post_Request user_request = new Post_Request(user_only_callback,url);
            user_request.post();
        }
        else {
            error();
        }
    }
    @SuppressLint("ResourceAsColor")
    public void error(){
        TextView t = (TextView) findViewById(R.id.lblError);
        t.setVisibility(View.VISIBLE);
        t.setText("enter correct details");
        t.setTextColor(getResources().getColor(R.color.red));
    }
}