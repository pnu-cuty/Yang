package com.example.fm24mhz.Reservation;

public class ReservationItem {
    public ReservationItem(String userid, String location,
                           String starthour, String startminute,
                           String endhour, String endminute) {
        this.userid = userid;
        this.location = location;
        this.starthour = starthour;
        this.startminute = startminute;
        this.endhour = endhour;
        this.endminute = endminute;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStarthour() {
        return starthour;
    }

    public void setStarthour(String starthour) {
        this.starthour = starthour;
    }

    public String getStartminute() {
        return startminute;
    }

    public void setStartminute(String startminute) {
        this.starthour = startminute;
    }

    public String getEndhour() {
        return endhour;
    }

    public void setEndhour(String endhour) {
        this.endhour = endhour;
    }

    public String getEndminute() {
        return endminute;
    }

    public void setEndminute(String endminute) {
        this.endminute = endminute;
    }

    private String userid;
    private String location;
    private String starthour;
    private String startminute;
    private String endhour;
    private String endminute;
}
