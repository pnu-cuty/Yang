package com.example.fm24mhz.mypage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fm24mhz.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VideoActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myvideo);
        btn = findViewById(R.id.addition);
        likeBtn = findViewById(R.id.likeBtn);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        Intent prev = getIntent();
        userid = prev.getStringExtra("ID");

        getVideos();
    }

    public void getVideos() {
        items = new ArrayList<>();
        DatabaseReference videoReference = firebaseDatabase.getReference("/video/" + userid);
        videoReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(this.getClass().getName(), snapshot.child("URL").getValue().toString());
                    VideoRecyclerItem videoRecyclerItem = new VideoRecyclerItem(
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"
                            + snapshot.child("URL").getValue().toString() +
                            "\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>"
                            , Integer.parseInt(snapshot.child("LIKE").getValue().toString())
                    );
                    items.add(videoRecyclerItem);
                }
                adapter = new VideoRecyclerAdapter(VideoActivity.this, items, userid);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void add(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("동영상 추가");
        builder.setMessage("추가할 동영상 소스를 입력하세요");

        final EditText editText = new EditText(this);

        builder.setView(editText);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String source = editText.getText().toString().trim();
                if(TextUtils.isEmpty(source)) {
                    Toast.makeText(VideoActivity.this, "URL을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    putsource(source);
                }
            }
        });

        builder.show();
    }

    public void putsource(String source){
        String key = databaseReference.push().getKey();
        databaseReference.child(userid).child(key).child("URL").setValue(source);
        databaseReference.child(userid).child(key).child("LIKE").setValue(Integer.toString(0));
        getVideos();
    }

    ArrayList<VideoRecyclerItem> items;
    RecyclerView recyclerView;
    VideoRecyclerAdapter adapter;
    Button btn, likeBtn;
    String userid;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("video");

}
