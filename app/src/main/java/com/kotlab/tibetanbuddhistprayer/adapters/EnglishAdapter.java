package com.kotlab.tibetanbuddhistprayer.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.activities.enPrayerDetailActivity;
import com.kotlab.tibetanbuddhistprayer.database.PechaDatabase;
import com.kotlab.tibetanbuddhistprayer.database.TableData;
import com.kotlab.tibetanbuddhistprayer.model.EnglishData;
import java.util.ArrayList;

/**
 * Created by topjor on 11/28/2017.
 */

public class EnglishAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG ="EnglishAdapter" ;
    private ArrayList<EnglishData> englishData;
    private Context context;
    private static final int TYPE_HEADER=0;
    private static final int TYPE_ITEM=1;

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView entvtitle;
        private LinearLayout itemlayout,iconlayout;
        public ItemViewHolder(View itemView) {
            super(itemView);
            entvtitle = itemView.findViewById(R.id.txtEnTitle);
            itemlayout = itemView.findViewById(R.id.itemLinear);
            iconlayout = itemView.findViewById(R.id.linearicon);
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
            ((ItemViewHolder) holder).itemlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent  = new Intent(context,enPrayerDetailActivity.class);
                    intent.putExtra("enData",enData);
                    context.startActivity(intent);
                }
            });

            ((ItemViewHolder) holder).iconlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("Add to My Prayer")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int prayer_count = 0;
                                    PechaDatabase pechaDatabase = new PechaDatabase(context);
                                    Cursor cursor = pechaDatabase.getTotalReadCount(pechaDatabase, enData.getId());
                                    Log.d("Count Value", String.valueOf(cursor));
                                    try {
                                        if (cursor.moveToFirst()) {
                                            do {
                                                prayer_count = cursor.getInt(cursor.getColumnIndex(TableData.PrayerTable.COUNT));

                                            } while (cursor.moveToNext());
                                        }
                                        cursor.close();

                                    } catch (Exception ex) {
                                        ex.fillInStackTrace();
                                    }
                                    if(cursor.isClosed()==false)
                                    {
                                        cursor.close();
                                    }

                                    String langtype="english";
                                    String body =enData.getBody();
                                    Log.d(TAG,"body="+ body);
                                    int prayer_id=enData.getId();
                                    String title =enData.getTitle();
                                    Log.d(TAG,"Title="+title);
                                    Log.d(TAG,"prayer id="+prayer_id);
                                    Log.d(TAG,"count ="+prayer_count);
                                    Log.d(TAG,"LANG="+langtype);

                                    long value = pechaDatabase.AddMyPrayerData(pechaDatabase,title,body,langtype,prayer_id,prayer_count);
                                    Cursor curs = pechaDatabase.getAllMyPrayerData(pechaDatabase);
                                    String vaa = DatabaseUtils.dumpCursorToString(curs);
                                    Log.d(TAG,"Output="+vaa);
                                    if(value==-1){
                                        showMessage("Something wrong try again.");
                                    }else {
                                        showMessage("successfully entered");
                                    }

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    showMessage("Ok.... ");
                                }
                            }).show();
                }
            });
        }


    }

    private void showMessage(String s) {

        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
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
