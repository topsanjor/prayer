package com.kotlab.tibetanbuddhistprayer.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.activities.enPrayerDetailActivity;
import com.kotlab.tibetanbuddhistprayer.model.EnglishData;
import com.kotlab.tibetanbuddhistprayer.viewholder.EngViewHolder;

import java.util.ArrayList;

/**
 * Created by topjor on 11/28/2017.
 */

public class EnglishAdapter extends RecyclerView.Adapter<EngViewHolder> {
    private ArrayList<EnglishData> englishData;
    private Context context;



    public EnglishAdapter(ArrayList<EnglishData> englishData, Context context) {
        this.englishData =englishData;
        this.context = context;
    }

    @Override
    public EngViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.en_item_row,parent,false);
        return new EngViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EngViewHolder holder, int position) {

        final EnglishData enData = englishData.get(position);
        holder.entvtitle.setText(enData.getTitle());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(context,enPrayerDetailActivity.class);
                intent.putExtra("data",enData);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return englishData.size();
    }
}
