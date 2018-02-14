package com.aspha.toyota.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.aspha.toyota.R;
import com.aspha.toyota.fragments.Fragements_services;
import com.aspha.toyota.fragments.Fragment_parts;

public class AfterSales extends BaseActivity {
    private ViewPager pager;
    private TabLayout Tabslayout;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        initObjects ();
        setContentView ( R.layout.activity_after_sales );
        initViews ();
        setViewsValues ();
        initListerners ();
    }

    @Override
    protected void initViews () {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("After Sales");
        pager = findViewById(R.id.viewpager);
        pager.setVisibility( View.VISIBLE);
        Tabslayout= findViewById(R.id.tablayout);

    }

    @Override
    protected void initListerners () {

    }

    @Override
    protected void setViewsValues () {
        Tabslayout.addTab(Tabslayout.newTab().setText("Services"));
        Tabslayout.addTab(Tabslayout.newTab().setText("Parts"));
        pager.setAdapter(new FragmentPagerAdapter (getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if(position==0){
                    return new Fragements_services ();
                }else{
                    return new Fragment_parts ();
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
    }

    @Override
    protected void initObjects () {

    }
}

// service -pick a date, minor, middle ,major
// puts text box for comment in service

// parts ---- request parts put names to email