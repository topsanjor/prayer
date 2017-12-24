package com.kotlab.tibetanbuddhistprayer.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.activities.TibPrayerDetailActivity;
import com.kotlab.tibetanbuddhistprayer.model.TibData;
import java.util.ArrayList;

/**
 * Created by topjor on 11/28/2017.
 */

public class TibetanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<TibData> tibData;
    private Context context;
    private static final int TYPE_HEAER=0;
    private static final int TYPE_ITEM=1;

    public TibetanAdapter(ArrayList<TibData> tibData, Context context) {
        this.tibData = tibData;
        this.context = context;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView txttibtitle ;
        private LinearLayout linearLayout,linearLayoutIcon;
        public ItemViewHolder(View itemView) {
            super(itemView);
            txttibtitle = itemView.findViewById(R.id.txttibtitle);
            linearLayout = itemView.findViewById(R.id.itemLinear);
            linearLayoutIcon = itemView.findViewById(R.id.linearicon);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView txtheadertitle;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            txtheadertitle = itemView.findViewById(R.id.tibHeaderTxt);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType ==TYPE_HEAER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tib_item_title_layout,parent,false);
            return new HeaderViewHolder(view);

        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.tib_item_row,parent,false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final TibData tibDatas = tibData.get(position);


        if(holder instanceof HeaderViewHolder){
            Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/nototibetanbold.ttf");
            ((HeaderViewHolder) holder).txtheadertitle.setTypeface(typeface);
             ((HeaderViewHolder) holder).txtheadertitle.setText(context.getResources().getString(R.string.tibIndex));

        }else if(holder instanceof ItemViewHolder){

            Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/nototibetanregular.ttf");

            ((ItemViewHolder) holder).txttibtitle.setText(tibDatas.getTibtitle());
              ((ItemViewHolder) holder).txttibtitle.setTypeface(typeface);

            ((ItemViewHolder) holder).linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, TibPrayerDetailActivity.class);
                    intent.putExtra("tibData",tibDatas);
                    context.startActivity(intent);
                }
            });

            ((ItemViewHolder) holder).linearLayoutIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("Add to My Prayer")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Toast.makeText(context,"hello thanks",Toast.LENGTH_SHORT).show();

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            }).show();

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return tibData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position)){

            return TYPE_HEAER;
        }else {

            return TYPE_ITEM;

        }
    }

    private boolean isPositionHeader(int position) {

        return position==0;
    }
}
