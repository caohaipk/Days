package com.wordpress.grayfaces.days.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Base64;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wordpress.grayfaces.days.R;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Project Days
 * Created by pcquy on 3/25/2017.
 */

class BSCFunction {
    private static final String SERVER_API="http://112.78.1.209:9001/DataService.svc/";
    public static int STORE=0;
    public static  int ID_LICH=0;
    public static  int NGUOI_DUNG=0;
    public static  int NHOM_NGUOI_DUNG=0;
    public static int GET= Request.Method.GET;
    public static int POST= Request.Method.POST;
    public static boolean IsShowLog=true;
    public static final String serverKey="AIzaSyCTNYPSZiAW3XsLM1Rscg1-n7a4bfMNggU";
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
    public  void requestJSON(int method, Context context, String url,
                             Response.Listener<JSONObject> listenerResponse, Response.ErrorListener listenerError, String sKey) {
        final  String key=sKey;
        JsonObjectRequest jsonRequest = new JsonObjectRequest(method, SERVER_API+url, null, listenerResponse,
                listenerError){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json;odata=verbose");
                headers.put("Authorization", key);
                return headers;
            };
        };
        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public  void getJSONObject(int method, Context context, String url, String sKey,final VolleyCallback callback,String action) {
        final  String key=sKey,ac=action;
        final JSONObject jsonObject=null;
        JsonObjectRequest jsonRequest = new JsonObjectRequest(method, SERVER_API+url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response,ac);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse==null){
                    callback.onErrorResponse(0,getHTTPError(0),ac);
                }else{
                    callback.onErrorResponse(error.networkResponse.statusCode,getHTTPError(error.networkResponse.statusCode),ac);
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json;odata=verbose");
                headers.put("Authorization", key);
                return headers;
            };
        };
        Volley.newRequestQueue(context).add(jsonRequest);
    }
    public interface VolleyCallback{
        void onSuccess(JSONObject jsonObject, String action);
        void onErrorResponse(int statusCode, String error, String action);
    }
    public  String URL_Encode(String text){
        try{
            text= URLEncoder.encode("'"+text+"'", "UTF-8");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  text;
    }
    private String getHTTPError(int Status){
        switch (Status){
            case 0: return  "Could not connect to server!";
            case 400: return  "400 Bad Request!";
            case 401: return  "401 Unauthorized!";
            case 403: return  "403 Forbidden!";
            case 404: return  "404 Not Found!";
            case 405: return  "405 Method Not Allowed!";
            case 408: return  "408 Request Timeout!";
            case 413: return  "413 Payload Too Large!";
            case 414: return  "414 URI Too Long!";
            case 415: return  "415 Unsupported Media Type!";
            case 500: return  "500 Internal Server Error!";
            case 501: return  "501 Not Implemented!";
            case 502: return  "502 Bad Gateway!";
            case 503: return  "503 Service Unavailable!";
            case 504: return  "504 Gateway Timeout!";
            default: return  Status+" Unknown Error!";
        }
    }
    public void hideStatusbar(Activity activity) {
        // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.colorPrimaryDark));
        }
    }
    public Date formatJsonDate(String date){
        try{
            return new Date(Long.parseLong(date.replace("/Date(", "").replace(")/", "")));
        }catch (Exception ex){
           return  null;
        }
    }
    public  String convertDateToString(Date date,String format){
        try{
            SimpleDateFormat formatter = new SimpleDateFormat(format);
             return  formatter.format(date);
        }catch (Exception ex){
            return  "";
        }
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show,final View mPanel,final View mProgressView,Activity activity) {
        try{
            int shortAnimTime = activity.getResources().getInteger(android.R.integer.config_shortAnimTime);
            mPanel.setVisibility(show ? View.GONE : View.VISIBLE);
            mPanel.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mPanel.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        }catch (Exception ex){

        }
    }
    //fortmat theo dinh dang ###,###.## (123456.789=>123,456.79)
    public String customFormat(double value ) {
        DecimalFormat myFormatter = new DecimalFormat("###,###.##");
        String output = myFormatter.format(value);
        return output;
    }
    public String customFormat(int value ) {
        DecimalFormat myFormatter = new DecimalFormat("###,###");
        String output = myFormatter.format(value);
        return output;
    }
}