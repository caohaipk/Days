package com.wordpress.grayfaces.days.app;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Project Days
 * Created by pcquy on 1/9/2017.
 */

class clsClass {
    //public  static  String Server_API="http://115.75.3.135/Mobifone_API/DataService.svc/";
    public  static  String Server_API="http://210.211.125.28:8083/DataService.svc/";
    public String decodeString(String encoded) {
        byte[] dataDec = Base64.decode(encoded, Base64.DEFAULT);
        String decodedString = "";
        try {
            decodedString = new String(dataDec, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            return decodedString;
        }
    }
    public String encodeString(String s) {
        byte[] data = new byte[0];
        try {
            data = s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            String base64Encoded = Base64.encodeToString(data, Base64.DEFAULT);
            return base64Encoded;
        }
    }
}
