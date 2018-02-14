package com.aspha.toyota.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.aspha.toyota.DBAccess.Preffs;
import com.aspha.toyota.R;
import com.aspha.toyota.cwidgets.customfonts.MyEditText;
import com.aspha.toyota.mMessages.NifftyDialogs;
import com.aspha.toyota.mMessages.SeeTastyToast;

import static com.aspha.toyota.DBAccess.CRUD.isUserFound;
import static com.aspha.toyota.mBuildConfigs.Utils.isvalidEmail;

public class LogIn extends BaseActivity {
    private ProgressBar topProgressBar, downProgressBar;
    private ProgressDialog progressDialog;
    private MyEditText email, password;
    private Button btnLogin , btnBacktoSignUp;
    private Context context = LogIn.this;
    private Preffs preffs;
    private SeeTastyToast toasty;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        initObjects();
        setContentView ( R.layout.activity_log_in );
        initViews();
        initListerners();

    }


    private void showProgressDialog (final boolean isToShow) {
        if ( Build.VERSION.SDK_INT < 26 ) {
            if ( isToShow ) {
                if ( ! progressDialog.isShowing () ) {
                    progressDialog.setMessage ( "Processing ...Please wait." );
                    progressDialog.setCancelable ( false );
                    progressDialog.show ();
                }
            } else {
                if ( progressDialog.isShowing () ) {
                    progressDialog.dismiss ();
                }
            }
        } else {
            /* this is Android Oreo and above*/
            if ( isToShow ) {
                topProgressBar.setVisibility ( View.VISIBLE );
                downProgressBar.setVisibility ( View.VISIBLE );
                getWindow ().setFlags ( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE );
            } else {
                topProgressBar.setVisibility ( View.GONE );
                downProgressBar.setVisibility ( View.GONE );
                getWindow ().clearFlags ( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE );
            }
        }
    }

    @Override
    protected void initViews () {
        getSupportActionBar().setTitle ( "Log In" );
        email = findViewById ( R.id.email );
        password = findViewById ( R.id.password );
        btnLogin = findViewById ( R.id.btnLogin );
        btnBacktoSignUp = findViewById ( R.id.btnBacktoSignUp );
        topProgressBar = findViewById ( R.id.top_progressBar );
        downProgressBar = findViewById ( R.id.down_progressBar );
        if ( Build.VERSION.SDK_INT < 26 ) {
            progressDialog = new ProgressDialog ( this );
        } else {
            topProgressBar.setIndeterminate ( true );
            downProgressBar.setIndeterminate ( true );
        }
    }

    @Override
    protected void initListerners () {
        btnBacktoSignUp.setOnClickListener ( ve->startActivity ( new Intent ( this , SignIn.class ) ));
        btnLogin.setOnClickListener ( ev->{
            final String email_ = email.getText ().toString ().trim ();
            final String password_ = password.getText ().toString ().trim ();

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

            btnLogin.setEnabled ( false );
            btnBacktoSignUp.setEnabled ( false );
            showProgressDialog(true);

            showProgressDialog(false);
            btnLogin.setEnabled ( true );
            btnBacktoSignUp.setEnabled ( true );

            if(isUserFound(email_ , password_)){
                preffs.setLogStatus ( true );
                preffs.setUSER_EMAIL ( email_ );
                clearAllActivities(context ,  MainActivity.class);

            }else{

                new NifftyDialogs ( context ).messageOkError ( "Log in Error" ,"Try again or register !");
            }

        } );
    }

    @Override
    protected void setViewsValues () {

    }

    @Override
    protected void initObjects () {
        preffs = new Preffs ( context );
        toasty = new SeeTastyToast ( context );
    }
}
