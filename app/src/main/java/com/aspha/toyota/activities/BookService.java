package com.aspha.toyota.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aspha.toyota.DBAccess.DBGarage;
import com.aspha.toyota.DBAccess.DBProfile;
import com.aspha.toyota.DBAccess.Preffs;
import com.aspha.toyota.R;
import com.aspha.toyota.mMessages.SeeTastyToast;

import io.realm.Realm;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.aspha.toyota.DBAccess.CRUD.saveBooking;

public class BookService extends BaseActivity {
    private SeeTastyToast toasty;
    private Preffs preffs;
    private Context context = BookService.this;
    private Realm realm;
    private DBGarage result;
    private DBProfile profile;
    private int intentID;
    private Button btnBook;
    private String message;
    private String regNumber;
    private static final String TOYOTA_LINK="+263772142207";
    public static final int PERMISSION_REQUEST_CODE=100;
    private TextView infoV;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        initObjects ();
        setContentView ( R.layout.activity_book_service );
        initViews ();
        initListerners ();
        setViewsValues ();
    }

    @Override
    protected void initViews () {
        getSupportActionBar ().setTitle ( "Vehicle Details" );
        getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );
        btnBook = findViewById ( R.id.btnBook );
        infoV = findViewById ( R.id.infoV );
    }

    @Override
    protected void initListerners () {
        btnBook.setOnClickListener ( ev->{
            initPermissions();
            SendSmsBtn();

        } );
    }

    private void SendSmsBtn(){
        try {
            SmsManager smsManager = SmsManager.getDefault();

            smsManager.sendTextMessage("0732446872", null,message, null, null);
            saveBooking(preffs.getUSER_EMAIL () ,regNumber );
            toasty.ToastSuccess ( "Booked. Message Sent." );
            clearAllActivities( context , MainActivity.class );

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            toasty.ToastError ( "Message not Sent." );
            ex.printStackTrace();
            Log.e("smsError",""+ex);
        }
    }
    private void initPermissions () {
        if(!checkPermission()){
            requestPermission();
            Toast.makeText(this,"Requesting permission...", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),SEND_SMS);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),CALL_PHONE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{SEND_SMS,CALL_PHONE},PERMISSION_REQUEST_CODE);
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    @Override
    protected void setViewsValues () {
        regNumber = result.getRegNumber ();
        message = "I " + profile.getName () + " " + profile.getSurname ()
                +"request a service for vehicle "+ result.getModelName ()
                + " Registration Number " + result.getRegNumber () + " \n"
                + "Phone:"+profile.getCellNumber () +"\n"
                + "Email:"+ profile.getEmail ()
                + "\n\n"
                +"General Repair";
        infoV.setText ( message );
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean readContactsAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (readContactsAccepted && writeStorageAccepted) {

                        Toast.makeText(this, "Permission is Granted ", Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(this,"Oops!you denied permission,We are sorry ,you can't access all features of this app.",Toast.LENGTH_LONG).show();

                        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(SEND_SMS)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        (dialog, which) -> {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{SEND_SMS,CALL_PHONE},PERMISSION_REQUEST_CODE);
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
    protected void onDestroy () {
        super.onDestroy ();
        if ( realm != null ) {

            realm.close ();
        }
    }

    @Override
    protected void initObjects () {
        preffs = new Preffs ( context );
        toasty = new SeeTastyToast ( this );

        intentID = getIntent().getIntExtra ( "id_" ,7);
        realm = Realm.getDefaultInstance ();
        result = realm.where ( DBGarage.class ).equalTo ( "ID", intentID ).findFirst ();
        profile = realm.where ( DBProfile.class ).equalTo ( "email", result.getEmail () ).findFirst ();

    }
}
