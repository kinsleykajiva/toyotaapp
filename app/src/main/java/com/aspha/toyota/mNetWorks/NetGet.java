package com.aspha.toyota.mNetWorks;

import android.util.Log;

import com.aspha.toyota.DBAccess.DBEvents;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Kajiva Kinsley on 2/13/2018.
 */
 // cvg9cJ_mv94:APA91bEeaTd-51CRQJi80pyLPxmptcVxXT0rQQ-shP4DfwOvtcKU4kjmOc3QA8oJLtOVP3aoID9QnhY8125e9QY5_FZcnbwbwp_10KSuosavcHXkCDBuOWRgwWoWpmFvEUtyFQdItV_e
public class NetGet {
    public static final String MAIN_URL = "http://www.rdsol.co.zw/aspha/";

    public static String saveToken(String token){
        String Rs="";
        OkHttpClient client = new OkHttpClient();
        JSONObject json;

        Request request = new Request.Builder().url(MAIN_URL + "savetoken.php?token="+token).build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){

                String mess = response.body().string();
                //Log.e ( "xxx", "saveToken: "+ mess  );
                json=(new JSONObject(mess));
                Rs=json.getString("message");
            }
        }catch(IOException | JSONException e) {e.printStackTrace();}
        return Rs;
    }

    public static  List<DBEvents > fetchEvent(String id){
        List<DBEvents > op = null;

        try {

            Response response = new OkHttpClient().newCall(
                    new Request.Builder().url(
                            MAIN_URL+"notificationRead.php?id="+id)
                            .build()
            ).execute();
            if(response.isSuccessful()) {
                String responseString = response.body().string();


                Gson gson = new Gson();
                Type collection = new TypeToken<List<DBEvents>>(){}.getType();
                op = gson.fromJson(responseString, collection);
            }
        } catch (IOException e){
            e.printStackTrace();

        }
        return  op == null ? Collections.emptyList () : op;
    }
}
