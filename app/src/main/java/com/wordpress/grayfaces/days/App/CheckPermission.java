package com.wordpress.grayfaces.days.App;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Project MobiAsset
 * Created by Gray on 6/2/2017.
 */

public class CheckPermission {
    private static boolean underAPI23() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
    }
    /**
     * @param PERMISSIONS list permissions to check
     * @param PERMISSIONS_REQUEST_ID request permission
     * @param msg message show when at least one permission deny
     * Created by TamNinja on 6/2/2017.
     * More info : https://tamninja.wordpress.com/2016/03/17/tuthuong-dan-dang-ki-runtime-permission-trong-android-m/
     */
    public static boolean verify(Activity activity, final String[] PERMISSIONS, final int PERMISSIONS_REQUEST_ID, String msg) {

        if (underAPI23())
            return true;

        boolean shouldShowRequest = false;
        List<String> pendingPermission = new ArrayList<>();

        for (String PERMISSION : PERMISSIONS) {
            int check = ContextCompat.checkSelfPermission(activity, PERMISSION);
            //Log.i(TAG, PERMISSION + ": " + (check != PackageManager.PERMISSION_GRANTED));
            if (check != PackageManager.PERMISSION_GRANTED) {
                pendingPermission.add(PERMISSION);
                if (!shouldShowRequest) {
                    boolean should = ActivityCompat.shouldShowRequestPermissionRationale(activity, PERMISSION);//kiểm tra xem người dùng có chặn hiển thị thông báo xin cấp quyền
                    if (should)
                        shouldShowRequest = true;
                }
            }
        }

        int denyPermissionLength = pendingPermission.size();
        String[] denyPermission = new String[denyPermissionLength];
        for (int i = 0; i < denyPermissionLength; i++) {
            denyPermission[i] = pendingPermission.get(i);
        }

        if (denyPermissionLength > 0) {
            if (shouldShowRequest) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Xin cấp quyền");
                builder.setMessage(msg);
                builder.setPositiveButton("Đồng ý", null);
                builder.show();
            } else {
                ActivityCompat.requestPermissions(activity, denyPermission, PERMISSIONS_REQUEST_ID);
            }
            return false;
        } else {
            return true;
        }
    }
}
