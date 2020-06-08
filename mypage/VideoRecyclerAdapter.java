package com.example.fm24mhz.mypage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fm24mhz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class VideoRecyclerAdapter extends RecyclerView.Adapter<VideoRecyclerViewHolder> {

    public VideoRecyclerAdapter(Context context, ArrayList<VideoRecyclerItem> items, String _userid) {
        this.context = context;
        this.items = items;
        this.userid = _userid;
    }

    @Override
    public VideoRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video, parent, false);
        return new VideoRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VideoRecyclerViewHolder holder, final int position) {
        /*  initialize the thumbnail image view , we need to pass Developer Key */
        holder.youTubePlayerView.loadData(items.get(position).getUrl(), "text/html" , "utf-8");
        holder.likeNum.setText(Integer.toString(items.get(position).getLike()));

        final VideoRecyclerViewHolder h = holder;

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendLike(h, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void sendLike(final VideoRecyclerViewHolder viewHolder, final int position) {

        like = items.get(position).getLike();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                while(child.hasNext() && (index != position - 1)) {
                    index++;
                    child.next();
                }

//                key = child.next().getKey();
//                Log.d(this.getClass().getName()+"Like Test", key);
//                DatabaseReference ModifyReference = firebaseDatabase.getReference("/video/" + userid + "/" + key);
//                ModifyReference.child("LIKE").setValue(Integer.toString(++like));
                viewHolder.likeNum.setText(Integer.toString(++like));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }

    private Context context;
    private ArrayList<VideoRecyclerItem> items;
    private String key = "";
    private String userid;
    private int index = 0;
    private int like = 0;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("/video/" + userid);
}
