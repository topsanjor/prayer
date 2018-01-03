package com.kotlab.tibetanbuddhistprayer.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.activities.TibPrayerDetailActivity;
import com.kotlab.tibetanbuddhistprayer.activities.enPrayerDetailActivity;
import com.kotlab.tibetanbuddhistprayer.database.PechaDatabase;
import com.kotlab.tibetanbuddhistprayer.model.MyPrayerData;

import java.util.ArrayList;

/**
 * Created by Tsephel_Treps on 12/22/2017.
 */

public class MyPrayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MyPrayerData> myPrayerData;
    private Context context;
    private String mptype;

    public MyPrayerAdapter(ArrayList<MyPrayerData> myPrayerData, Context context, String mptype) {

        this.context = context;
        this.myPrayerData = myPrayerData;
        this.mptype = mptype;
    }


    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView titletextView;
        private LinearLayout iconLayout, itemLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            titletextView = itemView.findViewById(R.id.txtmyprayertitle);
            itemLayout = itemView.findViewById(R.id.itemLinear);
            iconLayout = itemView.findViewById(R.id.linearicon);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myprayer_item_layout, parent, false);
        return new ItemViewHolder(view);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        final MyPrayerData mydata = myPrayerData.get(position);


        if (mptype.equalsIgnoreCase("tibetan")) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/nototibetanregular.ttf");

            ((ItemViewHolder) holder).titletextView.setTypeface(typeface);
        }


        ((ItemViewHolder) holder).titletextView.setText(mydata.getTitle());
        ((ItemViewHolder) holder).itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mptype.equalsIgnoreCase("english")) {
                    Intent intent = new Intent(context, enPrayerDetailActivity.class);
                    intent.putExtra("enData", mydata);
                    context.startActivity(intent);


                } else if (mptype.equalsIgnoreCase("tibetan")) {
                    Intent intent = new Intent(context, TibPrayerDetailActivity.class);
                    intent.putExtra("type", "mptibetan");
                    intent.putExtra("tibData", mydata);
                    context.startActivity(intent);
                }

            }
        });

        ((ItemViewHolder) holder).iconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Do you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                 int prayer_id = mydata.getId();
                                showMessage("Oh.. you deleted");
                                PechaDatabase pecha_db= new PechaDatabase(context);
                                pecha_db.DeleteMyPrayerData(pecha_db,prayer_id);
                                myPrayerData.remove(position);
                                notifyDataSetChanged();


                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        showMessage("Clicked no..");

                    }
                }).show();


            }
        });

    }


    private void showMessage(String s) {

        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return myPrayerData.size();
    }
}
