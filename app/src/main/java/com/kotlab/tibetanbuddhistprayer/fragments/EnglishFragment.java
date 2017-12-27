package com.kotlab.tibetanbuddhistprayer.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class EnglishFragment extends Fragment {
    private RecyclerView recycler;
    private Context context;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<EnglishData> englishDatas = new ArrayList<>();
    private EnglishAdapter enAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_english, container, false);
        // Inflate the layout for this fragment
        context = getContext();
        init(view);
        parseXMLData();
        return view;

    }


    public String readXML() {
        String line;
        StringBuilder total = new StringBuilder();
        try {
            InputStream is = getActivity().getAssets().open("enprayer.xml");
            BufferedReader r = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            total = new StringBuilder();

            while ((line = r.readLine()) != null) {
                total.append(line + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return total.toString();
    }

    private void parseXMLData() {
        englishDatas.clear();

        XmlParser xmlParser = new XmlParser();

        Log.d("StringBla", readXML());

        Document document = xmlParser.getDomElement(readXML());

        try {
            NodeList nodeList = document.getElementsByTagName(Constansts.KEY_ITEM);
            EnglishData englishDat = new EnglishData("blabla", "bbod", 0);
            englishDatas.add(englishDat);
            for (int i = 0; i < nodeList.getLength(); i++) {
                //HashMap<String,String> map = new HashMap<String, String>();
                Element el = (Element) nodeList.item(i);
                String title = xmlParser.getValue(el, Constansts.KEY_TITLE);
                String body = xmlParser.getValue(el, Constansts.KEY_BODY);
                String idstring = xmlParser.getValue(el, Constansts.KEY_ID);
                int id = Integer.parseInt(idstring);
                EnglishData englishData = new EnglishData(title, body, id);
                englishDatas.add(englishData);
                enAdapter.notifyDataSetChanged();

            }


        } catch (Exception ex) {

            ex.fillInStackTrace();


        }
    }


    private void init(View view) {

        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        enAdapter = new EnglishAdapter(englishDatas, context);
        linearLayoutManager = new LinearLayoutManager(context);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(enAdapter);
    }

}
