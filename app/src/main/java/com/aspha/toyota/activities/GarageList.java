package com.aspha.toyota.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.aspha.toyota.DBAccess.DBGarage;
import com.aspha.toyota.DBAccess.DBProfile;
import com.aspha.toyota.DBAccess.Preffs;
import com.aspha.toyota.R;
import com.aspha.toyota.cwidgets.MyRecyclerItemClickListener;
import com.aspha.toyota.cwidgets.VerticalSpaceItemDecoration;
import com.aspha.toyota.cwidgets.customfonts.MyTextView;
import com.aspha.toyota.mAdapaters.RecyclerGarageList;
import com.aspha.toyota.mMessages.SeeTastyToast;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class GarageList  extends BaseActivity {
    private MyTextView recycViewStatus;
    private RecyclerView recycler_view;
    private RecyclerGarageList mAdapter;
    private Realm realm;
    private RealmResults<DBGarage > results;
    private DBProfile profile;
    private Button btnAddVehicle;
    private final static int VERTICAL_ITEM_SPACE = 4;//14
    private Context context = GarageList.this;
    private SeeTastyToast toasty;
    private Preffs preff;
    private RealmChangeListener realmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            mAdapter.update(results);
            recycViewStatus.setVisibility(results.isEmpty() ? View.VISIBLE : View.GONE);

        }
    };
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        initObjects ();
        setContentView ( R.layout.activity_garage_list );
        initViews ();
        setViewsValues ();
        initListerners ();
        if(profile==null){
            toasty.ToastError ( "Set up Profile, first" );
            startActivity ( new Intent ( context , Profile.class ) );
        }
    }

    @Override
    protected void initViews () {
        getSupportActionBar().setTitle ( "Garage" );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycler_view = findViewById ( R.id.recyclerview );
        btnAddVehicle = findViewById ( R.id.btnAddVehicle );
        recycViewStatus = findViewById ( R.id.recycViewStatus );
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager (context));
        recycler_view.addItemDecoration(new VerticalSpaceItemDecoration (VERTICAL_ITEM_SPACE));

    }

    @Override
    protected void initListerners () {
        btnAddVehicle.setOnClickListener ( ev->{
            startActivity ( new Intent ( context , AddVehicle.class ) );
        } );
        recycler_view.addOnItemTouchListener(new MyRecyclerItemClickListener (context, (view, position) -> {
            DBGarage feeditem = results.get ( position );


            startActivity(new Intent (
                    context, VihicleReadPage.class)
                    .putExtra("id_", feeditem.getID ())
                    .putExtra("regNumber", feeditem.getRegNumber ())
            );
        }));
    }

    @Override
    protected void setViewsValues () {

        results = realm.where(DBGarage.class).sort("ID", Sort.DESCENDING).findAll ();
        mAdapter = new RecyclerGarageList( results );
        if(results.size ()==0){

            recycViewStatus.setVisibility ( View.VISIBLE );
        }
            recycler_view.setAdapter(mAdapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        results.addChangeListener(realmChangeListener);
    }
    @Override
    protected void onDestroy () {
        super.onDestroy ();
        if (realm != null) {
            results.removeChangeListener(realmChangeListener);
            realm.close();
        }
    }
    @Override
    protected void initObjects () {
        preff = new Preffs ( context );
        toasty = new SeeTastyToast ( context );
        realm = Realm.getDefaultInstance();

        profile = realm.where ( DBProfile.class ).equalTo ( "email", preff.getUSER_EMAIL () ).findFirst ();
    }
}
