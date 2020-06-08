package com.example.fm24mhz.mypage;

public class VideoRecyclerItem {

    public VideoRecyclerItem(String url, int _like) {
        this.url = url;
        this.like = _like;
    }

    public String getUrl() {
        return url;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;
    private int like;
}
