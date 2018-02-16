package com.aspha.toyota.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aspha.toyota.DBAccess.DBGarage;
import com.aspha.toyota.DBAccess.DBProfile;
import com.aspha.toyota.DBAccess.DBService;
import com.aspha.toyota.DBAccess.Preffs;
import com.aspha.toyota.R;
import com.aspha.toyota.activities.MainActivity;
import com.aspha.toyota.mMessages.NifftyDialogs;
import com.aspha.toyota.mMessages.SeeTastyToast;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.aspha.toyota.DBAccess.CRUD.saveDBService;
import static com.aspha.toyota.activities.RequestService.id_i;
import static com.aspha.toyota.mBuildConfigs.Cons.serviceType;
import static com.aspha.toyota.mNetWorks.NetGet.bookeService;

/**
 * Created by Kajiva Kinsley on 2/14/2018.
 */

public class Fragment_BookService extends Fragment implements
        AdapterView.OnItemSelectedListener {
    private View layout;
    private Button btnDatePicker, btn_book;
    private EditText txtDate, comment;
    private String serviceSlection;
    private int mYear, mMonth, mDay;
    private int intentID;
    private ProgressBar topProgressBar, downProgressBar;
    private ProgressDialog progressDialog;
    private TextView txtinfo;
    private Realm realm;
    private DBGarage resultsGarage;
    private DBProfile resultsProfile;
    private Preffs preffs;
    private SeeTastyToast toasty;
    private String message = "";
    private RealmResults < DBService > resultsGarage1;

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        realm = Realm.getDefaultInstance ();
        toasty = new SeeTastyToast ( getContext () );
        preffs = new Preffs ( getContext () );
        intentID = id_i;
        resultsProfile = realm.where ( DBProfile.class ).equalTo ( "email", preffs.getUSER_EMAIL () ).findFirst ();
        resultsGarage = realm.where ( DBGarage.class ).equalTo ( "ID", intentID ).findFirst ();
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate ( R.layout.fragment_bookservice, container, false );
        initViews ();
        setViewsValues ();
        initListerners ();


        return layout;
    }

    private void setViewsValues () {
        txtinfo.setText ( "I " + resultsProfile.getName () + " " + resultsProfile.getSurname ()
                + " am requesting a service for my vehicle " + resultsGarage.getModelName () + "with registration Number "
                + resultsGarage.getRegNumber () );
    }

    @Override
    public void onDestroy () {
        super.onDestroy ();
        if ( realm != null ) {
            realm.close ();
        }
    }

    private void initListerners () {
        btnDatePicker.setOnClickListener ( ev -> {
// Get Current Date
            final Calendar c = Calendar.getInstance ();
            mYear = c.get ( Calendar.YEAR );
            mMonth = c.get ( Calendar.MONTH );
            mDay = c.get ( Calendar.DAY_OF_MONTH );
            DatePickerDialog datePickerDialog = new DatePickerDialog ( getContext (),
                    (view, year, monthOfYear, dayOfMonth) ->
                            txtDate.setText ( dayOfMonth + "-" + (monthOfYear + 1) + "-" + year ), mYear, mMonth, mDay );
            datePickerDialog.show ();
        } );
        btn_book.setOnClickListener ( ev -> {
            if ( txtDate.getText ().toString ().trim ().isEmpty () ) {
                toasty.ToastError ( "Please Select Date" );
                return;
            }
            if ( comment.getText ().toString ().trim ().isEmpty () ) {
                toasty.ToastError ( "Please put Comment" );
                return;
            }

            message = "name=" + resultsProfile.getName () + "&surname=" + resultsProfile.getSurname ()
                    + "&comment=" + comment.getText ().toString ().trim ()
                    + "&model=" + resultsGarage.getModelName () + "&regNumber=" + resultsGarage.getRegNumber ()
                    + "&date=" + txtDate.getText () + "&service=" + serviceSlection + "&email=" + resultsProfile.getEmail ()
                    + "&cell=" + resultsProfile.getCellNumber ();
            showProgressDialog ( true );
            new MakeBooking ().execute ( message );


        } );
    }

    private  class MakeBooking extends AsyncTask < String, Void, String > {

        @Override
        protected String doInBackground (String... strings) {
            return bookeService ( strings[ 0 ] );
        }

        @Override
        protected void onPostExecute (String s) {
            super.onPostExecute ( s );
            showProgressDialog ( false );

            if ( s.equals ( "successful" ) ) {
                toasty.ToastSuccess ( "Service Booked" );
                saveDBService(preffs.getUSER_EMAIL () ,resultsGarage.getModelName (),
                        comment.getText ().toString ().trim () ,txtDate.getText ().toString ().trim () ,
                        serviceSlection );
                //startActivity ( new Intent ( getContext (), MainActivity.class ) );
                getActivity ().onBackPressed ();
            } else {
                new NifftyDialogs ( getContext () ).messageOkError ( "Sending Error", "Poor Network,Try again!" );
            }

        }
    }

    private void initViews () {
        btnDatePicker = layout.findViewById ( R.id.btn_date );
        btn_book = layout.findViewById ( R.id.btn_book );
        txtDate = layout.findViewById ( R.id.in_date );
        comment = layout.findViewById ( R.id.comment );
        txtinfo = layout.findViewById ( R.id.txtinfo );
        Spinner spin = layout.findViewById ( R.id.servicetype_spinner );
        spin.setOnItemSelectedListener ( this );


        ArrayAdapter aa = new ArrayAdapter ( getContext (), android.R.layout.simple_spinner_item, serviceType );
        aa.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
        spin.setAdapter ( aa );
        topProgressBar = layout.findViewById ( R.id.top_progressBar );
        downProgressBar = layout.findViewById ( R.id.down_progressBar );
        if ( Build.VERSION.SDK_INT < 26 ) {
            progressDialog = new ProgressDialog ( getContext () );
        } else {
            topProgressBar.setIndeterminate ( true );
            downProgressBar.setIndeterminate ( true );
        }
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
                getActivity ().getWindow ().setFlags ( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE );
            } else {
                topProgressBar.setVisibility ( View.GONE );
                downProgressBar.setVisibility ( View.GONE );
                getActivity ().getWindow ().clearFlags ( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE );
            }
        }
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected (AdapterView < ? > arg0, View arg1, int position, long id) {
        serviceSlection = serviceType[ position ];
    }

    @Override
    public void onNothingSelected (AdapterView < ? > arg0) {
    }
}
