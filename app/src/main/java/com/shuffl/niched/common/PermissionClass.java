package com.shuffl.niched.common;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionClass {

    private static final int PERMISSIONS = 1;
    Activity mActivity;

    public PermissionClass(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public boolean checkPermissions(String permission){
        if(ContextCompat.checkSelfPermission(mActivity,
                permission)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        return false;
    }

    public void askPermissions(String[] permissions){
        ActivityCompat.requestPermissions(mActivity,
                permissions,
                PERMISSIONS);
    }
}
