package com.example.fm24mhz.mypage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fm24mhz.R;

public class CommentRecyclerViewHolder extends RecyclerView.ViewHolder{
    public CommentRecyclerViewHolder(View v) {
        super(v);
        this.name = v.findViewById(R.id.userid);
        this.time = v.findViewById(R.id.time);
        this.comment = v.findViewById(R.id.comment);
        this.linearLayout = v.findViewById(R.id.linearlayout);
    }
    public TextView name;
    public TextView time;
    public TextView comment;
    public LinearLayout linearLayout;
}
