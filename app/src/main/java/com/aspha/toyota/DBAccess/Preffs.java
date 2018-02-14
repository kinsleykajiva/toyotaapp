package com.aspha.toyota.DBAccess;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kajiva Kinsley on 1/15/2018.
 */

public class Preffs {

    private Context _context;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "toyota.aspha.mPreffs";

    private String USER_NAME="u_name";
    private String loggedCheck="u_loggCheck";
    private String USER_EMAIL="u_email";
    private String FCM="fcm";
    private String FCMSaved="wassavedfcm";


    public boolean wasFCMSaved() {
        return  pref.getBoolean (this.FCMSaved,false);
    }

    public void setFCMSaved() {

        editor.putBoolean (this.FCMSaved,true);
        editor.commit();
    }
    public String getFCM() {
        return  pref.getString(this.FCM,"");
    }

    public void setFCM(String token) {

        editor.putString(this.FCM,token);
        editor.commit();
    }

    public String getUSER_EMAIL() {
        return  pref.getString(this.USER_EMAIL,"");
    }

    public void setUSER_EMAIL(String USER_EMAIL) {

        editor.putString(this.USER_EMAIL,USER_EMAIL);
        editor.commit();
    }
    public String getUSER_NAME() {

        return  pref.getString(this.USER_NAME,"");
    }

    public void setUSER_NAME(String USER_NAME) {
        editor.putString(this.USER_NAME,USER_NAME);
        editor.commit();
    }

    public boolean checkIfLoggedIn() {
        return  pref.getBoolean (this.loggedCheck,false);
    }

    public void setLogStatus(boolean state) {
        editor.putBoolean (this.loggedCheck,state);
        editor.commit();
    }


    public Preffs(Context context) {
        _context= context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }
}
