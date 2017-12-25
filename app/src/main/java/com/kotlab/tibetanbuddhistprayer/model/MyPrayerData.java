package com.kotlab.tibetanbuddhistprayer.model;

import java.io.Serializable;

/**
 * Created by Tsephel_Treps on 12/22/2017.
 */

public class MyPrayerData implements Serializable {

    private String title, body,langtype;
    private int id;

    public MyPrayerData(String title, String body, int id, String langtype) {
        this.title = title;
        this.body = body;
        this.id = id;
        this.langtype=langtype;
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

    public String getType() {
        return langtype;
    }

    public void setType(String type) {
        this.langtype = type;
    }
}
