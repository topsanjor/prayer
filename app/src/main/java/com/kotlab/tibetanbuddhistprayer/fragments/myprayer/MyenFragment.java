package com.kotlab.tibetanbuddhistprayer.fragments.myprayer;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.adapters.MyPrayerAdapter;
import com.kotlab.tibetanbuddhistprayer.model.MyPrayerData;

import java.util.ArrayList;

public class MyenFragment extends Fragment {

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
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_myen, container, false);
        enRecycler  = view.findViewById(R.id.myenRecycler);
        enAdapter = new MyPrayerAdapter(myPrayerDatas,context);
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        enRecycler.setLayoutManager(linearLayoutManager);
        enRecycler.setAdapter(enAdapter);
        getData();
        return  view;

    }

    private void getData() {
        try {
            myPrayerDatas.clear();
        } catch (Exception ex) {

            ex.fillInStackTrace();
        }

        for (int i = 0; i < 10; i++) {

            MyPrayerData myPrayerData = new MyPrayerData("the english","the body of en",2,0);
              myPrayerDatas.add(myPrayerData);

        }

        enAdapter.notifyDataSetChanged();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
