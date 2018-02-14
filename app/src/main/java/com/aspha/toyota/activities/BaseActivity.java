package com.aspha.toyota.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.aspha.toyota.R;

/**
 * Created by Kinsley Kajiva on 2/10/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    protected void clearAllActivities(Context context, Class clazz){
        Intent intent = new Intent(context, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    /**
     * Initialises all views to be used in you activity
     * <p>
     *
     */
    abstract protected  void initViews();
    /**
     * Initialises Listeners for views to be used in you activity
     * <p>
     *
     */
    abstract protected  void initListerners();
    /**
     * Setts views values to be used in you activity
     * <p>
     *
     */
    abstract protected  void setViewsValues();
    /**
     * Initialises all Objects or Classes to be used in you activity
     * <p>
     *
     */
    abstract protected  void initObjects();



    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }
    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition( R.anim.slide_from_left, R.anim.slide_to_right);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
