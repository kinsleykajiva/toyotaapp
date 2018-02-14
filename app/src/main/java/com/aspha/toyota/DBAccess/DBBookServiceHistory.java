package com.aspha.toyota.DBAccess;

import io.realm.RealmObject;

/**
 * Created by Kajiva Kinsley on 2/6/2018.
 */

public class DBBookServiceHistory extends RealmObject {

    private int ID;
    private String date_ , time, email,regNumber;


    public int getID () {
        return ID;
    }

    public void setID (int ID) {
        this.ID = ID;
    }

    public String getDate_ () {
        return date_;
    }

    public void setDate_ (String date_) {
        this.date_ = date_;
    }

    public String getTime () {
        return time;
    }

    public void setTime (String time) {
        this.time = time;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getRegNumber () {
        return regNumber;
    }

    public void setRegNumber (String regNumber) {
        this.regNumber = regNumber;
    }

    public DBBookServiceHistory () {
    }
}
