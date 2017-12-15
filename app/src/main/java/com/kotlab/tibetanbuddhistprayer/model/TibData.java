package com.kotlab.tibetanbuddhistprayer.model;

import java.io.Serializable;

/**
 * Created by topjor on 11/28/2017.
 */

public class TibData implements Serializable {

    private String tibtitle,tibbody;
    private int  tibId;


    public TibData(String tibtitle, String tibbody, int tibId) {
        this.tibtitle = tibtitle;
        this.tibbody = tibbody;
        this.tibId = tibId;
    }

    public String getTibtitle() {
        return tibtitle;
    }

    public void setTibtitle(String tibtitle) {
        this.tibtitle = tibtitle;
    }

    public String getTibbody() {
        return tibbody;
    }

    public void setTibbody(String tibbody) {
        this.tibbody = tibbody;
    }

    public int getTibId() {
        return tibId;
    }

    public void setTibId(int tibId) {
        this.tibId = tibId;
    }


}
