package com.example.fm24mhz.Reservation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fm24mhz.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.IOException;
import java.util.List;

public class schedule_time extends AppCompatActivity implements OnMapReadyCallback {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduletime);

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

        tp = (TimePicker) findViewById(R.id.start_time);
        tp2 = (TimePicker) findViewById(R.id.end_time);

        tp.setIs24HourView(true);
        tp2.setIs24HourView(true);

        searchButton = (Button) findViewById(R.id.searchButton);
        searchText = (EditText) findViewById(R.id.searchText);

        reservating = (Button) findViewById(R.id.reservating);
        reservating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchText.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "장소를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(tp.getCurrentHour() > tp2.getCurrentHour()) {
                    Toast.makeText(getApplicationContext(), "시간이 잘못 설정됐습니다.", Toast.LENGTH_SHORT).show();
                }
                else if(tp.getCurrentHour() == tp2.getCurrentHour() && tp.getCurrentMinute() > tp2.getCurrentMinute()) {
                    Toast.makeText(getApplicationContext(), "시간이 잘못 설정됐습니다.", Toast.LENGTH_SHORT).show();
                }
                else Reservation();
            }
        });

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @UiThread
    @Override
    public void onMapReady(@NonNull final NaverMap naverMap) {
        map = naverMap;
        geocoder = new Geocoder(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = searchText.getText().toString();
                List<Address> addressList = null;
                try {
                    addressList = geocoder.getFromLocationName(str, 10);
                } catch (IOException e) { e.printStackTrace(); }

                if(addressList == null) Toast.makeText(getApplicationContext(), "주소를 입력해주세요.", Toast.LENGTH_SHORT).show();
                else {
                    if (addressList.size() == 0)
                        Toast.makeText(getApplicationContext(), "해당되는 주소 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    else {
                        Address addr = addressList.get(0);
                        latitude = addr.getLatitude();
                        longitude = addr.getLongitude();

                        LatLng point = new LatLng(latitude, longitude);

                        Marker marker = new Marker();
                        marker.setCaptionText("Search Location");
                        marker.setPosition(point);

                        marker.setMap(map);

                        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(point).animate(CameraAnimation.Easing, 2000);
                        naverMap.moveCamera(cameraUpdate);
                    }
                }

            }
        });
    }

    public void Reservation() {
        SharedPreferences fPref = getSharedPreferences("user", MODE_PRIVATE);
        String userid = fPref.getString("USERID", "empty");
        SharedPreferences fPref2 = getSharedPreferences("date", MODE_PRIVATE);
        String year = fPref2.getString("YEAR", "empty");
        String month = fPref2.getString("MONTH", "empty");
        String day = fPref2.getString("DAY", "empty");

        String USERID = userid;
        String LOCATION = str;
        String LAT = String.valueOf(latitude);
        String LNG = String.valueOf(longitude);
        String YEAR = year;
        String MONTH = month;
        String DAY = day;
        String STRTIME = Integer.toString(tp.getCurrentHour());
        String STRMINUTE = Integer.toString(tp.getCurrentMinute());
        String ENDTIME = Integer.toString(tp2.getCurrentHour());
        String ENDMINUTE = Integer.toString(tp2.getCurrentMinute());

        insertData(USERID, LOCATION, LAT, LNG, YEAR, MONTH, DAY, STRTIME, STRMINUTE, ENDTIME, ENDMINUTE);
    }

    private void insertData(String USERID, String LOCATION, String LAT, String LNG, String YEAR, String MONTH,
                            String DAY, String STRTIME, String STRMINUTE, String ENDTIME, String ENDMINUTE) {
        String key = databaseReference.push().getKey();
        databaseReference.child(key).child("ID").setValue(USERID);
        databaseReference.child(key).child("LOCATION").setValue(LOCATION);
        databaseReference.child(key).child("LAT").setValue(LAT);
        databaseReference.child(key).child("LNG").setValue(LNG);
        databaseReference.child(key).child("YEAR").setValue(YEAR);
        databaseReference.child(key).child("MONTH").setValue(MONTH);
        databaseReference.child(key).child("DAY").setValue(DAY);
        databaseReference.child(key).child("STRTIME").setValue(STRTIME);
        databaseReference.child(key).child("STRMINUTE").setValue(STRMINUTE);
        databaseReference.child(key).child("ENDTIME").setValue(ENDTIME);
        databaseReference.child(key).child("ENDMINUTE").setValue(ENDMINUTE);

        Intent intent = new Intent(schedule_time.this, calender.class);
        startActivity(intent);
        finish();
    }

    public static int year, month, day;
    private EditText searchText;
    private Button searchButton, reservating;
    private TimePicker tp, tp2;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap map;
    private Geocoder geocoder;
    private String str;
    private double latitude, longitude;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("reservation");
}
