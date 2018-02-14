package com.aspha.toyota.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspha.toyota.DBAccess.DBEvents;
import com.aspha.toyota.DBAccess.DBGarage;
import com.aspha.toyota.DBAccess.Preffs;
import com.aspha.toyota.R;
import com.aspha.toyota.mMessages.SeeTastyToast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import io.realm.Realm;

public class EventReadNotification extends BaseActivity {
    private int intentID ;
    private Realm realm;
    private CollapsingToolbarLayout toolbar_layout;
    private AppBarLayout app_bar;
    private Toolbar toolbar;
    private TextView  eventTitle ,eventDate ,eventDescriptions ;
    private DBEvents result;
    private ImageView eventImage;
    private Context context = EventReadNotification.this;
    private SeeTastyToast toasty;
    private Preffs preffs;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        if (getIntent().getExtras().getString ( "IDNotification" ) != null) {
            notifications();
            intentID =  Integer.parseInt ( getIntent().getStringExtra ( "IDNotification" ));
        }else{
            intentID =  Integer.parseInt ( getIntent().getStringExtra ( "eve_id" ));
        }
        initObjects ();
        setContentView ( R.layout.activity_event_read_notification );
        initViews ();
        setViewsValues ();
        initListerners ();
    }
    private void notifications(){
        // Create channel to show notifications.
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel (channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
}

    @Override
    protected void initViews () {
         toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        eventTitle = findViewById ( R.id.eventTitle );
        eventDate = findViewById ( R.id.eventDate );
        eventDescriptions = findViewById ( R.id.eventDescriptions );
        eventImage = findViewById ( R.id.eventImage );
        app_bar= findViewById(R.id.app_bar);
        toolbar_layout= findViewById(R.id.toolbar_layout);
        toolbar.setTitle("");
        toolbar_layout.setTitleEnabled(false);

    }

    @Override
    protected void initListerners () {
        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isVisible = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    toolbar.setTitle("Event ");
                    isVisible = true;
                } else if(isVisible) {
                    toolbar.setTitle("");
                    isVisible = false;
                }
            }
        });
    }

    @Override
    protected void setViewsValues () {
        eventTitle.setText ( result.getTitle () );
        eventDate.setText ( result.getPostsdate () );


        String convt = Build.VERSION.SDK_INT >= 24 ?  String.valueOf( Html.fromHtml(result.getDescription (), Html.FROM_HTML_MODE_LEGACY))
                : String.valueOf(Html.fromHtml(result.getDescription ()));
        eventDescriptions.setText ( convt );

        Glide.with(context)
                .load(result.getImage ()).error(R.drawable.backup_load)
                .placeholder(R.drawable.backup_load)
                .thumbnail(0.1f).diskCacheStrategy( DiskCacheStrategy.ALL).crossFade()
                .fitCenter()
                .into(eventImage);
    }

    @Override
    protected void initObjects () {
        preffs = new Preffs ( context );
        toasty = new SeeTastyToast ( this );

        realm = Realm.getDefaultInstance();
        result = realm.where(DBEvents.class).equalTo ( "id" ,intentID ).findFirst ();



    }
}
