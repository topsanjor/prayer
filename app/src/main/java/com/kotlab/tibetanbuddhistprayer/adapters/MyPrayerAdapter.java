package com.kotlab.tibetanbuddhistprayer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.model.MyPrayerData;

import java.util.ArrayList;

/**
 * Created by Tsephel_Treps on 12/22/2017.
 */

public class MyPrayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MyPrayerData> myPrayerData;
    private Context context;
    private static final int TYPE_HEADER =0 ;
    private static final int TYPE_ITEM =1;

    public MyPrayerAdapter(ArrayList<MyPrayerData> myPrayerData , Context context)
    {

        this.context = context;
        this.myPrayerData = myPrayerData;
    }

    private static class HeaderViewHolder  extends RecyclerView.ViewHolder{
        private TextView myprayerTitle;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            myprayerTitle = itemView.findViewById(R.id.myPrayerHeaderTxt);
        }
    }


    private static class  ItemViewHolder extends RecyclerView.ViewHolder {
         private TextView titletextView;
        public ItemViewHolder(View itemView){
            super(itemView);
            titletextView = itemView.findViewById(R.id.txtmyprayertitle);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER){

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myprayer_header_layout,parent,false);

            return new HeaderViewHolder(view);
        }else{

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myprayer_item_layout,parent,false);
            return new ItemViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof HeaderViewHolder){

            ((HeaderViewHolder) holder).myprayerTitle.setText(context.getResources().getString(R.string.myprayertitle));

        }else if(holder instanceof ItemViewHolder){

            MyPrayerData mydata = myPrayerData.get(position);
            ((ItemViewHolder) holder).titletextView.setText(mydata.getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "thanks for toasting", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    public int getItemViewType(int position) {

        if(isPositionHeader(position)){
            return TYPE_HEADER;
        }else {

            return TYPE_ITEM;
        }
    }

    private boolean isPositionHeader(int position) {

        return position==0;
    }

    @Override
    public int getItemCount() {
        return myPrayerData.size();
    }
}
