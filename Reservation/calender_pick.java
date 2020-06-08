package com.example.fm24mhz.Reservation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fm24mhz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class calender_pick extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_pick);

        pickButton = (ImageButton) findViewById(R.id.pick);
        recyclerView = findViewById(R.id.reservationlist);

        SharedPreferences fPref = getSharedPreferences("date", MODE_PRIVATE);
        year = fPref.getString("YEAR", "empty");
        month = fPref.getString("MONTH", "empty");
        day = fPref.getString("DAY", "empty");

        Intent prev = getIntent();
        final String flag = prev.getStringExtra("FLAG");

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        });*/

        pickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equals("B")) {
                    Toast.makeText(getApplicationContext(), "판매자만 활동할 수 있습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(
                            getApplicationContext(),
                            schedule_time.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        getReservation();
    }

    private void getReservation() {
        items = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.child("DAY").getValue().toString().equals(day)) {
                        ReservationItem item = new ReservationItem(snapshot.child("ID").getValue().toString(),
                                snapshot.child("LOCATION").getValue().toString(),
                                snapshot.child("STRTIME").getValue().toString(),
                                snapshot.child("STRMINUTE").getValue().toString(),
                                snapshot.child("ENDTIME").getValue().toString(),
                                snapshot.child("ENDMINUTE").getValue().toString());

                        items.add(item);
                    }
                    }
                adapter = new ReservationRecyclerAdapter(calender_pick.this, items);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    ImageButton pickButton;
    String year, month, day;
    ArrayList<ReservationItem> items;
    RecyclerView recyclerView;
    ReservationRecyclerAdapter adapter;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("reservation");
}
