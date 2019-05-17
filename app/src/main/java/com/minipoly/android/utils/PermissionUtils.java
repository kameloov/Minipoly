package com.minipoly.android.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

/**
 * Created by kumait on 10/18/2016.
 */

public class PermissionUtils {

    public static final int STRAGE_PERMISSIONS_CODE = 7;

    public static  final String [] storage_permissions ={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    public static boolean hasStoragePermissions(Context context ){
        for (String s : storage_permissions)
        if (ContextCompat.checkSelfPermission(context,s)==PackageManager.PERMISSION_DENIED)
            return false;
        return true;
    }

    public static boolean permissionsGranted(int[] results){
        for (int i : results)
            if (i==PackageManager.PERMISSION_DENIED)
                return false;
        return true;
    }

    public static boolean hasOrrequestPermissions(Fragment fragment){
        if (hasStoragePermissions(fragment.getContext()))
            return true;
        else {
            fragment.requestPermissions(storage_permissions, STRAGE_PERMISSIONS_CODE);
            return false;
        }
    }
}
