package com.example.a1208022380.audbucketlist;

import java.util.Date;

/**
 * Created by 1208022380 on 3/10/2018.
 */

public class MyItem {
    private String title = "";
    private String content = "";
    private boolean checked = false;


    public MyItem(String title, String content, boolean checked) {
        this.title = title;
        this.content = content;
        this.checked = checked;
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

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
