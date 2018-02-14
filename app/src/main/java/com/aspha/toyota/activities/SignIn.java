package com.aspha.toyota.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.aspha.toyota.DBAccess.Preffs;
import com.aspha.toyota.R;
import com.aspha.toyota.cwidgets.customfonts.MyEditText;
import com.aspha.toyota.mMessages.NifftyDialogs;
import com.aspha.toyota.mMessages.SeeTastyToast;

import static com.aspha.toyota.DBAccess.CRUD.isUserFound;
import static com.aspha.toyota.DBAccess.CRUD.saveUserDetails;
import static com.aspha.toyota.mBuildConfigs.Utils.isvalidEmail;

public class SignIn extends BaseActivity {
    private MyEditText email, password, con_password;
    private Button btnRegister, btnBacktoLohin;
    private Preffs preffs;
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
            saveUserDetails ( email_ , password_ );
            preffs.setLogStatus ( false );
            preffs.setUSER_EMAIL ( email_ );

            new SeeTastyToast ( SignIn.this ).ToastSuccess ( "You can Log In !" );
            startActivity ( new Intent ( SignIn.this , LogIn.class ) );
            finish ();

        } );
    }

    @Override
    protected void setViewsValues () {

    }

    @Override
    protected void initObjects () {
        preffs = new Preffs(this);
    }
}

