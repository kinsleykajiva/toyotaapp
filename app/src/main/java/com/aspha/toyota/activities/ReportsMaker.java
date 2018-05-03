package com.aspha.toyota.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aspha.toyota.R;
import com.aspha.toyota.fragments.ReportsVehiclesFragment;

public class ReportsMaker extends AppCompatActivity {
    private Context context = ReportsMaker.this;
    private FragmentManager fragmentManager =null;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_reports_maker );
        fragmentManager = getSupportFragmentManager();
        switchFragments( new ReportsVehiclesFragment () , "home");
    }
    private void switchFragments(final Fragment fragment , final String id ){

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frame_container, fragment , id);
        ft.addToBackStack(null);
        ft.commit();
    }

}
