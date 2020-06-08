package com.example.fm24mhz.Reservation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.fm24mhz.R;

public class ReservationRecyclerViewHolder extends RecyclerView.ViewHolder{
    public ReservationRecyclerViewHolder(View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.userid1);
        this.location = itemView.findViewById(R.id.location);
        this.starthour = itemView.findViewById(R.id.starthour);
        this.startminute = itemView.findViewById(R.id.startminute);
        this.endhour = itemView.findViewById(R.id.endhour);
        this.endminute = itemView.findViewById(R.id.endminute);
    }

    public TextView name;
    public TextView location;
    public TextView starthour;
    public TextView startminute;
    public TextView endhour;
    public TextView endminute;
}
