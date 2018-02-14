package com.aspha.toyota.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspha.toyota.DBAccess.DBGarage;
import com.aspha.toyota.DBAccess.DBService;
import com.aspha.toyota.R;
import com.aspha.toyota.activities.RequestService;
import com.aspha.toyota.activities.VihicleReadPage;
import com.aspha.toyota.cwidgets.MyRecyclerItemClickListener;
import com.aspha.toyota.cwidgets.VerticalSpaceItemDecoration;
import com.aspha.toyota.cwidgets.customfonts.MyTextView;
import com.aspha.toyota.mAdapaters.RecyclerGarageList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Kajiva Kinsley on 2/13/2018.
 */

public class Fragements_services extends Fragment {
    private View layout;
    private MyTextView recycViewStatus;
    private RecyclerView recycler_view;
    private RecyclerGarageList mAdapter;
    private final static int VERTICAL_ITEM_SPACE = 4;//14
    private Realm realm;
    private RealmResults <DBGarage> resultsGarage;
    private RealmResults <DBService> resultsGarage1;

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        realm = Realm.getDefaultInstance ();
        resultsGarage  = realm.where(DBGarage.class).sort("ID", Sort.DESCENDING).findAll ();
    }

    @Override
    public void onDestroy () {
        super.onDestroy ();
        if(realm !=null){
            realm.close ();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate( R.layout.fragmen_services, container, false);
        initViews();
        setViewsValues();
        initListerners();


        return layout;
    }

    private void initListerners () {
        recycler_view.addOnItemTouchListener(new MyRecyclerItemClickListener (getContext (), (view, position) -> {
            DBGarage feeditem = resultsGarage.get ( position );

            startActivity(new Intent (
                    getContext (), RequestService.class)
                    .putExtra("id_", feeditem.getID ())
                    .putExtra("regNumber", feeditem.getRegNumber ())
            );
        }));
    }

    private void setViewsValues () {
        mAdapter = new RecyclerGarageList ( resultsGarage );
        if(resultsGarage.size ()==0){

            recycViewStatus.setVisibility ( View.VISIBLE );
        }else {

            recycler_view.setAdapter ( mAdapter );

        }

    }

    private void initViews () {
        recycler_view = layout.findViewById ( R.id.recyclerview );

        recycViewStatus = layout.findViewById ( R.id.recycViewStatus );
        recycler_view.setHasFixedSize ( true );
        recycler_view.setLayoutManager ( new LinearLayoutManager ( getContext () ) );
        recycler_view.addItemDecoration ( new VerticalSpaceItemDecoration ( VERTICAL_ITEM_SPACE ) );


    }

}
