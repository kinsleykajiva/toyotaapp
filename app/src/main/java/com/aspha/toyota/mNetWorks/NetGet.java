package com.aspha.toyota.mNetWorks;

import android.util.Log;

import com.aspha.toyota.DBAccess.DBEvents;
import com.aspha.toyota.DBAccess.DBProfile;
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
    //http://rdsol.co.zw/aspha/silentscripts/saveUsers.php?email=wat@mail.com&password=qwerty
    public static final String MAIN_URL = "http://rdsol.co.zw/aspha/";

    public static String saveToken (String token) {
        String Rs = "";
        OkHttpClient client = new OkHttpClient ();
        JSONObject json;

        Request request = new Request.Builder ().url ( MAIN_URL + "savetoken.php?token=" + token ).build ();
        try {
            Response response = client.newCall ( request ).execute ();
            if ( response.isSuccessful () ) {

                String mess = response.body ().string ();

                json = (new JSONObject ( mess ));
                Rs = json.getString ( "message" );
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace ();
        }
        return Rs;
    }

    public static String requestParts (String message) {
        String Rs = "";
        OkHttpClient client = new OkHttpClient ();
        JSONObject json;

        Request request = new Request.Builder ().url ( MAIN_URL + "enquireparts.php?" + message ).build ();
        try {
            Response response = client.newCall ( request ).execute ();
            if ( response.isSuccessful () ) {
                String mess = response.body ().string ();
                json = (new JSONObject ( mess ));
                Rs = json.getString ( "message" );
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace ();
        }
        return Rs;
    }
    
    public static String addVehicleNet(String modelName, String RegNumber , String email 
    , String chasisNumber,String engineNumber , String mileage) {
        String Rs = "";
        OkHttpClient client = new OkHttpClient();
        JSONObject json;
       
        Request request = new Request.Builder()
                .url(MAIN_URL + "/silentscripts/addVehicle.php?modelName=" + modelName 
                + "&RegNumber=" + RegNumber
                        + "&email=" + email
                        + "&chasisNumber=" + chasisNumber
                        + "&engineNumber=" + engineNumber
                        + "&mileage=" + mileage
                ).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String mess = response.body().string();

                json = (new JSONObject(mess));
                Rs = json.getString("message");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return Rs;
    }
    public static String loginNet(String email, String password) {
        String Rs = "";
        OkHttpClient client = new OkHttpClient();
        JSONObject json;

        Request request = new Request.Builder()
                .url(MAIN_URL + "/silentscripts/loginusers.php?email=" + email + "&password=" + password).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String mess = response.body().string();

                json = (new JSONObject(mess));
                Rs = json.getString("message");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return Rs;
    }
    public static String registerNet (String email , String password) {
        String Rs = "";
        OkHttpClient client = new OkHttpClient ();
        JSONObject json;

        Request request = new Request.Builder ()
                .url ( MAIN_URL + "/silentscripts/saveUsers.php?email=" + email + "&password="+password )
                .build ();
        try {
            Response response = client.newCall ( request ).execute ();
            if ( response.isSuccessful () ) {
                String mess = response.body ().string ();
              
                json = (new JSONObject ( mess ));
                Rs = json.getString ( "message" );
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace ();
        }
        return Rs;
    }
public static String saveProfileNET(String fullname, String surname, String celle, String email, String address) {
    String Rs = "";
    OkHttpClient client = new OkHttpClient ();
    JSONObject json;

    Request request = new Request.Builder ().url ( MAIN_URL + "silentscripts/DBProfile.php?name=" + fullname
            + "&surname="+surname +"&cellNumber="+celle
    + "&email="+email + "&address="+address).build ();// http://rdsol.co.zw/afm/
    try {
        Response response = client.newCall ( request ).execute ();
        if ( response.isSuccessful () ) {
            String mess = response.body ().string ();
            json = (new JSONObject ( mess ));
            Rs = json.getString ( "message" );
        }
    } catch (IOException | JSONException e) {
        e.printStackTrace ();
    }
    return Rs;
}
    public static String bookeService (String message) {
        String Rs = "";
        OkHttpClient client = new OkHttpClient ();
        JSONObject json;

        Request request = new Request.Builder ().url ( MAIN_URL + "bookservice.php?" + message ).build ();
        try {
            Response response = client.newCall ( request ).execute ();
            if ( response.isSuccessful () ) {
                String mess = response.body ().string ();
                json = (new JSONObject ( mess ));
                Rs = json.getString ( "message" );
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace ();
        }
        return Rs;
    }
public static List < DBEvents > fetchAllEvents () {
        List < DBEvents > op = null;
        try {

            Response response = new OkHttpClient ().newCall (
                    new Request.Builder ().url (
                            MAIN_URL + "silentscripts/getevents.php" )
                            .build ()
            ).execute ();
            if ( response.isSuccessful () ) {
                String responseString = response.body ().string ();


                Gson gson = new Gson ();
                Type collection = new TypeToken < List < DBEvents > > () {
                }.getType ();
                op = gson.fromJson ( responseString, collection );
            }
        } catch (IOException e) {
            e.printStackTrace ();

        }
        return op == null ? Collections.emptyList () : op;
    }
    public static List < DBEvents > fetchEvent (String id) {
        List < DBEvents > op = null;

        try {

            Response response = new OkHttpClient ().newCall (
                    new Request.Builder ().url (
                            MAIN_URL + "notificationRead.php?id=" + id )
                            .build ()
            ).execute ();
            if ( response.isSuccessful () ) {
                String responseString = response.body ().string ();


                Gson gson = new Gson ();
                Type collection = new TypeToken < List < DBEvents > > () {
                }.getType ();
                op = gson.fromJson ( responseString, collection );
            }
        } catch (IOException e) {
            e.printStackTrace ();

        }
        return op == null ? Collections.emptyList () : op;
    }
}
