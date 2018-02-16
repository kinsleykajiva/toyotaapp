package com.aspha.toyota.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aspha.toyota.DBAccess.DBGarage;
import com.aspha.toyota.R;
import com.aspha.toyota.fragments.Fragment_BookService;
import com.aspha.toyota.fragments.Fragment_parts;
import com.aspha.toyota.fragments.Fragment_partsAftersales;
import com.aspha.toyota.mMessages.SeeTastyToast;

import io.realm.Realm;

public class RequestService extends BaseActivity {
    private Context context = RequestService.this;

    private Fragment fragement;
    private SeeTastyToast toasty;

    private int intentID;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        initObjects ();
        setContentView ( R.layout.activity_request_service );
        initViews ();
        setViewsValues ();
        initListerners ();
    }

    @Override
    protected void initViews () {

        //getActionBar ().setDisplayHomeAsUpEnabled ( true );
    }

    @Override
    protected void initListerners () {

    }

    @Override
    protected void setViewsValues () {

        FragmentTransaction ft = getSupportFragmentManager ().beginTransaction ();

        ft.replace ( R.id.framelayoutholder, fragement );
// or ft.add(R.id.your_placeholder, new FooFragment());

        ft.commit ();
    }

    public static int id_i = 0;

    @Override
    protected void initObjects () {
        id_i = getIntent ().getExtras ().getInt ( "id_" );
        if ( getIntent ().getExtras ().getString ( "frag" ).equals ( "Fragment_BookService" ) ) {
          //  getActionBar ().setTitle ( "Book Service" );

            fragement = new Fragment_BookService ();

        } else {
//            getActionBar ().setTitle ( "Enquire parts " );
            fragement = new Fragment_parts ();
        }
    }
}
