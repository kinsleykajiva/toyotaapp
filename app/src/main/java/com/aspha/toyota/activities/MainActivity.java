package com.aspha.toyota.activities;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.aspha.toyota.DBAccess.Preffs;
import com.aspha.toyota.R;
import com.aspha.toyota.mServices.MyJobService;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.SEND_SMS;
import static com.aspha.toyota.activities.BookService.PERMISSION_REQUEST_CODE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private MenuItem nav_log, nav_profile;
    private Context context = MainActivity.this;
    private Preffs preffs;
    private Boolean exit = false;
    private Menu menu;
    private Button btnBookService, btnShowRoom, btnShowWebsite, btnShowDelears, btnCustomerCare, btnRecovery ,btnAfterSales;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        initObjects ();
        setContentView ( R.layout.activity_main );
        initViews ();
        clicks ();
    }
    private void initPermissions () {
        if(!checkPermission()){
            requestPermission();
            Toast.makeText(this,"Requesting permission...", Toast.LENGTH_SHORT).show();
        }
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

                        Toast.makeText(this,"Oops!you denied permission.",Toast.LENGTH_LONG).show();

                        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CALL_PHONE)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        (dialog, which) -> {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{CALL_PHONE,SEND_SMS},PERMISSION_REQUEST_CODE);
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

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),CALL_PHONE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),SEND_SMS);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CALL_PHONE ,SEND_SMS},PERMISSION_REQUEST_CODE);
    }
    private void initObjects () {
        preffs = new Preffs ( context );


        if ( ! preffs.checkIfLoggedIn () ) {
            startActivity ( new Intent ( context, LogIn.class ) );
            finish ();
            return;
        }
    }

    private void clicks () {
        btnAfterSales.setOnClickListener ( ev->{
            startActivity ( new Intent ( context, AfterSales.class ) );
        } );
        btnBookService.setOnClickListener ( ev -> {
            if ( preffs.checkIfLoggedIn () ) {
                startActivity ( new Intent ( context, GarageList.class ) );
            } else {
                startActivity ( new Intent ( context, BookServiceUN.class ) );
            }
        } );
        btnShowRoom.setOnClickListener ( ev -> {
            startActivity ( new Intent ( context, ShowRoom.class ) );
        } );

        btnShowWebsite.setOnClickListener ( ev -> {
            startActivity ( new Intent ( context, Website.class ) );
        } );
        btnShowDelears.setOnClickListener ( ev -> {
            startActivity ( new Intent ( context, MapsActivity.class ) );
        } );
        btnCustomerCare.setOnClickListener ( ev -> {
            initPermissions ();
            Intent callIntent = new Intent(Intent.ACTION_CALL);

            callIntent.setData( Uri.parse("tel:737531075"));//change the number

            startActivity(callIntent);

        } );
        btnRecovery.setOnClickListener ( ev -> {
            initPermissions ();
            Intent callIntent = new Intent(Intent.ACTION_CALL);

            callIntent.setData( Uri.parse("tel:737531075"));//change the number

            startActivity(callIntent);
        } );


    }


    private void initViews () {
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );
        drawer = findViewById ( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener ( toggle );
        toggle.syncState ();

        NavigationView navigationView = findViewById ( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener ( this );
        btnBookService = findViewById ( R.id.btnBookService );
        btnShowRoom = findViewById ( R.id.btnShowRoom );
        btnShowWebsite = findViewById ( R.id.btnShowWebsite );
        btnShowDelears = findViewById ( R.id.btnShowDelears );
        btnCustomerCare = findViewById ( R.id.btnCustomerCare );
        btnRecovery = findViewById ( R.id.btnRecovery );
        btnAfterSales = findViewById ( R.id.btnAfterSales );

        menu = navigationView.getMenu ();
        nav_log = menu.findItem ( R.id.nav_log );
        nav_profile = menu.findItem ( R.id.nav_profile );
        nav_profile.setVisible ( preffs.checkIfLoggedIn () );
        nav_log.setTitle ( preffs.checkIfLoggedIn () ? "Log Out" : "Log In" );


        btnBookService.setText ( preffs.checkIfLoggedIn () ? "My Garage" : "Book A Service" );


    }

    @Override
    public void onBackPressed () {
        if ( drawer == null ) {
            drawer = findViewById ( R.id.drawer_layout );
        }

        if ( drawer.isDrawerOpen ( GravityCompat.START ) ) {
            drawer.closeDrawer ( GravityCompat.START );
        } else {
            if ( exit ) {
                finish (); // finish activity
            } else {
                Toast.makeText ( this, "Press Back again to Exit.", Toast.LENGTH_SHORT ).show ();
                exit = true;
                new Handler ().postDelayed ( () -> exit = false, 3 * 1000 );
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId ();

        if ( id == R.id.nav_log ) {
            if ( ! preffs.checkIfLoggedIn () ) {
                startActivity ( new Intent ( this, Log.class ) );
            } else {
                preffs.setLogStatus ( false );
                preffs.setUSER_EMAIL ( "" );
                preffs.setUSER_NAME ( "" );
                startActivity ( new Intent ( context, LogIn.class ) );
                finish ();
            }

        }
        if ( id == R.id.nav_profile ) {
            startActivity ( new Intent ( context, ViewProfile.class ) );
        }
        if ( id == R.id.nav_events ) {
            startActivity ( new Intent ( context, EventsList.class ) );
        }
        if ( drawer == null ) {
            drawer = findViewById ( R.id.drawer_layout );
        }
        drawer.closeDrawer ( GravityCompat.START );
        return true;
    }
}
