package com.example.a1208022380.audbucketlist;

import java.util.Date;

/**
 * Created by 1208022380 on 3/10/2018.
 */

public class MyItem {
    private String title = "";
    private String content = "";
    private Date date;
    private double latitude;
    private double longitude;
    private String PushKey;
    private boolean checked = false;



    public MyItem() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public String getPushKey() {
        return PushKey;
    }

    public void setPushKey(String pushKey) {
        PushKey = pushKey;
    }

    public String getContent() {
        return content;
    }
}
