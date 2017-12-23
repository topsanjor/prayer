package com.kotlab.tibetanbuddhistprayer.model;

/**
 * Created by Tsephel_Treps on 12/22/2017.
 */

public class MyPrayerData {

    private String title, body;
    private int id,type;

    public MyPrayerData(String title, String body, int id, int type) {
        this.title = title;
        this.body = body;
        this.id = id;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
