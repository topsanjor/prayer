package com.kotlab.tibetanbuddhistprayer.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.adapters.EnglishAdapter;
import com.kotlab.tibetanbuddhistprayer.helper.Constansts;
import com.kotlab.tibetanbuddhistprayer.model.EnglishData;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;

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
//        addFakeData();

        parseXMLData();
        return view;

    }

    private void parseXMLData() {

        XmlParser xmlParser = new XmlParser();
        Document  document = xmlParser.getDomElement(String.valueOf(R.xml.enprayer));

        NodeList nodeList = document.getElementsByTagName(Constansts.KEY_ITEM);

        for(int i=0;i<nodeList.getLength();i++){

            //HashMap<String,String> map = new HashMap<String, String>();
            Element el = (Element) nodeList.item(i);
            String title = xmlParser.getValue(el, Constansts.KEY_TITLE);
            String body = xmlParser.getValue(el,Constansts.KEY_BODY);
            String idstring = xmlParser.getValue(el,Constansts.KEY_ID) ;
            int id = Integer.parseInt(idstring);

            EnglishData englishData = new EnglishData(title,body,id);
            englishDatas.add(englishData);
            enAdapter.notifyDataSetChanged();




        }
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
