package com.kotlab.tibetanbuddhistprayer.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kotlab.tibetanbuddhistprayer.R;

/**
 * Created by topjor on 11/28/2017.
 */

public class TibViewHolder extends RecyclerView.ViewHolder {

    public TextView tibtitle;
    public View view;

    public TibViewHolder(View itemView) {
        super(itemView);
        tibtitle = itemView.findViewById(R.id.txttibtitle);
        view = itemView;

    }
}
