package com.aspha.toyota.mAdapaters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspha.toyota.DBAccess.DBEvents;
import com.aspha.toyota.R;
import com.aspha.toyota.cwidgets.customfonts.MyTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import io.realm.RealmResults;

/**
 * Created by Kajiva Kinsley on 2/13/2018.
 */

public class EventsListRecycler extends RecyclerView.Adapter<EventsListRecycler.CustomViewHolder> {
private RealmResults<DBEvents > results;
    private  Context context ;

    public EventsListRecycler (Context context, RealmResults < DBEvents > results) {
        this.context = context;
        this.results = results;
    }
     static class CustomViewHolder extends RecyclerView.ViewHolder {

        MyTextView recycle_title, recycle_date_time ,recycle_repeat_info;
        ImageView thumbnail_image;

        CustomViewHolder (View view) {
            super(view);
            this.recycle_repeat_info = view.findViewById ( R.id.recycle_repeat_info );
            thumbnail_image = view.findViewById ( R.id.thumbnail_image );
            this.recycle_date_time =  view.findViewById(R.id.recycle_date_time);
            this.recycle_title =  view.findViewById(R.id.recycle_title);


        }
    }
         @Override
         public CustomViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
             View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item, null);
             return new CustomViewHolder (view);
         }
         @Override
         public void onBindViewHolder (CustomViewHolder holder, int position) {
             DBEvents feeditem = results.get ( position );
             holder.recycle_title.setText ( ""+feeditem.getTitle ()  );
             holder.recycle_date_time.setText (feeditem.getPosttime ());
             String convt = Build.VERSION.SDK_INT >= 24 ?  String.valueOf( Html.fromHtml(feeditem.getDescription (), Html.FROM_HTML_MODE_LEGACY))
                     : String.valueOf(Html.fromHtml(feeditem.getDescription ()));
             holder.recycle_repeat_info.setText (convt);
             Glide.with(context)
                     .load(feeditem.getImage ())
                     .error(R.drawable.backup_load)
                     .placeholder(R.drawable.backup_load)
                     .thumbnail(0.1f).diskCacheStrategy( DiskCacheStrategy.ALL).crossFade()
                     .fitCenter()
                     .into(holder.thumbnail_image);
         }
         public void update(RealmResults<DBEvents> feedItemList) {
             this.results = feedItemList;

         }
         @Override
         public int getItemCount() {

             return (null != results ? results.size() : 0);

         }
}

