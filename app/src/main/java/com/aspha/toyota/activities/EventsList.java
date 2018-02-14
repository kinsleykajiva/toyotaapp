package com.aspha.toyota.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspha.toyota.DBAccess.DBEvents;
import com.aspha.toyota.R;
import com.aspha.toyota.cwidgets.MyRecyclerItemClickListener;
import com.aspha.toyota.cwidgets.VerticalSpaceItemDecoration;
import com.aspha.toyota.cwidgets.customfonts.MyTextView;
import com.aspha.toyota.mAdapaters.EventsListRecycler;
import com.aspha.toyota.mAdapaters.RecyclerGarageList;
import com.aspha.toyota.mMessages.SeeTastyToast;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class EventsList extends BaseActivity {
    private MyTextView recycViewStatus;
    private RecyclerView recycler_view;
    private EventsListRecycler mAdapter;
    private Realm realm;
    private RealmResults<DBEvents > results;
    private final static int VERTICAL_ITEM_SPACE = 4;//14
    private Context context = EventsList.this;
    private SeeTastyToast toasty;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        initObjects ();
        setContentView ( R.layout.activity_events_list );
        initViews ();
        initListerners ();
        setViewsValues ();
    }

    @Override
    protected void initViews () {
        getSupportActionBar().setTitle ( "Events" );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycler_view = findViewById ( R.id.recyclerview );

        recycViewStatus = findViewById ( R.id.recycViewStatus );
    }

    @Override
    protected void initListerners () {
        recycler_view.addOnItemTouchListener(new MyRecyclerItemClickListener (context, (view, position) -> {
            DBEvents feeditem = results.get ( position );
            startActivity(new Intent (
                    context, EventReadNotification.class)
                    .putExtra("eve_id", feeditem.getId ()+"")

            );
        }));
    }
    @Override
    protected void onDestroy () {
        super.onDestroy ();
        if (realm != null) {

            realm.close();
        }
    }
    @Override
    protected void setViewsValues () {
        mAdapter = new EventsListRecycler (context, results );
        if(results.size ()==0){
            toasty.ToastDafault ( "No Events to show yet" );
            recycViewStatus.setVisibility ( View.VISIBLE );
        }else{
            recycler_view.setLayoutManager(new LinearLayoutManager (context));
            recycler_view.addItemDecoration(new VerticalSpaceItemDecoration (VERTICAL_ITEM_SPACE));

            recycler_view.setAdapter(mAdapter);
            recycler_view.setHasFixedSize(true);

        }
    }

    @Override
    protected void initObjects () {
        toasty = new SeeTastyToast ( context );
        realm = Realm.getDefaultInstance();
        results = realm.where(DBEvents.class).sort("id", Sort.DESCENDING).findAll ();

    }
}
