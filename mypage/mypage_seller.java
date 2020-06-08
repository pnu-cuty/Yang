package com.example.fm24mhz.mypage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fm24mhz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class mypage_seller extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_seller);

        picture = (Button) findViewById(R.id.picture);
        video = (Button) findViewById(R.id.video);
        chat = (Button) findViewById(R.id.chat);
        send = (Button) findViewById(R.id.send);
        inputMsg = (EditText) findViewById(R.id.msg);

        picture.setOnClickListener(onClick);
        video.setOnClickListener(onClick);
        chat.setOnClickListener(onClick);
        send.setOnClickListener(onClick);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        recyclerView = findViewById(R.id.mypageRecyclerView);

        Intent prev = getIntent();
        userid = prev.getStringExtra("ID");

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        getUserInfo();
        getComment();
    }

    public void linearOnClick(View v) {
        imm.hideSoftInputFromWindow(inputMsg.getWindowToken(), 0);
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.picture : break;
                case R.id.video : {
                    Intent intent = new Intent(mypage_seller.this, VideoActivity.class);
                    intent.putExtra("ID", userid);
                    startActivity(intent);
                    break;
                }
                case R.id.chat : break;
                case R.id.send : pushComment(); break;
                default : break;
            }
        }
    };

    private void getUserInfo() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("ID").getValue().toString().equals(userid)) {
                        name.setText(snapshot.child("ID").getValue().toString());
                        email.setText(snapshot.child("EMAIL").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getComment() {
        Log.d(this.getClass().getName() + "USERID", userid);
        items = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference("/comment/" + userid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(this.getClass().getName() + "YOURID", snapshot.child("YOURID").getValue().toString());
                    Log.d(this.getClass().getName() + "USERID", userid);
                    if(snapshot.child("YOURID").getValue().toString().equals(userid)) {
                        CommentItem commentItem = new CommentItem(snapshot.child("MYID").getValue().toString(),
                                snapshot.child("DATE").getValue().toString(),
                                snapshot.child("COMMENT").getValue().toString());
                        items.add(commentItem);
                    }
                }
                adapter = new CommentRecyclerAdapter(mypage_seller.this, items);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void pushComment() {
        String msg = inputMsg.getText().toString().trim();

        long current = System.currentTimeMillis();
        Date date = new Date(current);
        SimpleDateFormat SimpleCurrent = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String DateFormat = SimpleCurrent.format(date);

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String myID = sharedPreferences.getString("USERID", "empty");
        String yourId = userid;
        String Comment = msg;
        String Date = DateFormat;

        String key = commentReference.push().getKey();
        commentReference.child(userid).child(key).child("MYID").setValue(myID);
        commentReference.child(userid).child(key).child("YOURID").setValue(yourId);
        commentReference.child(userid).child(key).child("COMMENT").setValue(Comment);
        commentReference.child(userid).child(key).child("DATE").setValue(Date);

        inputMsg.setText("");
        getComment();
    }

    private InputMethodManager imm;
    private TextView name;
    private TextView email;
    private RecyclerView recyclerView;
    private Button picture, video, chat, send, search;
    private EditText inputMsg, searchid;
    private String userid;
    private ArrayList<CommentItem> items;
    private CommentRecyclerAdapter adapter;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("login");
    private DatabaseReference commentReference = firebaseDatabase.getReference("comment");
}
