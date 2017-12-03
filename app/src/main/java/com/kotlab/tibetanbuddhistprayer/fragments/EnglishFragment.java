package com.kotlab.tibetanbuddhistprayer.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.adapters.EnglishAdapter;
import com.kotlab.tibetanbuddhistprayer.model.EnglishData;

import java.util.ArrayList;

public class EnglishFragment extends Fragment {
    private RecyclerView recycler;
    private Context context;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<EnglishData> englishDatas= new ArrayList<>();
    private EnglishAdapter enAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view =inflater.inflate(R.layout.fragment_english, container, false);
        // Inflate the layout for this fragment
        context = getContext();
        init(view);
        addFakeData();
        return view;

    }

    private void addFakeData() {

        EnglishData en = new EnglishData("First","Hello this is a body",3);
        englishDatas.add(en);
        en = new EnglishData("Second","Second Body",2);
        englishDatas.add(en);
        enAdapter.notifyDataSetChanged();
    }

    private void init(View view) {

        recycler =(RecyclerView) view.findViewById(R.id.recycler);
        enAdapter = new EnglishAdapter(englishDatas,context);
        linearLayoutManager = new LinearLayoutManager(context);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(enAdapter);


    }

}
