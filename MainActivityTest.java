package com.example.fm24mhz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.view.View;
import android.widget.ImageButton;

import com.example.fm24mhz.tologin.fmintro;
import com.example.fm24mhz.tologin.login;

public class MainActivityTest extends Activity implements View.OnClickListener {

    @Override
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
//        initUI();
    }

    private void initUI() {
        final View btnHorizontalNtb = findViewById(R.id.btn_horizontal_ntb);
        btnHorizontalNtb.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        ViewCompat.animate(v)
                .setDuration(200)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setInterpolator(new CycleInterpolator())
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(final View view) {

                    }

                    @Override
                    public void onAnimationEnd(final View view) {
                        startActivity(
                                new Intent(MainActivityTest.this, fmintro.class)
                        );
                    }

                    @Override
                    public void onAnimationCancel(final View view) {

                    }
                })
                .withLayer()
                .start();
    }

    private class CycleInterpolator implements android.view.animation.Interpolator {

        private final float mCycles = 0.5f;

        @Override
        public float getInterpolation(final float input) {
            return (float) Math.sin(2.0f * mCycles * Math.PI * input);
        }
    }
}
