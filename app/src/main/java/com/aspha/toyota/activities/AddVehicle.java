package com.aspha.toyota.activities;

import android.app.ProgressDialog;
import android.content.Context;
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

import static com.aspha.toyota.DBAccess.CRUD.saveVehicle;
import static com.aspha.toyota.DBAccess.CRUD.vehicleExists;

public class AddVehicle extends BaseActivity {
private Context  context = AddVehicle.this;
private MyEditText modelName,RegNumber ,chasisNumber , engineNumber , mileage;;
private Button btnAdd , btnCancel;
    private SeeTastyToast toasty;
    private ProgressBar topProgressBar, downProgressBar;
    private ProgressDialog progressDialog;
    private Preffs preffs;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        initObjects ();
        setContentView ( R.layout.activity_add_vehicle );
        initViews ();
        initListerners ();
        setViewsValues ();
    }

    @Override
    protected void initViews () {
        getSupportActionBar().setTitle ( "Add Vehicle" );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        modelName = findViewById ( R.id.modelName );
        RegNumber = findViewById ( R.id.RegNumber );
        btnAdd = findViewById ( R.id.btnAdd );
        btnCancel = findViewById ( R.id.btnCancel );
        chasisNumber = findViewById ( R.id.chasisNumber );
        engineNumber = findViewById ( R.id.engineNumber );
        mileage = findViewById ( R.id.mileage );

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
        btnCancel.setOnClickListener ( er->{
            onBackPressed ();
        } );
        btnAdd.setOnClickListener ( er->{
            final String modelName_ = modelName.getText ().toString ().trim ();
            final String RegNumber_ = RegNumber.getText ().toString ().trim ();
            final String chasisNumber_ = chasisNumber.getText ().toString ().trim ();
            final String engineNumber_ = engineNumber.getText ().toString ().trim ();
            final String mileage_ = mileage.getText ().toString ().trim ();

            if ( modelName_.isEmpty () ) {
                modelName.setError ( "Model Name can't be empty" );
                return;
            }
            if ( RegNumber_.isEmpty () ) {
                RegNumber.setError ( "Registration Number can't be empty" );
                return;
            }
            if ( chasisNumber_.isEmpty () ) {
                chasisNumber.setError ( "Chasis Number can't be empty" );
                return;
            }
            if ( engineNumber_.isEmpty () ) {
                engineNumber.setError ( "Engine Number can't be empty" );
                return;
            }
            if ( mileage_.isEmpty () ) {
                mileage.setError ( "Mileage can't be empty" );
                return;
            }
            showProgressDialog(true);
            showProgressDialog(false);
            if(vehicleExists(RegNumber_)){
                new NifftyDialogs ( context ).messageOkError ( "Vehicle Exists" ,"There is a vehicle that already has that information !");
            }else{
                toasty.ToastSuccess ( "Saved" );
                saveVehicle(modelName_ , RegNumber_ , preffs.getUSER_EMAIL () ,chasisNumber_ , engineNumber_ , mileage_);
                onBackPressed ();
            }

        } );
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
    protected void setViewsValues () {

    }

    @Override
    protected void initObjects () {
        preffs = new Preffs ( context );
        toasty = new SeeTastyToast ( this );
    }
}
