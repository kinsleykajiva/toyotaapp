package com.aspha.toyota.DBAccess;

import io.realm.RealmObject;

/**
 * Created by Kajiva Kinsley on 2/5/2018.
 */

public class DBProfile  extends RealmObject{
    private int ID ;
    private String name , surname , cellNumber , email , address;


    public DBProfile (String name, String surname, String cellNumber, String email, String address) {
        this.name = name;
        this.surname = surname;
        this.cellNumber = cellNumber;
        this.email = email;
        this.address = address;
    }

    public DBProfile () {
    }

    public int getID () {
        return ID;
    }

    public void setID (int ID) {
        this.ID = ID;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getSurname () {
        return surname;
    }

    public void setSurname (String surname) {
        this.surname = surname;
    }

    public String getCellNumber () {
        return cellNumber;
    }

    public void setCellNumber (String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress (String address) {
        this.address = address;
    }
}
