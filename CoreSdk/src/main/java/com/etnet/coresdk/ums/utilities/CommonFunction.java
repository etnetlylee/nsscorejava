package com.etnet.coresdk.ums.utilities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Random;

public class CommonFunction {
    public static final String ENCODING = "UTF-8";
    private static final String TAG = "CommonFunction";
    public static Random randomGenerator = new Random();
    public static final String statusCodeSuccess = "200";

    public static int toInteger(String s){
        return toInteger(s,0);
    }
    public static int toInteger(String s, int defInt){
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e){
            return defInt;
        }
    }

    public static String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdf.format(timestamp);
    }

}
