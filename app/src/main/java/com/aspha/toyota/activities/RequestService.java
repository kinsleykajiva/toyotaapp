package com.aspha.toyota.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aspha.toyota.DBAccess.DBGarage;
import com.aspha.toyota.R;
import com.aspha.toyota.mMessages.SeeTastyToast;

import io.realm.Realm;

public class RequestService extends BaseActivity {
    private Context context = RequestService.this;
    private Realm realm;
    private SeeTastyToast toasty;
    private DBGarage result;
    private int intentID ;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        initObjects ();
        setContentView ( R.layout.activity_request_service );
        initViews ();
        setViewsValues ();
        initListerners ();
    }

    @Override
    protected void initViews () {

    }

    @Override
    protected void initListerners () {

    }

    @Override
    protected void setViewsValues () {

    }

    @Override
    protected void initObjects () {

    }
}
