package com.kotlab.tibetanbuddhistprayer.fragments.myprayer;


import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.adapters.MyPrayerAdapter;
import com.kotlab.tibetanbuddhistprayer.database.PechaDatabase;
import com.kotlab.tibetanbuddhistprayer.database.TableData;
import com.kotlab.tibetanbuddhistprayer.model.MyPrayerData;

import java.util.ArrayList;

public class MyenFragment extends Fragment {

    private static final String TAG ="enFragment";
    private RecyclerView enRecycler;
    private MyPrayerAdapter enAdapter ;
    private ArrayList<MyPrayerData> myPrayerDatas = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    View view;
    Context context;


    public MyenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        view = inflater.inflate(R.layout.fragment_myen, container, false);
        enRecycler  = view.findViewById(R.id.myenRecycler);
        enAdapter = new MyPrayerAdapter(myPrayerDatas,context,"english");
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        enRecycler.setLayoutManager(linearLayoutManager);
        enRecycler.setAdapter(enAdapter);
      //  getData();
        getDB();
        return  view;

    }

    private void getData() {
        try {
            myPrayerDatas.clear();
        } catch (Exception ex) {

            ex.fillInStackTrace();
        }

        for (int i = 0; i < 10; i++) {

            MyPrayerData myPrayerData = new MyPrayerData("the english","the body of en",2,"english");
              myPrayerDatas.add(myPrayerData);

        }

        enAdapter.notifyDataSetChanged();
    }

    private void getDB() {
        try{
            myPrayerDatas.clear();
        }catch (Exception ex) {

        }
        PechaDatabase pechaDatabase = new PechaDatabase(context);
        Cursor cursor = pechaDatabase.getMyPrayerByType(pechaDatabase,"english");

        Log.d(TAG, DatabaseUtils.dumpCursorToString(cursor));
        if(cursor.moveToFirst()){
            do {

                String title = cursor.getString(cursor.getColumnIndex(TableData.MyPrayerTable.PRAYER_TITLE));
                String body = cursor.getString(cursor.getColumnIndex(TableData.MyPrayerTable.PRAYER_BODY));
                int prayer_id = cursor.getInt(cursor.getColumnIndex(TableData.MyPrayerTable.PRAYER_ID));
                String langtype = cursor.getString(cursor.getColumnIndex(TableData.MyPrayerTable.LANG_TYPE));


                MyPrayerData myPrayerData = new MyPrayerData(title,body,prayer_id,langtype);
                myPrayerDatas.add(myPrayerData);

                Log.d(TAG,title);
                Log.d(TAG,body);
                Log.d(TAG, String.valueOf(prayer_id));

            }while (cursor.moveToNext());
        }
        cursor.close();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
