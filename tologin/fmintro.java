package com.example.fm24mhz.tologin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.fm24mhz.R;

public class fmintro extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ImageButton btn_s = (ImageButton) findViewById(R.id.sellerbtn);
        btn_s.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        login.class);
                intent.putExtra("FLAG", "S");
                startActivity(intent);
            }
        });

        ImageButton btn_b = (ImageButton) findViewById(R.id.buyerbtn);
        btn_b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        login.class);
                intent.putExtra("FLAG", "B");
                startActivity(intent);
            }
        });
    }
}
