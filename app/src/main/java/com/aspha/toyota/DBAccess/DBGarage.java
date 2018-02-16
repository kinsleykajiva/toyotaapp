package com.aspha.toyota.DBAccess;

import io.realm.RealmObject;

/**
 * Created by Kajiva Kinsley on 2/5/2018.
 */

public class DBGarage  extends RealmObject{
    private int ID;
    private String modelName;
    private String RegNumber;
    private String email;
    private String chasisNumber , engineNumber , mileage;
    private String date_;


    public String getChasisNumber () {
        return chasisNumber;
    }

    public void setChasisNumber (String chasisNumber) {
        this.chasisNumber = chasisNumber;
    }

    public String getEngineNumber () {
        return engineNumber;
    }

    public void setEngineNumber (String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getMileage () {
        return mileage;
    }

    public void setMileage (String mileage) {
        this.mileage = mileage;
    }

    public String getDate_ () {
        return date_;
    }

    public void setDate_ (String date_) {
        this.date_ = date_;
    }



    public int getID () {
        return ID;
    }

    public void setID (int ID) {
        this.ID = ID;
    }

    public String getModelName () {
        return modelName;
    }

    public void setModelName (String modelName) {
        this.modelName = modelName;
    }

    public String getRegNumber () {
        return RegNumber;
    }

    public void setRegNumber (String regNumber) {
        RegNumber = regNumber;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public DBGarage () {  }
}
