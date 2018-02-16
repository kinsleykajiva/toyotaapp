package com.aspha.toyota.DBAccess;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.aspha.toyota.mNetWorks.NetGet.MAIN_URL;

/**
 * Created by Kajiva Kinsley on 2/5/2018.
 */

public class CRUD {
    // import java.text.SimpleDateFormat;
    //  final String date = new SimpleDateFormat ( "yyyy/MM/dd" ).format ( new Date () );
    public static void saveUserDetails (String email, String Password) {
        new Thread ( () -> {

            Realm realm = Realm.getDefaultInstance ();
            RealmResults < DBUsers > results = realm.where ( DBUsers.class ).findAll ();

            realm.beginTransaction ();
            DBUsers user = realm.createObject ( DBUsers.class );
            user.setEmail ( email );
            user.setPassword ( Password );

            user.setID (
                    (results.isEmpty () && results.size () < 1) ? 0 : (results.max ( "ID" ).intValue () + 1)
            );

            realm.commitTransaction ();
            realm.close ();
        } ).start ();
    }

    public static boolean isUserFound (String Email, String password) {
        boolean retVal = false;
        Realm realm = Realm.getDefaultInstance ();
        RealmResults < DBUsers > results = realm.where ( DBUsers.class ).equalTo ( "email", Email ).and ().equalTo ( "password", password ).findAll ();
        if ( ! results.isEmpty () ) {
            retVal = true;
        }
        realm.close ();
        return retVal;
    }

    public static void saveVehicle (String modelName, String regNumber, String user ,String chasisNumber ,
                                    String engineNumber , String mileage) {
        String date = new SimpleDateFormat ( "yyyy/MM/dd" ).format ( new Date () );
        new Thread ( () -> {

            Realm realm = Realm.getDefaultInstance ();
            RealmResults < DBGarage > results = realm.where ( DBGarage.class ).findAll ();

            realm.beginTransaction ();
            DBGarage rw = realm.createObject ( DBGarage.class );
            rw.setEmail ( user );
            rw.setDate_ ( date );
            rw.setModelName ( modelName );
            rw.setRegNumber ( regNumber );
            rw.setChasisNumber ( chasisNumber );
            rw.setEngineNumber ( engineNumber );
            rw.setMileage ( mileage );

            rw.setID (
                    (results.isEmpty () && results.size () < 1) ? 0 : (results.max ( "ID" ).intValue () + 1)
            );

            realm.commitTransaction ();
            realm.close ();
        } ).start ();
    }

    public static boolean vehicleExists (String regNumber) {
        boolean retVal = false;
        Realm realm = Realm.getDefaultInstance ();
        RealmResults < DBGarage > results = realm.where ( DBGarage.class ).equalTo ( "RegNumber", regNumber ).findAll ();
        if ( ! results.isEmpty () ) {
            retVal = true;
        }
        realm.close ();
        return retVal;
    }

    public static void saveProfile (String fullname, String surname, String celle, String email, String address) {

        new Thread ( () -> {

            Realm realm = Realm.getDefaultInstance ();
            RealmResults < DBProfile > results = realm.where ( DBProfile.class ).findAll ();

            realm.beginTransaction ();
            DBProfile rw = realm.createObject ( DBProfile.class );
            rw.setEmail ( email );
            rw.setAddress ( address );
            rw.setCellNumber ( celle );
            rw.setName ( fullname );
            rw.setSurname ( fullname );

            rw.setID (
                    (results.isEmpty () && results.size () < 1) ? 0 : (results.max ( "ID" ).intValue () + 1)
            );

            realm.commitTransaction ();
            realm.close ();
        } ).start ();
    }

    public static void saveBooking (String email, String regNumber) {
        String date = new SimpleDateFormat ( "yyyy/MM/dd" ).format ( new Date () );
        String dtime = new SimpleDateFormat ( "HH:mm:ss" ).format ( new Date () );
        new Thread ( () -> {

            Realm realm = Realm.getDefaultInstance ();
            RealmResults < DBBookServiceHistory > results = realm.where ( DBBookServiceHistory.class ).findAll ();

            realm.beginTransaction ();
            DBBookServiceHistory rw = realm.createObject ( DBBookServiceHistory.class );
            rw.setEmail ( email );
            rw.setRegNumber ( regNumber );
            rw.setDate_ ( date );
            rw.setTime ( dtime );

            rw.setID (
                    (results.isEmpty () && results.size () < 1) ? 0 : (results.max ( "ID" ).intValue () + 1)
            );

            realm.commitTransaction ();
            realm.close ();
        } ).start ();
    }

    public static void saveDBService (String email, String regNumber , String comment ,  String date , String typee) {


        new Thread ( () -> {

            Realm realm = Realm.getDefaultInstance ();
            RealmResults < DBService > results = realm.where ( DBService.class ).findAll ();

            realm.beginTransaction ();
            DBService rw = realm.createObject ( DBService.class );
            rw.setCarReg ( regNumber );
            rw.setComment ( comment );
            rw.setDatePicked ( date );
            rw.setServiceType ( typee );
            rw.setEmail ( email );


            rw.setId (
                    (results.isEmpty () && results.size () < 1) ? 0 : (results.max ( "id" ).intValue () + 1)
            );

            realm.commitTransaction ();
            realm.close ();
        } ).start ();
    }


    public static void saveEvent (List<DBEvents> ev) {

        DBEvents events = ev.get ( 0 );

            Realm realm = Realm.getDefaultInstance ();

            realm.beginTransaction ();
            DBEvents rw = realm.createObject ( DBEvents.class );
            rw.setTitle ( events.getTitle () );
            rw.setDescription ( events.getDescription ());
            rw.setPostsdate ( events.getPostsdate () );
            rw.setPosttime ( events.getPosttime () );
            rw.setImage ( MAIN_URL+"uploads/"+events.getImage () );
            rw.setId (
                    events.getId ()
            );

            realm.commitTransaction ();
            realm.close ();

    }
    public static boolean eventExists (int id) {
        boolean retVal = false;
        Realm realm = Realm.getDefaultInstance ();
        RealmResults < DBEvents > results = realm.where ( DBEvents.class ).equalTo ( "id", id ).findAll ();
        if ( ! results.isEmpty () ) {
            retVal = true;
        }
        realm.close ();
        return retVal;
    }
    public static  List<DBEvents> getEvent(int id){
        List<DBEvents> ev ;
        Realm realm = Realm.getDefaultInstance ();
        RealmResults < DBEvents > results = realm.where ( DBEvents.class ).equalTo ( "id", id ).findAll ();
        ev = realm.copyFromRealm(results);
        realm.close ();
        return ev;
    }


}
