package com.aspha.toyota.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.aspha.toyota.R;
import com.aspha.toyota.fragments.Fragements_services;
import com.aspha.toyota.fragments.Fragment_parts;
import com.aspha.toyota.fragments.Fragment_partsAftersales;

import java.util.ArrayList;
import java.util.List;

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
        Tabslayout= findViewById(R.id.tablayout);

    }

    @Override
    protected void initListerners () {

    }
    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<> ();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Fragements_services(), "Services");
        adapter.addFrag(new Fragment_partsAftersales(), "Parts");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem( 0 );

    }
    @Override
    protected void setViewsValues () {
        setupViewPager(pager);
        Tabslayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(Tabslayout));


    }

    @Override
    protected void initObjects () {

    }
}

// service -pick a date, minor, middle ,major
// puts text box for comment in service

// parts ---- request parts put names to email