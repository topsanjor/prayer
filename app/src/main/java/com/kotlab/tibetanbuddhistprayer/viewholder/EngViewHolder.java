package com.kotlab.tibetanbuddhistprayer.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kotlab.tibetanbuddhistprayer.R;

import org.w3c.dom.Text;

/**
 * Created by topjor on 11/28/2017.
 */

public class EngViewHolder extends RecyclerView.ViewHolder {
    public TextView entvtitle;
    public View view;

    public EngViewHolder(View itemView) {
        super(itemView);
        view =itemView;
        entvtitle =(TextView) itemView.findViewById(R.id.txtEnTitle);

    }
}
