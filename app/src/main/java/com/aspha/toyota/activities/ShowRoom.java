package com.aspha.toyota.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.aspha.toyota.DBAccess.Preffs;
import com.aspha.toyota.R;
import com.aspha.toyota.cwidgets.GridSpacingItemDecoration;
import com.aspha.toyota.cwidgets.VerticalSpaceItemDecoration;
import com.aspha.toyota.mAdapaters.RecyclerGarageList;
import com.aspha.toyota.mAdapaters.ShowRoomRecyclerAdapter;
import com.aspha.toyota.mMessages.SeeTastyToast;
import com.aspha.toyota.mPojo.ShowRoomPojo;

import java.util.ArrayList;
import java.util.List;

public class ShowRoom extends BaseActivity {
private ShowRoomRecyclerAdapter mAdapter;
    private RecyclerView recycler_view;
    private Context context = ShowRoom.this;
    private SeeTastyToast toasty;
    private Preffs preff;
    private ArrayList<ShowRoomPojo > results = new ArrayList <> (  );
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        initObjects ();
        setContentView ( R.layout.activity_show_room );
        initViews ();
        initListerners ();
        setViewsValues ();


    }

    @Override
    protected void initViews () {
        getSupportActionBar().setTitle ( "Show Room");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycler_view = findViewById ( R.id.recyclerview );
        recycler_view.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager =new GridLayoutManager (context,2);
        recycler_view.setLayoutManager(layoutManager);

        int spanCount = 2; // columns
        int spacing = 20; // px
        boolean includeEdge = true;
        recycler_view.addItemDecoration(new GridSpacingItemDecoration (spanCount, spacing, includeEdge));

    }

    @Override
    protected void initListerners () {

    }

    @Override
    protected void setViewsValues () {
        mAdapter = new ShowRoomRecyclerAdapter ( context , results );
        recycler_view.setAdapter(mAdapter);
    }

    @Override
    protected void initObjects () {
        preff = new Preffs ( context );
        toasty = new SeeTastyToast ( context );
        results.add ( new ShowRoomPojo ( "Hilux" , R.drawable.hiluxcover , "$232 500-20" ) );
        results.add ( new ShowRoomPojo ( "Fortuner" , R.drawable.fortuner, "$303 000-00" ) );
        results.add ( new ShowRoomPojo ( "Hiace" , R.drawable.hiace , "$23 000-20" ) );
        results.add ( new ShowRoomPojo ( "Hilux Single" , R.drawable.hiluxsingle , "$100 540-00" ) );
        results.add ( new ShowRoomPojo ( "Land Cruser" , R.drawable.landcruiser, "$550 000-00" ) );
        results.add ( new ShowRoomPojo ( "Prado" , R.drawable.prado , "$90 500-00" ) );
        results.add ( new ShowRoomPojo ( "Land Cruiser" , R.drawable.landcruiser , "$340 000-00" ) );
    }
}
