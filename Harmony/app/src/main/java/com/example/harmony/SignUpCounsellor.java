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

import com.example.harmony.fragment2.CounsDetails;
import com.example.harmony.fragment2.CounsPage;
import com.example.harmony.fragments.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignUpCounsellor extends AppCompatActivity {
    int last_person = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_counsellor);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView login = (TextView) findViewById(R.id.lblLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent form = new Intent(SignUpCounsellor.this, Login.class);
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
        String Fname = ((EditText) findViewById(R.id.txtFname)).getText().toString().trim();
        String Lname = ((EditText) findViewById(R.id.txtLname)).getText().toString().trim();
        String email = ((EditText) findViewById(R.id.txtEmail)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.txtPassword)).getText().toString().trim();
        String password_re = ((EditText) findViewById(R.id.txtPassword_re)).getText().toString().trim();

        Callback add_couns = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                System.out.println("hj,dtsb uruyrdsy");
                if(response.isSuccessful()){
                    assert response.body() != null;
                    final String res = response.body().string();
                    JSONArray js_user = Post_Request.processJSON(res);
                    if(js_user!=null) {
                        System.out.println("hj");
                        try {
                            String name = "";
                            String id = "";
                            for (int i = 0; i < js_user.length(); i++) {
                                JSONObject me = js_user.getJSONObject(i);
                                id =  me.getString("Couns_ID");
                                name = me.getString("Fname");

                            }
                            System.out.println(id +":"+ name);
                            CounsDetails.Name = name;
                            CounsDetails.Counsellor_id = id;
                            Intent form = new Intent(SignUpCounsellor.this, CounsPage.class);
                            form.putExtra("Couns_ID", id);
                            form.putExtra("ID", "-1");
                            startActivity(form);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        if(!(Fname.equals("") || Lname.equals("") || password.equals("")) && password_re.equals(password) || email.equals("")){
            String url_add = "add_counsellor";
            Post_Request add_couns_in = new Post_Request(add_couns,url_add);
            add_couns_in.add("Password",password);
            add_couns_in.add("Email",email);
            add_couns_in.add("Fname",Fname);
            add_couns_in.add("Lname",Lname);
            add_couns_in.post();
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