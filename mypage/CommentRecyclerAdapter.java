package com.example.fm24mhz.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fm24mhz.R;

import java.util.ArrayList;

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerViewHolder>{
    public CommentRecyclerAdapter(Context _context, ArrayList<CommentItem> _items) {
        this.context = _context;
        this.items = _items;
    }

    public CommentRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);

        return new CommentRecyclerViewHolder(v);
    }

    public void onBindViewHolder(CommentRecyclerViewHolder viewHolder, int position) {
        viewHolder.name.setText(items.get(position).getUserid());
        viewHolder.comment.setText(items.get(position).getComment());
        viewHolder.time.setText(items.get(position).getTime());

        final int pos = position;
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, mypage_seller.class);
                intent.putExtra("ID", items.get(pos).getUserid());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    Context context;
    ArrayList<CommentItem> items;
}
