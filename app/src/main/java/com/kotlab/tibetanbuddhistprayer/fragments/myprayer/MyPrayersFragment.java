package com.kotlab.tibetanbuddhistprayer.fragments.myprayer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kotlab.tibetanbuddhistprayer.R;

public class MyPrayersFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private MytibFragment mytibFragment =null;
    private MyenFragment myenFragment;
    private TextView textViewTibetan,textViewEnglish;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_prayers, container, false);
        context = getActivity();
        textViewEnglish = view.findViewById(R.id.textViewEnglish);
        textViewTibetan = view.findViewById(R.id.textViewTibetan);
        textViewTibetan.setOnClickListener(this);
        textViewEnglish.setOnClickListener(this);
        setMytibFragment();
        return view;
    }

    private void replaceFragment(Fragment newFragment, String tag) {

//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        FragmentTransaction transaction =getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_myprayer, newFragment, tag);
        transaction.commit();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.textViewTibetan:
                textViewTibetan.setTextColor(ContextCompat.getColor(context,R.color.colorWhite));
                textViewEnglish.setTextColor(ContextCompat.getColor(context,R.color.colorBlack));
                textViewEnglish.setBackgroundResource(R.drawable.round_right);
                textViewTibetan.setBackgroundResource(R.drawable.round_left_selected);
                setMytibFragment();
                break;

            case R.id.textViewEnglish:
                textViewEnglish.setTextColor(ContextCompat.getColor(context,R.color.colorWhite));
                textViewTibetan.setTextColor(ContextCompat.getColor(context,R.color.colorBlack));
                textViewTibetan.setBackgroundResource(R.drawable.round_left);
                textViewEnglish.setBackgroundResource(R.drawable.round_right_selected);
                setMyenFragment();
                break;
            default:
                break;

        }

    }

    private void setMyenFragment(){

        if(myenFragment==null){
            myenFragment = new MyenFragment();
        }
        replaceFragment(myenFragment,myenFragment.getClass().getName());
    }

    private void setMytibFragment(){

        if(mytibFragment==null){
            mytibFragment = new MytibFragment();
        }
        replaceFragment(mytibFragment,mytibFragment.getClass().getName());
    }


}
