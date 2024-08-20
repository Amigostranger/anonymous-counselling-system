package com.example.harmony;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.harmony.fragment2.CounsPage;

public class MoveCounsellor extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_counsellor);
        ((Button)findViewById(R.id.button55)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change();
            }
        });
    }
    public void change(){
        Intent form = new Intent(MoveCounsellor.this, CounsPage.class);
        startActivity(form);
    }
}