package com.kotlab.tibetanbuddhistprayer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.kotlab.tibetanbuddhistprayer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TibetanFragment extends Fragment {


    public TibetanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_tibetan, container, false);
        return view;
    }

}
