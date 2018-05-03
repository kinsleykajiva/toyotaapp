package com.aspha.toyota.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aspha.toyota.DBAccess.DBBookServiceHistory;
import com.aspha.toyota.DBAccess.DBGarage;
import com.aspha.toyota.DBAccess.DBProfile;
import com.aspha.toyota.DBAccess.DBService;
import com.aspha.toyota.R;
import com.robinhood.spark.SparkAdapter;
import com.robinhood.spark.SparkView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Kajiva Kinsley on 30-Apr-18.
 */

public class ReportsVehiclesFragment  extends Fragment {
    private Realm realm ;
    private RealmResults<DBGarage> garageRealmResults;
    private RealmResults<DBService> serviceRealmResults;
    private RealmResults<DBBookServiceHistory> bookServiceHistoryRealmResults;

    private TextView NumberOfCars ,Models,NumberOfServicesDone;
    View view;

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        realm = Realm.getDefaultInstance ();
        garageRealmResults = realm.where ( DBGarage.class ).findAll ();
        serviceRealmResults = realm.where ( DBService.class ).findAll ();
        bookServiceHistoryRealmResults = realm.where ( DBBookServiceHistory.class ).findAll ();

    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate ( R.layout.frag_vehicles_report, container, false );
        NumberOfCars = view.findViewById ( R.id.NumberOfCars );
        Models = view.findViewById ( R.id.Models );
        NumberOfServicesDone = view.findViewById ( R.id.NumberOfServicesDone );
        SparkView sparkView = view. findViewById(R.id.sparkview);

        Set<String> models = new HashSet <> (  );
        Map<String , Integer> modelCount = new HashMap <> (  );
        for ( DBGarage el:garageRealmResults ){
            models.add ( el.getModelName () );
        }
        for(int i=0 ; i< garageRealmResults.size () ; i++){
            int cn=0;
            for ( String ky :models ){
                if(ky.equals ( garageRealmResults.get ( i ).getModelName ()  )){
                    // check if key exists
                    if(modelCount.containsKey ( garageRealmResults.get ( i ).getModelName () )){
                        // exists
                       int was= modelCount.get ( garageRealmResults.get ( i ).getModelName () );
                       modelCount.put ( garageRealmResults.get ( i ).getModelName (),was++ );
                    }else{
                        // new
                        modelCount.put ( garageRealmResults.get ( i ).getModelName (),cn++ );
                    }
                }else{
                    modelCount.put ( garageRealmResults.get ( i ).getModelName (),cn++ );
                }
            }
        }
        for(int q=0;q<modelCount.size ();q++){
            Log.e ( "xxx", "onCreateView: " + modelCount.get ( q ) );
        }
        //for(Map.Entry<String , Integer> els:garageRealmResults)

        Models.setText ( garageRealmResults.size () );
        sparkView.setAdapter(new MyAdapter(new float[]{garageRealmResults.size () ,garageRealmResults.size ()}));
       return view;
    }

    @Override
    public void onDestroy () {
        super.onDestroy ();
        realm.close ();
    }
    public class MyAdapter extends SparkAdapter {
        private float[] yData;

        public MyAdapter(float[] yData) {
            this.yData = yData;
        }

        @Override
        public int getCount() {
            return yData.length;
        }

        @Override
        public Object getItem(int index) {
            return yData[index];
        }

        @Override
        public float getY(int index) {
            return yData[index];
        }
    }
}
