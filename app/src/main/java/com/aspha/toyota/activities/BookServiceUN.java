package com.aspha.toyota.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aspha.toyota.DBAccess.Preffs;
import com.aspha.toyota.R;
import com.aspha.toyota.cwidgets.customfonts.MyEditText;
import com.aspha.toyota.mMessages.SeeTastyToast;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.SEND_SMS;
import static com.aspha.toyota.activities.BookService.PERMISSION_REQUEST_CODE;
import static com.aspha.toyota.mBuildConfigs.Utils.isvalidEmail;

public class BookServiceUN extends BaseActivity {
    private Preffs preffs;
    private SeeTastyToast toasty;
    private Context context = BookServiceUN.this;
    private Button btnAdd;
    private ProgressBar top_progressBar, down_progressBar;
    private MyEditText name, surname, modelName, RegNumber, celle, email;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        initObjects ();
        setContentView ( R.layout.activity_book_service_un );
        initViews ();
        initListerners ();
        setViewsValues ();
    }

    @Override
    protected void initViews () {
        getSupportActionBar ().setTitle ( "Book for a Service" );
        getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );
        name = findViewById ( R.id.name );
        surname = findViewById ( R.id.surname );
        modelName = findViewById ( R.id.modelName );
        RegNumber = findViewById ( R.id.RegNumber );
        celle = findViewById ( R.id.celle );
        email = findViewById ( R.id.email );
        btnAdd = findViewById ( R.id.btnAdd );
        top_progressBar = findViewById ( R.id.top_progressBar );
        down_progressBar = findViewById ( R.id.down_progressBar );

    }

    @Override
    protected void initListerners () {
        btnAdd.setOnClickListener ( er -> {
             String modelName_ = modelName.getText ().toString ().trim ();
             String RegNumber_ = RegNumber.getText ().toString ().trim ();
             String name_ = name.getText ().toString ().trim ();
             String surname_ = surname.getText ().toString ().trim ();
             String celle_ = celle.getText ().toString ().trim ();
             String email_ = email.getText ().toString ().trim ();
            if ( name_.isEmpty () ) {
                name.setError ( "Name can't be empty" );
                return;
            }
            if ( surname_.isEmpty () ) {
                surname.setError ( "Surname can't be empty" );
                return;
            }

            if ( modelName_.isEmpty () ) {
                modelName.setError ( "Model Name can't be empty" );
                return;
            }
            if ( RegNumber_.isEmpty () ) {
                RegNumber.setError ( "Reg Number can't be empty" );
                return;
            }


            if ( celle_.isEmpty () ) {
                celle.setError ( "Cell No. can't be empty" );
                return;
            }
            if ( email_.isEmpty () ) {
                email.setError ( "Email can't be empty" );
                return;
            }
            if ( ! isvalidEmail ( email_ ) ) {
                email.setError ( "Invalid Email" );
                return;
            }
            String message = "I " + name_ + " " + surname_
                    + "requesting a service for vehicle " + modelName_
                    + " Registration Number " + RegNumber_ + " \n"
                    + "Phone:" + celle_ + "\n"
                    + "Email:" + email_
                    + "\n\n"
                    + "General Repair";
            initPermissions ();
            SendSmsBtn ( message );

        } );
    }

    private void SendSmsBtn (String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault ();

            smsManager.sendTextMessage ( "0732446872", null, message, null, null );

            toasty.ToastSuccess ( "Booked. Message Sent." );
            clearAllActivities ( context, MainActivity.class );

        } catch (Exception ex) {
            Toast.makeText ( getApplicationContext (), ex.getMessage ().toString (),
                    Toast.LENGTH_LONG ).show ();
            toasty.ToastError ( "Message not Sent." );
            ex.printStackTrace ();
            Log.e ( "smsError", "" + ex );
        }
    }

    private void initPermissions () {
        if ( ! checkPermission () ) {
            requestPermission ();
            Toast.makeText ( this, "Requesting permission...", Toast.LENGTH_SHORT ).show ();
        }
    }

    private boolean checkPermission () {
        int result = ContextCompat.checkSelfPermission ( getApplicationContext (), SEND_SMS );
        int result1 = ContextCompat.checkSelfPermission ( getApplicationContext (), CALL_PHONE );
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission () {
        ActivityCompat.requestPermissions ( this, new String[]{SEND_SMS , CALL_PHONE}, PERMISSION_REQUEST_CODE );
    }

    private void showMessageOKCancel (String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder ( context )
                .setMessage ( message )
                .setPositiveButton ( "OK", okListener )
                .setNegativeButton ( "Cancel", null )
                .create ()
                .show ();
    }

    @Override
    protected void setViewsValues () {

    }

    @Override
    public void onRequestPermissionsResult (int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if ( grantResults.length > 0 ) {
                    boolean readContactsAccepted = grantResults[ 0 ] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[ 1 ] == PackageManager.PERMISSION_GRANTED;
                    if ( readContactsAccepted && writeStorageAccepted ) {

                        Toast.makeText ( this, "Permission is Granted ", Toast.LENGTH_LONG ).show ();

                    } else {

                        Toast.makeText ( this, "Oops!you denied permission,We are sorry ,you can't access all features of this app.", Toast.LENGTH_LONG ).show ();

                        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
                            if ( shouldShowRequestPermissionRationale ( SEND_SMS ) ) {
                                showMessageOKCancel ( "You need to allow access to both the permissions",
                                        (dialog, which) -> {
                                            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
                                                requestPermissions ( new String[]{SEND_SMS , CALL_PHONE}, PERMISSION_REQUEST_CODE );
                                            }
                                        } );
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }

    @Override
    protected void initObjects () {
        preffs = new Preffs ( context );
        toasty = new SeeTastyToast ( this );
    }
}
