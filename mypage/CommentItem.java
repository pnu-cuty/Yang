package com.example.fm24mhz.mypage;

public class CommentItem {

    public CommentItem(String userid, String time, String comment) {
        this.userid = userid;
        this.time = time;
        this.comment = comment;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String userid;
    private String time;
    private String comment;
}
