package com.kotlab.tibetanbuddhistprayer.database;

import android.provider.BaseColumns;

/**
 * Created by topjor on 12/2/2017.
 */

public class TableData {

    public TableData()
    {

    }


    public static abstract class PrayerTable implements BaseColumns {

        public static final String DATABASE_NAME = "kotlab_Database";
        public static final String TABLE_NAME = "PrayerKot";
        public static final String ID = "ID";
        public static final String PRAYER_ID = "prayerID";
        public static final String COUNT="count";

    }







}
