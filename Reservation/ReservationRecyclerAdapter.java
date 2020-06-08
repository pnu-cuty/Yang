package com.example.fm24mhz.Reservation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fm24mhz.R;

import java.util.ArrayList;

public class ReservationRecyclerAdapter extends RecyclerView.Adapter<ReservationRecyclerViewHolder> {
    public ReservationRecyclerAdapter(Context context, ArrayList<ReservationItem> mItems) {
        this.context = context;
        this.mItems = mItems;
    }

    @Override
    public ReservationRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new ReservationRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReservationRecyclerViewHolder holder, int position) {
        holder.name.setText(mItems.get(position).getUserid());
        holder.location.setText(mItems.get(position).getLocation());
        holder.starthour.setText(mItems.get(position).getStarthour());
        holder.startminute.setText(mItems.get(position).getStartminute());
        holder.endhour.setText(mItems.get(position).getEndhour());
        holder.endminute.setText(mItems.get(position).getEndminute());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    Context context;
    ArrayList<ReservationItem> mItems;
}
