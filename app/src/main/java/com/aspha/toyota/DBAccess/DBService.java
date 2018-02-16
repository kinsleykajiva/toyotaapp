package com.aspha.toyota.DBAccess;

import io.realm.RealmObject;

/**
 * Created by Kajiva Kinsley on 2/13/2018.
 */

public class DBService extends RealmObject {

    private int id;

    private String carReg , comment , datePicked , serviceType , email;

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getCarReg () {
        return carReg;
    }

    public void setCarReg (String carReg) {
        this.carReg = carReg;
    }

    public String getComment () {
        return comment;
    }

    public void setComment (String comment) {
        this.comment = comment;
    }

    public String getDatePicked () {
        return datePicked;
    }

    public void setDatePicked (String datePicked) {
        this.datePicked = datePicked;
    }

    public String getServiceType () {
        return serviceType;
    }

    public void setServiceType (String serviceType) {
        this.serviceType = serviceType;
    }

    public DBService () {
    }
}
