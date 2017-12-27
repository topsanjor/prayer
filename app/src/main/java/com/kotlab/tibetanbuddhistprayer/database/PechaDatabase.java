package com.kotlab.tibetanbuddhistprayer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by topjor on 12/2/2017.
 */

public class PechaDatabase extends SQLiteOpenHelper {

    private static final int currentVersion = 1;

    // prayer Table

    public String CREATE_PRAYER_TABLE = "CREATE TABLE IF NOT EXISTS " + TableData.PrayerTable.TABLE_NAME + "("
            + TableData.PrayerTable.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TableData.PrayerTable.PRAYER_ID + " INTEGER,"
            + TableData.PrayerTable.COUNT + " INTEGER );";

    public String CREATE_MYPRAYER_TABLE = "CREATE TABLE IF NOT EXISTS " + TableData.MyPrayerTable.TABLE_NAME + "("
            + TableData.MyPrayerTable.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TableData.MyPrayerTable.COUNT + " INTEGER,"
            + TableData.MyPrayerTable.PRAYER_TITLE + " TEXT,"
            + TableData.MyPrayerTable.PRAYER_BODY + " TEXT,"
            + TableData.MyPrayerTable.PRAYER_ID + " INTEGER UNIQUE,"
            + TableData.MyPrayerTable.LANG_TYPE + " TEXT );";


    public PechaDatabase(Context context) {
        super(context, TableData.PrayerTable.DATABASE_NAME, null, currentVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_PRAYER_TABLE);
        db.execSQL(CREATE_MYPRAYER_TABLE);

        Log.d("SQlite", CREATE_MYPRAYER_TABLE);
        Log.d("SQlite", CREATE_PRAYER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableData.PrayerTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TableData.MyPrayerTable.TABLE_NAME);
    }

    // update prayer read count

    public long UpdatePrayeReadCount(PechaDatabase dop, int prayerid, int count) {

        try {

            SQLiteDatabase sq = dop.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TableData.PrayerTable.PRAYER_ID, prayerid);
            cv.put(TableData.PrayerTable.COUNT, count);
            long reslt = sq.update(TableData.PrayerTable.TABLE_NAME, cv, "" + TableData.PrayerTable.PRAYER_ID + "=?", new String[]{String.valueOf(prayerid)});
            return reslt;

        } catch (Exception ex) {

            ex.fillInStackTrace();
            return -1;
        }

    }

    public Cursor getTotalReadCount(PechaDatabase db, int prayerid) {
        try {

            SQLiteDatabase SQ = db.getReadableDatabase();
            String getcountQuery = "SELECT " + TableData.PrayerTable.COUNT + " FROM " + TableData.PrayerTable.TABLE_NAME + " WHERE " + TableData.PrayerTable.PRAYER_ID + "='" + prayerid + "'";
            Log.d("Result", getcountQuery);
            Cursor c = SQ.rawQuery(getcountQuery, null);
            Log.d("Cursor", String.valueOf(c));
            return c;
        } catch (Exception ex) {
            ex.fillInStackTrace();

            Log.d("Result", String.valueOf(ex));
            return null;
        }
    }

    public long AddPrayerData(PechaDatabase db, int prayer_id, int count) {

        try {

            SQLiteDatabase database = db.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TableData.PrayerTable.PRAYER_ID, prayer_id);
            cv.put(TableData.PrayerTable.COUNT, count);
            long result = database.insertWithOnConflict(TableData.PrayerTable.TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            return result;

        } catch (Exception ex) {
            return -1;
        }
    }


    public long AddMyPrayerData(PechaDatabase db, String title, String body, String langtype, int prayer_id, int count) {

        try {

            SQLiteDatabase database = db.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TableData.MyPrayerTable.PRAYER_TITLE, title);
            cv.put(TableData.MyPrayerTable.PRAYER_ID, prayer_id);
            cv.put(TableData.MyPrayerTable.PRAYER_BODY, body);
            cv.put(TableData.MyPrayerTable.LANG_TYPE, langtype);
            cv.put(TableData.MyPrayerTable.COUNT, count);

            long result = database.insertWithOnConflict(TableData.MyPrayerTable.TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            return result;

        } catch (Exception ex) {
            ex.fillInStackTrace();
            Log.d("Error", String.valueOf(ex));
            return -1;
        }
    }

    public Cursor getMyPrayerByType(PechaDatabase db, String langtype) {
        try {

            SQLiteDatabase SQ = db.getReadableDatabase();
            String getcountQuery = "SELECT * FROM " + TableData.MyPrayerTable.TABLE_NAME + " WHERE " + TableData.MyPrayerTable.LANG_TYPE + "='" + langtype + "'";

            Log.d("QueryPl",getcountQuery);
            Cursor c = SQ.rawQuery(getcountQuery, null);
            Log.d("getMyPrayerData", String.valueOf(c));
            return c;
        } catch (Exception ex) {
            ex.fillInStackTrace();

            Log.d("Result", String.valueOf(ex));
            return null;


        }
    }

    public Cursor getAllMyPrayerData(PechaDatabase db){

        try{

        SQLiteDatabase sq =db.getReadableDatabase();
        String Query ="SELECT * FROM " +TableData.MyPrayerTable.TABLE_NAME +"";
        Log.d("hub",Query);
        Cursor c=sq.rawQuery(Query,null);
        return c;


        }catch (Exception ex)
        {
            ex.fillInStackTrace();
            Log.d("GETDa",String.valueOf(ex));
            return null;
        }
    }

//    delete my prayer data

    public Boolean DeleteMyPrayerData(PechaDatabase dop,int prayer_id){

        SQLiteDatabase sq = dop.getWritableDatabase();

       int result= sq.delete(TableData.MyPrayerTable.TABLE_NAME, TableData.MyPrayerTable.PRAYER_ID + "=" +
                prayer_id, null);
        return  true;


    }
}
