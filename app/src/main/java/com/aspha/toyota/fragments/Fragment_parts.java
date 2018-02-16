package com.aspha.toyota.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aspha.toyota.DBAccess.DBGarage;
import com.aspha.toyota.DBAccess.DBProfile;
import com.aspha.toyota.DBAccess.Preffs;
import com.aspha.toyota.R;
import com.aspha.toyota.mMessages.NifftyDialogs;
import com.aspha.toyota.mMessages.SeeTastyToast;

import io.realm.Realm;

import static com.aspha.toyota.activities.RequestService.id_i;
import static com.aspha.toyota.mNetWorks.NetGet.requestParts;

/**
 * Created by Kajiva Kinsley on 2/13/2018.
 */

public class Fragment_parts extends Fragment {
    private View layout;
    private ProgressBar topProgressBar, downProgressBar;
    private ProgressDialog progressDialog;
    private DBGarage resultsGarage;
    private TextView info;
    private DBProfile resultsProfile;
    private Button btn_book;
    private EditText parts;
    private Realm realm;
    private Preffs preffs;
    private int intentID;
    private SeeTastyToast toasty;
    private String message = "";
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate( R.layout.fragment_parts, container, false);
        initViews();
        setViewsValues();
        initListerners();


        return layout;
    }

    private void initListerners () {
        btn_book.setOnClickListener ( ev -> {
        message = "name=" + resultsProfile.getName () + "&surname=" + resultsProfile.getSurname ()
                + "&parts=" + parts.getText ().toString ().trim ()
                + "&model=" + resultsGarage.getModelName () + "&regNumber=" + resultsGarage.getRegNumber ()
                +   "&email=" + resultsProfile.getEmail ()
                + "&cell=" + resultsProfile.getCellNumber ();
        showProgressDialog ( true );
        new MakeBooking ().execute ( message );
        } );
    }

    private void setViewsValues () {

        info.setText ( " I " + resultsProfile.getName () + " " + resultsProfile.getSurname ()
                +" am requesting information on hardware parts for my vehicle " + resultsGarage.getModelName ()
                 + " with registration Number "+resultsGarage.getRegNumber ()+"\n\n"
        );
    }
    private class MakeBooking extends AsyncTask< String, Void, String > {

        @Override
        protected String doInBackground (String... strings) {
            return requestParts(strings[0]);
        }

        @Override
        protected void onPostExecute (String s) {
            super.onPostExecute ( s );
            showProgressDialog ( false );

            if ( s.equals ( "successful" ) ) {
                toasty.ToastSuccess ( "Parts Enquiry sent" );
                getActivity ().onBackPressed ();
                } else {
                new NifftyDialogs ( getContext () ).messageOkError ( "Sending Error", "Poor Network,Try again!" );
            }

        }
    }
    private void initViews () {
        btn_book = layout.findViewById ( R.id.btn_book );
        parts = layout.findViewById ( R.id.parts );
        info  = layout.findViewById ( R.id.info );
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
    public void onDestroy () {
        super.onDestroy ();
        if ( realm != null ) {
            realm.close ();
        }
    }
}
