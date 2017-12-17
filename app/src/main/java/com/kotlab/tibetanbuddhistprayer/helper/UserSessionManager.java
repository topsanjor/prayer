package com.kotlab.tibetanbuddhistprayer.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Tsephel_Treps on 12/5/2017.
 */

public class UserSessionManager  {

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    private Context context;
    private int PRIVATE_MODE =0 ;
    private static final String PREFER_NAME = "Kotlab_Prayer";
    private static final String EN_DETAIL_TEXT_SIZE ="endetailtextsize";
    private static final String TIB_DETAIL_TEXT_SIZE ="tibdetailtextsize";
    private float envalue,tibvalue;
    public UserSessionManager(Context context){

        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFER_NAME,PRIVATE_MODE);
        editor =sharedPreferences.edit();

    }

    public float getEnvalue() {
        return sharedPreferences.getFloat(EN_DETAIL_TEXT_SIZE,16);
    }

    public void setEnvalue(float envalue) {
        editor.putFloat(EN_DETAIL_TEXT_SIZE,envalue);
        editor.commit();
    }

    public float getTibvalue() {
        return sharedPreferences.getFloat(TIB_DETAIL_TEXT_SIZE,20);
    }

    public void setTibvalue(float tibvalue) {
        editor.putFloat(TIB_DETAIL_TEXT_SIZE,tibvalue);
        editor.commit();
    }
}
