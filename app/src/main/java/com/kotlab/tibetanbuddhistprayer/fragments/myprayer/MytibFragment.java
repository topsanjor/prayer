package com.kotlab.tibetanbuddhistprayer.fragments.myprayer;


import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.kotlab.tibetanbuddhistprayer.helper.Utils;
import com.kotlab.tibetanbuddhistprayer.model.MyPrayerData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MytibFragment extends Fragment {

    private static final String TAG ="TibetanFragment" ;
    private ArrayList<MyPrayerData> myPrayerDatas = new ArrayList<>();
    private RecyclerView tibrecycler;
    private LinearLayoutManager layoutManager;
    private MyPrayerAdapter mytibAdapter;
    private View view;
    private Context context;


    public MytibFragment() {


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void getData() {
        myPrayerDatas.clear();
        for(int i=0;i<10;i++){

            MyPrayerData myPrayerData = new MyPrayerData("tashi","tghis us body",99,"tibetan");
            myPrayerDatas.add(myPrayerData);
        }
        mytibAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tibrecycler = view.findViewById(R.id.mytibRecycler);
        mytibAdapter = new MyPrayerAdapter(myPrayerDatas,context,"tibetan");
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tibrecycler.setLayoutManager(layoutManager);
        tibrecycler.setAdapter(mytibAdapter);
        //getData();
        getDB();



    }

    private void getDB() {
        try{
            myPrayerDatas.clear();
        }catch (Exception ex) {

        }


        PechaDatabase pechaDatabase = new PechaDatabase(context);

        Cursor cursor = pechaDatabase.getMyPrayerByType(pechaDatabase,"tibetan");

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
    public void onResume() {
        super.onResume();
//        getDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mytib, container, false);
        context = getContext();

        return view;
    }

}
