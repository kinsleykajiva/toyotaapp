package com.aspha.toyota.mBuildConfigs;

/**
 * Created by Kajiva Kinsley on 12/11/2017.
 */

import java.text.DecimalFormat;
import java.util.UUID;

/**
 * This will hold all constants variables for final references.<br>If a constant is not found
 * then check the <b> string </b> resource xml file .
 */
public final class Cons {
    /**
     * Official Link.
     */
    private static final String MAIN_URL = "https://www.com/";

    private static final String SILENT_SCRIPTS = MAIN_URL + "silentscripts/";

    public static final String USER_PROFILE = SILENT_SCRIPTS + "user.php";
    public static final String USER_SIGNUP = SILENT_SCRIPTS + "signupUser.php";
    public static final String USER_SIGNIN = SILENT_SCRIPTS + "signinUser.php";

    public static String [] serviceType = {"MINOR" ,
            "MIDDLE" ,"MAJOR" };

    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
/**
 * Constants for a System Definations
 * */
    public static final class Conditions {
        public Conditions () {
            throw new RuntimeException ( "Can not create an object or an instance of this class" );
        }

        public static final String STATE = "state";
        public static final String CONNECTION_ERROR = "Connection-Error";
        public static final String GET_ALL = "g";
    }

    /**
     *This is keep all the device data constants
     * */
    public static final class Instruments {

        public static final String BLUETOOTH_MODULE_MAC_ADDRESS = "98:D3:35:70:B2:5F";
        public static final UUID BLUETOOTH_MODULE_UUID = UUID.fromString ( "00001101-0000-1000-8000-00805F9B34FC" );

    }
    public static double calculateMonthlyPayment(double loanAmount,  double interestRate ,int termInMonths) {

        // Convert interest rate into a decimal
        // eg. 6.5% = 0.065

        interestRate /= 100.0;

        // Monthly interest rate
        // is the yearly rate divided by 12

        double monthlyRate = interestRate / 12.0;



        // Calculate the monthly payment
        // Typically this formula is provided so
        // we won't go into the details

        // The Math.pow() method is used calculate values raised to a power

        double monthlyPayment =
                (loanAmount*monthlyRate) /
                        (1-Math.pow(1+monthlyRate, -termInMonths));

        double res=Math.round(monthlyPayment * 100.0)/100.0;

        return res;
    }
public  static String [] consumer = new String[15];
    public  static String [] student = new String[12];
    public static double interestCalCulation (double principal , double rate , double time){
        return  principal * rate * time;
    }

   /* Intent intent = new Intent(getApplicationContext(), Home.class);
intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);*/
}
