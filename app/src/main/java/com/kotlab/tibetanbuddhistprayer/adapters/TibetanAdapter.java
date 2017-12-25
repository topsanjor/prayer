package com.kotlab.tibetanbuddhistprayer.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.activities.TibPrayerDetailActivity;
import com.kotlab.tibetanbuddhistprayer.database.PechaDatabase;
import com.kotlab.tibetanbuddhistprayer.database.TableData;
import com.kotlab.tibetanbuddhistprayer.model.TibData;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Handler;

/**
 * Created by topjor on 11/28/2017.
 */

public class TibetanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<TibData> tibData;
    private Context context;
    private static final int TYPE_HEAER = 0;
    private static final int TYPE_ITEM = 1;
    public static final String TAG="TibetanAdapter";

    public TibetanAdapter(ArrayList<TibData> tibData, Context context) {
        this.tibData = tibData;
        this.context = context;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView txttibtitle;
        private LinearLayout linearLayout, linearLayoutIcon;

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

        if (viewType == TYPE_HEAER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tib_item_title_layout, parent, false);
            return new HeaderViewHolder(view);

        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.tib_item_row, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final TibData tibDatas = tibData.get(position);

        if (holder instanceof HeaderViewHolder) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/nototibetanbold.ttf");
            ((HeaderViewHolder) holder).txtheadertitle.setTypeface(typeface);
            ((HeaderViewHolder) holder).txtheadertitle.setText(context.getResources().getString(R.string.tibIndex));

        } else if (holder instanceof ItemViewHolder) {

            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/nototibetanregular.ttf");

            ((ItemViewHolder) holder).txttibtitle.setText(tibDatas.getTibtitle());
            ((ItemViewHolder) holder).txttibtitle.setTypeface(typeface);

            ((ItemViewHolder) holder).linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, TibPrayerDetailActivity.class);
                    intent.putExtra("type","tibetan");
                    intent.putExtra("tibData", tibDatas);
                    context.startActivity(intent);
                }
            });

            ((ItemViewHolder) holder).linearLayoutIcon.setOnClickListener(new View.OnClickListener() {
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
                                    Cursor cursor = pechaDatabase.getTotalReadCount(pechaDatabase, tibDatas.getTibId());
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





                                    String langtype="tibetan";

                                    String body =tibDatas.getTibbody();
                                    Log.d(TAG,"body="+ body);
                                    int prayer_id=tibDatas.getTibId();
                                    String title =tibDatas.getTibtitle();
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

        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return tibData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {

            return TYPE_HEAER;
        } else {

            return TYPE_ITEM;

        }
    }

    private boolean isPositionHeader(int position) {

        return position == 0;
    }
}
