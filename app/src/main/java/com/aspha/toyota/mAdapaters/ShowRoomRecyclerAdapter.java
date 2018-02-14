package com.aspha.toyota.mAdapaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspha.toyota.R;
import com.aspha.toyota.cwidgets.customfonts.MyTextView;
import com.aspha.toyota.mPojo.ShowRoomPojo;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Kajiva Kinsley on 2/7/2018.
 */

public class ShowRoomRecyclerAdapter extends RecyclerView.Adapter<ShowRoomRecyclerAdapter.CustomViewHolder> {

    private ArrayList<ShowRoomPojo > results;
    private Context context;

    public ShowRoomRecyclerAdapter (Context context, ArrayList<ShowRoomPojo > results) {
         this.context = context;
        this.results = results;
    }
    static class CustomViewHolder extends RecyclerView.ViewHolder {

        MyTextView title, txtdesciption;
        ImageView imgthumbnail;

        CustomViewHolder (View view) {
            super(view);
            this.title = view.findViewById ( R.id.title );
            this.imgthumbnail = view.findViewById ( R.id. imgthumbnail);
            this.txtdesciption =  view.findViewById(R.id.txtdesciption);

        }
    }
    @Override
    public CustomViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_showroom, null);
        return new CustomViewHolder (view);
    }
    @Override
    public void onBindViewHolder (CustomViewHolder holder, int position) {
        ShowRoomPojo feeditem = results.get (position )  ;

        holder.title.setText (feeditem.getCarName ()  );
        Glide.with(context).load(feeditem.getImg ()).thumbnail(0.1f).into(holder.imgthumbnail);

        holder.txtdesciption.setText (feeditem.getPrice ());

    }
    @Override
    public int getItemCount() {

        return (null != results ? results.size() : 0);

    }
}
