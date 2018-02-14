package com.aspha.toyota.DBAccess;

import io.realm.RealmObject;

/**
 * Created by Kajiva Kinsley on 1/15/2018.
 */

public class DBUsers extends RealmObject {

    public DBUsers () { }

    private String email , password;

    private int ID;

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public int getID () {
        return ID;
    }

    public void setID (int ID) {
        this.ID = ID;
    }
}
