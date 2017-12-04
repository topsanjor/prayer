package com.kotlab.tibetanbuddhistprayer.model;

import java.io.Serializable;

/**
 * Created by topjor on 11/28/2017.
 */

public class EnglishData implements Serializable {

    private String title,body;
    private int id;

    public EnglishData(String title, String body, int id) {
        this.title = title;
        this.body = body;
        this.id = id;
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
}
