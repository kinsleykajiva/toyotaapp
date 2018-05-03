package com.aspha.toyota.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.aspha.toyota.DBAccess.Preffs;
import com.aspha.toyota.R;
import com.aspha.toyota.cwidgets.customfonts.MyEditText;
import com.aspha.toyota.mMessages.NifftyDialogs;
import com.aspha.toyota.mMessages.SeeTastyToast;

import static com.aspha.toyota.DBAccess.CRUD.isUserFound;
import static com.aspha.toyota.DBAccess.CRUD.saveUserDetails;
import static com.aspha.toyota.mBuildConfigs.Utils.isvalidEmail;
import static com.aspha.toyota.mNetWorks.NetGet.registerNet;

public class SignIn extends BaseActivity {
    private MyEditText email, password, con_password;
    private Button btnRegister, btnBacktoLohin;
    private Preffs preffs;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        initObjects ();
        setContentView ( R.layout.activity_sign_in );
        initViews ();
        initListerners ();

    }


    @Override
    protected void initViews () {
        getSupportActionBar().setTitle ( "Sign Up" );
        email = findViewById ( R.id.email );
        password = findViewById ( R.id.password );
        con_password = findViewById ( R.id.con_password );
        btnRegister = findViewById ( R.id.btnRegister );
        btnBacktoLohin = findViewById ( R.id.btnBacktoLohin );

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
    @Override
    protected void initListerners () {
        btnBacktoLohin.setOnClickListener ( ev -> {
            onBackPressed ();
        } );
        btnRegister.setOnClickListener ( ev -> {
            final String email_ = email.getText ().toString ().trim ();
            final String password_ = password.getText ().toString ().trim ();
            final String con_password_ = con_password.getText ().toString ().trim ();

            if ( email_.isEmpty () ) {
                email.setError ( "Email can't be empty" );
                return;
            }
            if ( !isvalidEmail(email_ )) {
                email.setError ( "Please put a Valid Email" );
                return;
            }
            if ( password_.isEmpty () ) {
                password.setError ( "Passsword can't be empty" );
                return;
            }
            if ( con_password_.isEmpty () ) {
                con_password.setError ( "Confirm Password" );
                return;
            }
            if ( !con_password_.equals (password_) ) {
                con_password.setError ( "Passwords not the same" );
                password.setError ( "Passwords not the same" );
                return;
            }
            if(isUserFound(email_ , password_)){
                new NifftyDialogs (SignIn.this).messageOk ( "You are already Saved.Just log in!" );
                return;
            }
            showProgressDialog ( true );
            new AsyncTask<Void,Void,String> (){

                @Override
                protected String doInBackground (Void... voids) {
                    return registerNet(email_ , password_);
                }

                @Override
                protected void onPostExecute (String s) {
                    super.onPostExecute ( s );
                    Log.e ( "xxx", "onPostExecute: "+s  );
                    showProgressDialog ( false);
                    if(s.equals ( "done" )){
                        saveUserDetails ( email_ , password_ );
                        preffs.setLogStatus ( false );
                        preffs.setUSER_EMAIL ( email_ );

                        new SeeTastyToast ( SignIn.this ).ToastSuccess ( "You can Log In !" );
                        startActivity ( new Intent ( SignIn.this , LogIn.class ) );
                        finish ();
                    }
                    if(s.equals ( "failed" )) {
                        new NifftyDialogs(SignIn.this).messageOkError("Connection Failed", "Failed to Save,try again");
                        Toast.makeText ( SignIn.this, "Failed to Save", Toast.LENGTH_SHORT ).show ();

                    }
                    if(s.equals ( "found" )){
                        Toast.makeText ( SignIn.this, "Already Registerd, Please log in", Toast.LENGTH_SHORT ).show ();
                    }
                    if(s.isEmpty ()){
                        new NifftyDialogs(SignIn.this).messageOkError("Connection Failed","try again");
                     //   Toast.makeText ( SignIn.this, "Connection Failed , Try again", Toast.LENGTH_SHORT ).show ();
                    }
                }

            }.execute (  );


        } );
    }

    @Override
    protected void setViewsValues () {

    }

    @Override
    protected void initObjects () {
        progressDialog = new ProgressDialog ( this );
        preffs = new Preffs(this);
    }
}

