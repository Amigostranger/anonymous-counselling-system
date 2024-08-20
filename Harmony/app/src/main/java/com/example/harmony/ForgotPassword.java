package com.example.harmony;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        TextView SignUp = (TextView) findViewById(R.id.lblSignUp);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent form = new Intent(ForgotPassword.this,SignUp.class);
                startActivity(form);
            }
        });
        ((Button) findViewById(R.id.btnProcess)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process();
            }
        });
    }
    public void process(){
        String email = ((TextView) findViewById(R.id.txtEmail)).getText().toString().trim();
        String username = ((TextView) findViewById(R.id.txtUsername)).getText().toString().trim();
        Callback call = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    final String res = response.body().string();
                    JSONArray json = Post_Request.processJSON(res);
                    if(json != null){
                        ((Button) findViewById(R.id.btnProcess)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ChangePass(email,username);
                            }
                        });
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((TextView) findViewById(R.id.txtEmail)).setVisibility(View.GONE);
                                ((TextView) findViewById(R.id.txtUsername)).setVisibility(View.GONE);
                                ((TextView) findViewById(R.id.lblHeader)).setText("New Password");
                                ((TextView) findViewById(R.id.txtPassword)).setVisibility(View.VISIBLE);
                                TextView mm = (TextView) findViewById(R.id.lblError);
                                mm.setVisibility(View.GONE);
                            }
                        });
                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                error();
                            }
                        });
                    }
                }
            }
        };

        if(email.equals("")){
            TextView mm = (TextView) findViewById(R.id.lblError);
            mm.setText("insert an email");
            mm.setVisibility(View.VISIBLE);
            return;
        }
        if(username.equals("")){
            TextView mm = (TextView) findViewById(R.id.lblError);
            mm.setText("insert an your username");
            mm.setVisibility(View.VISIBLE);
            return;
        }
        Post_Request me = new Post_Request(call,"getEmail");
        me.add("Email",email);
        me.add("Username",email);
        me.post();
    }
    public void ChangePass(String email,String username){
        String password = ((TextView) findViewById(R.id.txtPassword)).getText().toString().trim();
        if(password.equals("")){
            ForgotPassword.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView mm = (TextView) findViewById(R.id.lblError);
                    mm.setText("insert your new password");
                    mm.setVisibility(View.VISIBLE);
                }
            });
            return;
        }
        Callback call = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView) findViewById(R.id.txtPassword)).setVisibility(View.GONE);
                            TextView mm = (TextView) findViewById(R.id.lblError);
                            mm.setText("Password Changed Successful");
                            mm.setTextColor(getResources().getColor(R.color.blue));
                            mm.setVisibility(View.VISIBLE);
                        }
                    });
                    Intent log = new Intent(ForgotPassword.this,Login.class);
                    startActivity(log);

                }
            }
        };

        Post_Request add = new Post_Request(call,"update_password");//UPDATE
        add.add("Email",email);
        add.add("Password",password);
        add.add("Username",username);
        add.post();
    }
    public void error(){
        ((TextView) findViewById(R.id.lblError)).setVisibility(View.VISIBLE);
    }
}