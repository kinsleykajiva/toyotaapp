package com.aspha.toyota.mBuildConfigs;

/**
 * Created by Kajiva Kinsley on 12/11/2017.
 */

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


import static com.aspha.toyota.mBuildConfigs.Cons.EMAIL_PATTERN;


/**
 * This will have utility methods that are deemed useful, and all the methods are static.<br/>
 * This class can not be instanciated.
 * */
public class Utils {

    private Utils () {
        throw new RuntimeException ( "Can not create an object or an instance of this class" );
    }
    public static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^[0-9]{10}$";
        return mobile.matches(regEx);
    }

    public static boolean isvalidEmail(String email) {
        return Pattern.compile(EMAIL_PATTERN)
                .matcher(email).matches();
    }
    private static String get_device_id (Context ctx) {

        final TelephonyManager tm = (TelephonyManager) ctx.getSystemService ( Context.TELEPHONY_SERVICE );

        try {
            if ( ActivityCompat.checkSelfPermission ( ctx, Manifest.permission.READ_PHONE_STATE ) != PackageManager.PERMISSION_GRANTED ) {
                return "";
            }
            String tmDevice = "" + tm.getDeviceId ();
            String  tmSerial = "" + tm.getSimSerialNumber();
            String androidId = "" + Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID (androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            String deviceId = deviceUuid.toString();

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(deviceId.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for ( byte aByteData : byteData ) {
                sb.append ( Integer.toString ( (aByteData & 0xff) + 0x100, 16 ).substring ( 1 ) );
            }
            deviceId = sb.toString();


            return deviceId;
        } catch (Exception ignored) {
        }
        return "nodeviceid";
    }

    private static String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

    }
    /**
     * <p>
     *     get device ID .useful in identifying device
     * </p>
     * @param  context
     * @return String
     *
     * */
    public static String getAndroidDeviceID(Context context) {
        String testee = get_device_id(context);
        return testee.equalsIgnoreCase("nodeviceid") ? getAndroidID(context) : testee;
    }

    /**
     * <p>
     *     converting milli seconds to hours
     *
     * </p>
     * <p>
     *
     * </p>
     * @param
     *  @param millis
     *  @return  String
     * **/
    private String milliSectoHours(long millis) {

        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));


    }

    public static String RemoveLastChars(String string, int RemoveTheLast) {
        return string.substring(0, string.length() - RemoveTheLast);
    }
    /**
     * <p>
     *     Will only restart the app by closing everything and restart afresh hence all static variables will be reset from start
     * </p>
     * **/
    public static void doRestart(Context c) {
        try {
            //check if the context is given
            if (c != null) {
                //fetch the packagemanager so we can get the default launch activity
                // (you can replace this intent with any other activity if you want
                PackageManager pm = c.getPackageManager();
                //check if we got the PackageManager
                if (pm != null) {
                    //create the intent with the default start activity for your application
                    Intent mStartActivity = pm.getLaunchIntentForPackage(
                            c.getPackageName()
                    );
                    if (mStartActivity != null) {
                        mStartActivity.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //create a pending intent so the application is restarted after System.exit(0) was called.
                        // We use an AlarmManager to call this intent in 100ms
                        int mPendingIntentId = 223344;
                        PendingIntent mPendingIntent = PendingIntent
                                .getActivity(c, mPendingIntentId, mStartActivity,
                                        PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager mgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
                        mgr.set( AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                        //kill the application
                        System.exit(0);
                    } else {
                        Toast.makeText(c, "Please Open the  app now ", Toast.LENGTH_LONG).show();
                        // Log.e(TAG, "Was not able to restart application, mStartActivity null");
                    }
                } else {
                    Toast.makeText(c, "Please Open the  app now ", Toast.LENGTH_LONG).show();
                    //  Log.e(TAG, "Was not able to restart application, PM null");
                }
            } else {
                // Log.e(TAG, "Was not able to restart application, Context null");
                Toast.makeText(c, "Please Open the  App now ", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(c, "Please Open the  App now ", Toast.LENGTH_LONG).show();
            //Log.e(TAG, "Was not able to restart application");
        }
    }
    public static int RandomInt(int min, int max) {
        int randomNum = new Random().nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static Drawable getDrawableCustome(Context context , int iconId){
        return ContextCompat.getDrawable ( context , iconId );
    }
    /**
     * Fetch the color from the resources xml file safely.
     * */
    public static int getColorCustome(Context context , int colorId){
        return Build.VERSION.SDK_INT >= 23 ? ContextCompat.getColor ( context , colorId ) :
                context.getResources ().getColor ( colorId );
    }

    /**
     * This make the first letter of the String to a capital letter.
     * */
    public static String capitalizeFirstLetter(String string){
        return string.isEmpty () || string == null ? string : string.substring ( 0 , 1 ).toUpperCase () + string.substring ( 1 );
    }

    // 23.4#23,0|233,56|~
    public static String [] processTempEkg1(String data){

        data = RemoveLastChars ( data, 1 ); // returns 23.4#23|233|~
        data = RemoveLastChars ( data, 1 ); // returns 23.4#23|233|
        data = RemoveLastChars ( data, 1 ); // returns 23.4#23|233
        Log.e ( "xxx", "processTempEkg:"+ data  );
        String temp = data.split ( "#" )[ 0 ]; //23.4#
        String ekg = data.split ( "#" )[ 1 ]; //23|233
        String [] ekg_arr =   ekg.split("\\|"); //23|233

        List<Integer> zx = range(1 , ekg_arr.length );

        StringBuilder qw = new StringBuilder ();

        for(int  i = 0 ; i < ekg_arr.length ; i++){
            qw.append ( ekg_arr[ i ] ).append ( "," ).append ( zx.get ( i ) ).append ( "|" );
        }

        return new String[] {temp ,ekg  };

    }


    public static String [] myStringSplit(String string){
        String [] sp = string.split("\\|");
        return sp;
    }


    public static List<Integer> range(int min, int max) {
        List<Integer> list = new LinkedList ();
        for (int i = min; i <= max; i++) {
            list.add(i);
        }
        return list;
    }
}
