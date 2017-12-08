package com.kotlab.tibetanbuddhistprayer.fragments;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.adapters.EnglishAdapter;
import com.kotlab.tibetanbuddhistprayer.helper.Constansts;
import com.kotlab.tibetanbuddhistprayer.model.EnglishData;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;

public class EnglishFragment extends Fragment {
    private RecyclerView recycler;
    private Context context;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<EnglishData> englishDatas= new ArrayList<>();
    private EnglishAdapter enAdapter;

    String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<menu>\n" +
            "    <item>\n" +
            "        <id>1</id>    \n" +
            "        <name>Margherita</name>\n" +
            "        <cost>155</cost>\n" +
            "        <description>Single cheese topping Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.</description>\n" +
            "    </item> \n" +
            "    <item>\n" +
            "        <id>2</id>    \n" +
            "        <name>Double Cheese Margherita</name>\n" +
            "        <cost>225</cost>\n" +
            "        <description>Loaded with Extra Cheese</description>\n" +
            "    </item> \n" +
            "    <item>\n" +
            "        <id>3</id>    \n" +
            "        <name>Fresh Veggie</name>\n" +
            "        <cost>110</cost>\n" +
            "        <description>Oninon and Crisp capsicum</description>\n" +
            "    </item> \n" +
            "    <item>\n" +
            "        <id>4</id>    \n" +
            "        <name>Peppy Paneer</name>\n" +
            "        <cost>155</cost>\n" +
            "        <description>Paneer, Crisp capsicum and Red pepper</description>\n" +
            "    </item> \n" +
            "    <item>\n" +
            "        <id>5</id>    \n" +
            "        <name>Mexican Green Wave</name>\n" +
            "        <cost>445</cost>\n" +
            "        <description>Onion, Crip capsicum, Tomato with mexican herb</description>\n" +
            "    </item> \n" +
            "</menu> ";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view =inflater.inflate(R.layout.fragment_english, container, false);
        // Inflate the layout for this fragment
        context = getContext();
        init(view);
      //addFakeData();
     parseXMLData();

        return view;

    }

    private void parseXMLData() {

        XmlParser xmlParser = new XmlParser();
        Document  document = xmlParser.getDomElement(xml);

        Log.d("TashiDoc",document.toString());

        try{
            NodeList nodeList = document.getElementsByTagName(Constansts.KEY_ITEM);

            Log.d("TashiDelek",nodeList.toString());
            for(int i=0;i<nodeList.getLength();i++){

                //HashMap<String,String> map = new HashMap<String, String>();
                Element el = (Element) nodeList.item(i);
                String title = xmlParser.getValue(el, Constansts.KEY_TITLE);
                String body = xmlParser.getValue(el,Constansts.KEY_BODY);
                String idstring = xmlParser.getValue(el,Constansts.KEY_ID) ;

                int id = Integer.parseInt(idstring);

                Log.d("Result",title+ "Body"+body);

                EnglishData englishData = new EnglishData(title,body,id);
                englishDatas.add(englishData);
                enAdapter.notifyDataSetChanged();

            }


        }catch (Exception ex){

            ex.fillInStackTrace();

            Log.d("Tashi",ex.getMessage());

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
