package com.example.fm24mhz.Reservation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;

import com.example.fm24mhz.R;

public class calender extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender);

        Intent prev = getIntent();
        final String flag = prev.getStringExtra("FLAG");
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        });*/

        final DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);

        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        schedule_time.year = year; schedule_time.month = monthOfYear; schedule_time.day = dayOfMonth;
                        Intent intent = new Intent(
                                getApplicationContext(),
                                calender_pick.class);
                        intent.putExtra("FLAG", flag);
                        SharedPreferences fPref = getSharedPreferences("date", MODE_PRIVATE);
                        SharedPreferences.Editor editor = fPref.edit();
                        editor.putString("YEAR", Integer.toString(datePicker.getYear()));
                        editor.putString("MONTH", Integer.toString(datePicker.getMonth()+1));
                        editor.putString("DAY", Integer.toString(datePicker.getDayOfMonth()));
                        editor.commit();
                        startActivity(intent);
                        finish();
                    }
                });
    }
}
