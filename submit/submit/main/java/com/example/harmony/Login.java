package com.example.harmony;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.harmony.fragment2.CounsDetails;
import com.example.harmony.fragment2.CounsPage;
import com.example.harmony.fragments.UserPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView SignUp = (TextView) findViewById(R.id.lblSignUp);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent form = new Intent(Login.this,SignUp.class);
                startActivity(form);
            }
        });
        ((Button) findViewById(R.id.btnLogin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logging_in();
            }
        });
        ((TextView) findViewById(R.id.lblForgotPass)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this,ForgotPassword.class);
                startActivity(i);
            }
        });
        ((TextView) findViewById(R.id.lblSignUpCouns)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this,SignUpCounsellor.class);
                startActivity(i);
            }
        });
    }
    public void Logging_in(){
        String username = ((TextView) findViewById(R.id.txtUsername)).getText().toString().trim();
        String password = ((TextView) findViewById(R.id.txtPassword)).getText().toString().trim();

        if(!(username.equals("") || password.equals(""))){
            Callback login_counselor = new Callback() {
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
                                JSONObject user = js_user.getJSONObject(0);
                                Intent form = new Intent(Login.this, CounsPage.class);
                                CounsDetails.Name = user.getString("Fname");
                                form.putExtra("Couns_ID", user.getString("Couns_ID"));
                                form.putExtra("ID", user.getString("Problem_ID"));
                                startActivity(form);
                                return;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                error();
                            }
                        });
                    }
                }
            };

            Callback login_user = new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        final String res = response.body().string();
                        JSONArray js_user = Post_Request.processJSON(res);
                        if (js_user != null) {
                            try {
                                JSONObject user = js_user.getJSONObject(0);
                                Intent form = new Intent(Login.this, UserPage.class);
                                form.putExtra("Username", user.getString("Username"));
                                startActivity(form);
                                return;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Post_Request counselor = new Post_Request(login_counselor, "login_counsellor");
                        counselor.add("Couns_ID", username);
                        counselor.add("Password", password);
                        counselor.post();
                    }
                }
            };

            Post_Request user = new Post_Request(login_user,"login_user");
            user.add("Username",username);
            user.add("Password",password);
            user.post();
        }
        else{
            error();
        }
    }
    public void error(){
        ((TextView) findViewById(R.id.lblError)).setVisibility(View.VISIBLE);
    }
}