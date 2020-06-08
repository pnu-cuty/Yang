package com.example.fm24mhz.mypage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.example.fm24mhz.R;

public class VideoRecyclerViewHolder extends RecyclerView.ViewHolder{

    public VideoRecyclerViewHolder(View itemView) {
        super(itemView);
        this.youTubePlayerView = itemView.findViewById(R.id.player);
        this.youTubePlayerView.getSettings().setJavaScriptEnabled(true);
        this.youTubePlayerView.setWebChromeClient(new WebChromeClient(){

        });

        likeNum = itemView.findViewById(R.id.likeNum);
        likeBtn = itemView.findViewById(R.id.likeBtn);
    }

    public WebView youTubePlayerView;
    public TextView likeNum;
    public Button likeBtn;
}
