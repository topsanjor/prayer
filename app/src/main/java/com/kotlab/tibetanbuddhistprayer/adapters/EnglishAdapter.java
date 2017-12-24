package com.kotlab.tibetanbuddhistprayer.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.activities.enPrayerDetailActivity;
import com.kotlab.tibetanbuddhistprayer.model.EnglishData;
import java.util.ArrayList;

/**
 * Created by topjor on 11/28/2017.
 */

public class EnglishAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<EnglishData> englishData;
    private Context context;
    private static final int TYPE_HEADER=0;
    private static final int TYPE_ITEM=1;

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView entvtitle;
        public ItemViewHolder(View itemView) {
            super(itemView);
            entvtitle = itemView.findViewById(R.id.txtEnTitle);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView headertitletv;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            headertitletv = itemView.findViewById(R.id.engHeaderTxt);

        }
    }

    public EnglishAdapter(ArrayList<EnglishData> englishData, Context context) {
        this.englishData =englishData;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==TYPE_HEADER){
            View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.en_item_title_layout,parent,false);

            return new HeaderViewHolder(view);

        }else {

            View view = LayoutInflater.from(context).inflate(R.layout.en_item_row,parent,false);
            return new ItemViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder){

            ((HeaderViewHolder) holder).headertitletv.setText("INDEX");

        }else if(holder instanceof ItemViewHolder){


            final EnglishData enData = englishData.get(position);

            ((ItemViewHolder) holder).entvtitle.setText(enData.getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent  = new Intent(context,enPrayerDetailActivity.class);
                    intent.putExtra("enData",enData);
                    context.startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return englishData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
        {

            return TYPE_HEADER;
        }else {
            return TYPE_ITEM;
        }
    }

    private boolean isPositionHeader(int position) {

        return position==0;
    }
}
