package com.kotlab.tibetanbuddhistprayer.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.adapters.TibetanAdapter;
import com.kotlab.tibetanbuddhistprayer.helper.Constansts;
import com.kotlab.tibetanbuddhistprayer.model.TibData;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TibetanFragment extends Fragment {

    private RecyclerView tibrecycler;
    private RecyclerView.LayoutManager layoutManager;
    private TibetanAdapter tibetanAdapter;
    private ArrayList<TibData> tibDatas = new ArrayList<>();
    private Context context;
    private Typeface typeface;

    public TibetanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view= inflater.inflate(R.layout.fragment_tibetan, container, false);
        context=getContext();
        typeface =Typeface.createFromAsset(context.getAssets(), "fonts/nototibetanbold.ttf");

        initView(view);
        parseXMLData();
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        TextView svText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        svText.setTypeface(typeface);
        searchView.setQueryHint(context.getResources().getString(R.string.tibqueryhint));
        search(searchView);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tibetanAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }


    private void initView(View view) {

        tibrecycler = view.findViewById(R.id.recyclertib);
        tibetanAdapter = new TibetanAdapter(tibDatas,context);
        layoutManager = new LinearLayoutManager(context);
        tibrecycler.setLayoutManager(layoutManager);
        tibrecycler.setAdapter(tibetanAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        parseXMLData();
    }

    public String readXML() {

        String line;
        StringBuilder total = new StringBuilder();

        try {
            InputStream is = getActivity().getAssets().open("tibprayer.xml");

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
        try{
            tibDatas.clear();
        }catch (Exception ex) {

            ex.fillInStackTrace();
        }

        XmlParser xmlParser = new XmlParser();
        Document document = xmlParser.getDomElement(readXML());
        try{
            NodeList nodeList = document.getElementsByTagName(Constansts.KEY_ITEM);
          TibData tibData= new TibData(context.getResources().getString(R.string.tibIndex),"vala",0);
          tibDatas.add(tibData);

            for(int i=0;i<nodeList.getLength();i++){

                Element el = (Element) nodeList.item(i);
                String title = xmlParser.getValue(el, Constansts.KEY_TITLE);
                String body = xmlParser.getValue(el,Constansts.KEY_BODY);
                String idstring = xmlParser.getValue(el,Constansts.KEY_ID) ;
                int id = Integer.parseInt(idstring);
                Log.d("Name", title);
                TibData  tibdata = new TibData(title,body,id);
                tibDatas.add(tibdata);
                tibetanAdapter.notifyDataSetChanged();
            }

        }catch (Exception ex){
            ex.fillInStackTrace();
        }
    }

}
