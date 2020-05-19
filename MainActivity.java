package com.example.fm24mhz;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fm24mhz.Reservation.calender;
import com.example.fm24mhz.mapview.mapview;
import com.example.fm24mhz.mypage.VideoActivity;
import com.example.fm24mhz.mypage.mypage_buyer;
import com.example.fm24mhz.mypage.mypage_seller;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fPref = getSharedPreferences("user", MODE_PRIVATE);
        userId = fPref.getString("USERID", "empty");
        userEmail = fPref.getString("EMAIL", "empty");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View nav_header_view = navigationView.getHeaderView(0);

        TextView nav_header_id_text = (TextView) nav_header_view.findViewById(R.id.myname);
        TextView nav_header_email_text = (TextView) nav_header_view.findViewById(R.id.myemail);
        nav_header_id_text.setText(userId);
        nav_header_email_text.setText(userEmail);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_FM) {
            Intent intent = new Intent(
                    getApplicationContext(),
                    mapview.class);
            startActivity(intent);
        } else if (id == R.id.nav_calender) {
            Intent intent = new Intent(
                    getApplicationContext(),
                    calender.class);
            intent.putExtra("FLAG", "S");
            startActivity(intent);
        } else if (id == R.id.nav_top) {
            Intent intent = new Intent(
                    getApplicationContext(),
                    VideoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_findID) {
            findId();
        } else if (id == R.id.nav_info) {
            Intent intent = new Intent(
                    getApplicationContext(),
                    mypage_seller.class);
            intent.putExtra("ID", userId);
            startActivity(intent);
        } else if (id == R.id.nav_developer) {
            Intent intent = new Intent(
                    getApplicationContext(),
                    developerinfo.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void findId() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("검색");
        builder.setMessage("검색할 ID를 입력하세요.");

        final EditText editText = new EditText(this);

        builder.setView(editText);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id = editText.getText().toString().trim();
                if(TextUtils.isEmpty(id)) {
                    Toast.makeText(MainActivity.this, "ID를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else SearchID(id);
            }
        });

        builder.show();
    }

    public void SearchID(final String id) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("ID").getValue().toString().equals(id)) {
                        isSearched = true;
                        if(snapshot.child("FLAG").getValue().toString().equals("S")) {
                            Intent intent = new Intent(MainActivity.this, mypage_seller.class);
                            intent.putExtra("ID", id);
                            startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(MainActivity.this, mypage_buyer.class);
                            intent.putExtra("ID", id);
                            startActivity(intent);
                        }
                    }
                }
                if (!isSearched) Toast.makeText(getApplicationContext(), "검색하신 ID가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    SharedPreferences fPref;
    String userId, userEmail;
    private boolean isSearched = false;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("login");
}
