package com.aspha.toyota.mAdapaters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aspha.toyota.DBAccess.DBGarage;
import com.aspha.toyota.R;

import io.realm.RealmResults;

/**
 * Created by Kajiva Kinsley on 2/5/2018.
 */

public class RecyclerGarageList extends RecyclerView.Adapter<RecyclerGarageList.CustomViewHolder> {

    private RealmResults<DBGarage > results;

    public RecyclerGarageList ( RealmResults < DBGarage > results) {
        update(results);
        this.results = results;
    }
    static class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView descrp, txtModel;

        CustomViewHolder (View view) {
            super(view);
            this.descrp = view.findViewById ( R.id.description );

            this.txtModel =  view.findViewById(R.id.txtModel);

        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_garage, null);
        return new CustomViewHolder(view);
    }
    @Override
    public void onBindViewHolder (CustomViewHolder holder, int position) {
        DBGarage feeditem = results.get ( position );

        holder.txtModel.setText ( "Model: "+feeditem.getModelName ()  );
        holder.descrp.setText ("Reg-Number: "+feeditem.getRegNumber ());

    }
    public void update(RealmResults<DBGarage> feedItemList) {
        this.results = feedItemList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {

        return (null != results ? results.size() : 0);

    }
}
