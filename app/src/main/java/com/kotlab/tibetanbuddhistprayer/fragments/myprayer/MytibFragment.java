package com.kotlab.tibetanbuddhistprayer.fragments.myprayer;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class MytibFragment extends Fragment {

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
        getData();
    }

    private void getData() {
        myPrayerDatas.clear();
        for(int i=0;i<10;i++){

            MyPrayerData myPrayerData = new MyPrayerData("tashi","tghis us body",99,0);
            myPrayerDatas.add(myPrayerData);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tibrecycler = view.findViewById(R.id.mytibRecycler);
        mytibAdapter = new MyPrayerAdapter(myPrayerDatas,context );
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tibrecycler.setLayoutManager(layoutManager);
        tibrecycler.setAdapter(mytibAdapter);
        mytibAdapter.notifyDataSetChanged();
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
