package com.aspha.toyota.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aspha.toyota.DBAccess.DBProfile;
import com.aspha.toyota.DBAccess.Preffs;
import com.aspha.toyota.R;
import com.aspha.toyota.cwidgets.customfonts.MyEditText;
import com.aspha.toyota.mMessages.SeeTastyToast;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class ViewProfile extends BaseActivity {
    private TextView txtName, txtsurname, txtemail, txtaddress, txtcellNumber;
    private Button btnProfile;
    private SeeTastyToast toasty;
    private Preffs preffs;
    private Context context = ViewProfile.this;
    private Realm realm;
    private DBProfile profile;
    private RealmChangeListener realmChangeListener = element -> setViewsValues ();
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        initObjects ();
        setContentView ( R.layout.activity_view_profile );
        initViews ();
        initListerners ();
        if(profile!=null){

            setViewsValues ();
        }else{
            startActivity ( new Intent ( context , Profile.class ) );
            finish ();
            return;
        }

    }
    @Override
    public void onStart() {
        super.onStart();
        if(profile!=null){
            profile.addChangeListener(realmChangeListener);
        }

    }
    @Override
    protected void initViews () {
        getSupportActionBar().setTitle ( "View Profile" );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtName = findViewById ( R.id.txtName );
        txtsurname = findViewById ( R.id.txtsurname );
        txtemail = findViewById ( R.id.txtemail );
        txtaddress = findViewById ( R.id.txtaddress );
        txtcellNumber = findViewById ( R.id.txtcellNumber );
        btnProfile = findViewById ( R.id.btnProfile );
    }

    @Override
    protected void initListerners () {
        btnProfile.setOnClickListener ( ev->{
            startActivity ( new Intent ( context , Profile.class ) );
        } );
    }

    @Override
    protected void setViewsValues () {
        txtName.setText ( "Name : " + profile.getName () );
        txtsurname.setText ( "Surname: " +  profile.getSurname () );
        txtemail.setText ( "Email :" +  profile.getEmail () );
        txtaddress.setText ( "Address :" +  profile.getAddress () );
        txtcellNumber.setText (  "Cell :" +  profile.getCellNumber () );
    }
    @Override
    protected void onDestroy () {
        super.onDestroy ();
        if (realm != null) {
            if(profile!=null){

                profile.removeChangeListener(realmChangeListener);
            }
            realm.close();
        }
    }

    @Override
    protected void initObjects () {
        preffs = new Preffs ( context );
        toasty = new SeeTastyToast ( this );
        realm = Realm.getDefaultInstance ();
        profile = realm.where ( DBProfile.class ).equalTo ( "email", preffs.getUSER_EMAIL () ).findFirst ();

    }
}
