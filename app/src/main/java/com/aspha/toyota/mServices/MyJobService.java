package com.aspha.toyota.mServices;

/**
 * Created by Kajiva Kinsley on 2/12/2018.
 */

import android.graphics.Bitmap;
import android.util.Log;

import com.aspha.toyota.DBAccess.DBEvents;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.aspha.toyota.DBAccess.CRUD.saveEvent;
import static com.aspha.toyota.mNetWorks.NetGet.MAIN_URL;
import static com.aspha.toyota.mNetWorks.NetGet.fetchEvent;
import static com.aspha.toyota.mServices.MyFirebaseMessagingService.sendNotification;

public class MyJobService extends JobService {


    private static final String TAG = "MyJobService";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        new Thread( () -> {
            List<DBEvents> ev = fetchEvent(jobParameters.getExtras ().getString  ( "eventid" ));
            if(!ev.isEmpty ()){

                saveEvent(   ev  );



                sendNotification(MyJobService.this , ev.get ( 0 ).getTitle () , ev.get ( 0 ).getDescription () ,ev.get ( 0 ).getId ()+"");
                jobFinished(jobParameters, false);
            }
        } ).start();

        return false;



    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

}
