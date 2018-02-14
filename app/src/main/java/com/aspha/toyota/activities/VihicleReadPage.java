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
import android.widget.TextView;

import com.aspha.toyota.DBAccess.DBBookServiceHistory;
import com.aspha.toyota.DBAccess.DBGarage;
import com.aspha.toyota.DBAccess.Preffs;
import com.aspha.toyota.R;
import com.aspha.toyota.cwidgets.MyRecyclerItemClickListener;
import com.aspha.toyota.cwidgets.VerticalSpaceItemDecoration;
import com.aspha.toyota.cwidgets.customfonts.MyTextView;
import com.aspha.toyota.mAdapaters.RecyclerGarageList;
import com.aspha.toyota.mAdapaters.VihicleRecyclerAdapter;
import com.aspha.toyota.mMessages.SeeTastyToast;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class VihicleReadPage extends BaseActivity {
    private Context context = VihicleReadPage.this;
    private Realm realm;
    private SeeTastyToast toasty;
    private Button btnBook;
    private TextView infoV;
    private Preffs preffs;
    private DBGarage result;
    private int intentID ;
    private MyTextView recycViewStatus;
    private RecyclerView recycler_view;
    private VihicleRecyclerAdapter mAdapter;
    private final static int VERTICAL_ITEM_SPACE = 4;
    private RealmResults<DBBookServiceHistory > results;
    private String regIntent ;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        initObjects ();
        setContentView ( R.layout.activity_vihicle_read_page );
        initViews ();
        initListerners ();
        setViewsValues ();
    }

    @Override
    protected void initViews () {
        getSupportActionBar().setTitle ( "Vehicle Page" );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnBook = findViewById ( R.id.btnBook );
        infoV = findViewById ( R.id.infoV );
        recycler_view = findViewById ( R.id.recyclerview );
        recycViewStatus = findViewById ( R.id.recycViewStatus );

    }

    @Override
    protected void initListerners () {
        btnBook.setOnClickListener ( ev->{
            startActivity(new Intent (
                    context, BookService.class)
                    .putExtra("id_", intentID)
            );
        } );
    }

    @Override
    protected void setViewsValues () {
        infoV.setText (
                "Model :" +result.getModelName () + " \n"
                + "Registration Number: " + result.getRegNumber () +"\n"
                + "Added on " + result.getDate_ ()

        );


        results = realm.where(DBBookServiceHistory.class).equalTo ( "regNumber",regIntent ).findAll ();
        mAdapter = new VihicleRecyclerAdapter( results );

        if(results.size ()==0){
            toasty.ToastDafault ( "No History yet" );
            recycViewStatus.setText ( "No History yet" );
        }else{
            recycler_view.setLayoutManager(new LinearLayoutManager (context));
            recycler_view.addItemDecoration(new VerticalSpaceItemDecoration (VERTICAL_ITEM_SPACE));
            recycViewStatus.setText ( "History (" + results.size () +")" );
            recycler_view.setAdapter(mAdapter);
            recycler_view.setHasFixedSize(true);

        }
    }

    @Override
    protected void initObjects () {
        preffs = new Preffs ( context );
        toasty = new SeeTastyToast ( this );
        intentID = getIntent().getIntExtra ( "id_" ,7);
        regIntent = getIntent().getStringExtra ( "regNumber" );
        realm = Realm.getDefaultInstance();
        result = realm.where(DBGarage.class).equalTo ( "ID" ,intentID ).findFirst ();



    }
    @Override
    protected void onDestroy () {
        super.onDestroy ();
        if (realm != null) {

            realm.close();
        }
    }
}
