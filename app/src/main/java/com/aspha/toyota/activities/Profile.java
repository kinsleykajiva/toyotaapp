package com.aspha.toyota.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.aspha.toyota.DBAccess.DBProfile;
import com.aspha.toyota.DBAccess.Preffs;
import com.aspha.toyota.R;
import com.aspha.toyota.cwidgets.customfonts.MyEditText;
import com.aspha.toyota.cwidgets.customfonts.MyTextView;
import com.aspha.toyota.mMessages.SeeTastyToast;

import io.realm.Realm;

import static com.aspha.toyota.DBAccess.CRUD.saveProfile;
import static com.aspha.toyota.mBuildConfigs.Utils.isvalidEmail;
import static com.aspha.toyota.mNetWorks.NetGet.saveProfileNET;

public class Profile extends BaseActivity {
    private MyEditText fullname, surname, celle, email, address;
    private Button buttonProceed;
    private SeeTastyToast toasty;
    private Preffs preffs;
    private Realm realm;
    private DBProfile profile;
    private Context context = Profile.this;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        initObjects ();
        setContentView ( R.layout.activity_profile );
        initViews ();
        initListerners ();
        setViewsValues ();
    }

    @Override
    protected void initViews () {
        getSupportActionBar().setTitle ( "Edit Profile" );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fullname = findViewById ( R.id.fullname );
        surname = findViewById ( R.id.surname );
        email = findViewById ( R.id.email );
        address = findViewById ( R.id.address );
        celle = findViewById ( R.id.celle );
        buttonProceed = findViewById ( R.id.buttonProceed );
    }

    @Override
    protected void initListerners () {
        buttonProceed.setOnClickListener ( er -> {
            final String fullname_ = fullname.getText ().toString ().trim ();
            final String surname_ = surname.getText ().toString ().trim ();
            final String email_ = email.getText ().toString ().trim ();
            final String address_ = address.getText ().toString ().trim ();
            final String celle_ = celle.getText ().toString ().trim ();

            if ( email_.isEmpty () ) {
                email.setError ( "Email can't be empty" );
                return;
            }
            if ( !isvalidEmail(email_ )) {
                email.setError ( "Please put a Valid Email" );
                return;
            }
            if ( fullname_.isEmpty () ) {
                fullname.setError ( "Name can't be empty" );
                return;
            }
            if ( surname_.isEmpty () ) {
                surname.setError ( "Surname can't be empty" );
                return;
            }
            if ( address_.isEmpty () ) {
                address.setError ( "Address can't be empty" );
                return;
            }
            if ( celle_.isEmpty () ) {
                celle.setError ( "Cell can't be empty" );
                return;
            }
            showProgressDialog(true);
            new AsyncTask<Void,Void,String> (){

                @Override
                protected String doInBackground (Void... voids) {
                    return saveProfileNET( fullname_, surname_, celle_, email_, address_ );
                }

                @Override
                protected void onPostExecute (String s) {
                    super.onPostExecute ( s );

                    showProgressDialog(false);
                    if(s.equals ( "done" )) {
                        saveProfile ( fullname_, surname_, celle_, email_, address_ );
                        toasty.ToastSuccess ( "Saved" );
                        onBackPressed ();
                    }
                    if(s.equals ( "up-date-done" )) {
                        saveProfile ( fullname_, surname_, celle_, email_, address_ );
                        toasty.ToastSuccess ( "Updated Profile" );
                        onBackPressed ();
                    }
                    if(s.equals ( "failed" )) {

                        toasty.ToastError ( "Failed to save Profile" );
                    }
                    if(s.equals ( "up-date-failed" )) {

                        toasty.ToastError ( "Failed to save Profile" );
                    }
                    if(s.isEmpty ()){
                        toasty.ToastError (  "Connection Failed , Try again");
                    }
                }
            }.execute (  );





        } );
    }

    @Override
    protected void setViewsValues () {
        email.setText ( preffs.getUSER_EMAIL () );
        if(profile !=null){
            fullname.setText ( profile.getName () );
            surname.setText ( profile.getSurname () );
            address.setText ( profile.getAddress () );
            celle.setText ( profile.getCellNumber () );
            buttonProceed.setText ( "Update" );
        }
    }

    @Override
    protected void initObjects () {
        preffs = new Preffs ( context );
        progressDialog = new ProgressDialog ( this );
        toasty = new SeeTastyToast ( this );
        realm = Realm.getDefaultInstance ();
        profile = realm.where ( DBProfile.class ).equalTo ( "email", preffs.getUSER_EMAIL () ).findFirst ();

    }
    private void showProgressDialog (final boolean isToShow) {

        if ( isToShow ) {
            if ( ! progressDialog.isShowing () ) {
                progressDialog.setMessage ( "Processing..." );
                progressDialog.setCancelable ( false );
                progressDialog.show ();
            }
        } else {
            if ( progressDialog.isShowing () ) {
                progressDialog.dismiss ();
            }
        }

    }
}
