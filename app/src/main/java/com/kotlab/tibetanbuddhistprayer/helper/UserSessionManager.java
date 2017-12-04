package com.kotlab.tibetanbuddhistprayer.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Tsephel_Treps on 12/5/2017.
 */

public class UserSessionManager  {

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    Context context;
    int PRIVATE_MODE =0 ;
    private static final String PREFER_NAME = "Kotlab_Prayer";
    private static final String IS_FIRST_BUTTON_CLICK = "IsFirstTimeCounBtnClick";

    public UserSessionManager(Context context){

        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFER_NAME,PRIVATE_MODE);
        editor =sharedPreferences.edit();

    }



}
