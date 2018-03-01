package com.example.sanaebadi.ussd;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

  public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
  private static int SPLASH_TIME_OUT = 2000;
  private Handler handler;
  private static final String TAG = "SplashActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    handler = new Handler();

    if (checkAndRequestPermissions()) {

      handler.postDelayed(new Runnable() {
        @Override
        public void run() {
          startActivity(new Intent(SplashActivity.this, MainActivity.class));
          finish();
        }
      }, SPLASH_TIME_OUT);
    }
  }

  private boolean checkAndRequestPermissions() {

    int callPhonePermission = ContextCompat.checkSelfPermission(SplashActivity.this,
      Manifest.permission.CALL_PHONE);


    List<String> listPermissionNeeded = new ArrayList<>();

    if (callPhonePermission != PackageManager.PERMISSION_GRANTED) {
      listPermissionNeeded.add(Manifest.permission.CALL_PHONE);

    }


    if (!listPermissionNeeded.isEmpty()) {
      ActivityCompat.requestPermissions(SplashActivity.this,
        listPermissionNeeded.toArray(new String[listPermissionNeeded.size()])
        , REQUEST_ID_MULTIPLE_PERMISSIONS);

      return false;
    }

    return true;
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    Log.d(TAG, "Permission callback called-------");

    switch (requestCode) {
      case REQUEST_ID_MULTIPLE_PERMISSIONS: {
        Map<String, Integer> perms = new HashMap<>();

        perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);

        if (grantResults.length > 0) {
          for (int i = 0; i < permissions.length; i++) {
            perms.put(permissions[i], grantResults[i]);

            if (perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {


              Log.d(TAG, "sms & location services permission granted");
              startActivity(new Intent(SplashActivity.this, MainActivity.class));
              finish();
            }

          }
        } else {
          Log.e(TAG, "Some permissions are not granted ask again ");

          if (ActivityCompat.shouldShowRequestPermissionRationale
            (SplashActivity.this, Manifest.permission.CALL_PHONE)) {


            showDialogOk("Service Permissions are required for this app",
              new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                      checkAndRequestPermissions();
                      break;
                    case DialogInterface.BUTTON_NEGATIVE:
                      finish();
                      break;
                  }
                }
              });

          } else {
            explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?");

          }
        }
      }
    }
  }


  private void showDialogOk(String message, DialogInterface.OnClickListener okListener) {
    new AlertDialog.Builder(SplashActivity.this)
      .setMessage(message)
      .setPositiveButton("OK", okListener)
      .setNegativeButton("Cancel", okListener)
      .create()
      .show();
  }

  private void explain(String msg) {
    AlertDialog.Builder dialog = new AlertDialog.Builder(SplashActivity.this);
    dialog.setMessage(msg)
      .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
          startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package com.example.sanaebadi.ussd")));
        }
      })
      .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
          finish();
        }
      });

    dialog.show();
  }
}


