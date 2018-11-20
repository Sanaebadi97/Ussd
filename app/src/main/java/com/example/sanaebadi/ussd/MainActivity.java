package com.example.sanaebadi.ussd;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  private Button btn_ussd;
  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    btn_ussd = (Button) 

    btn_ussd.setOnClickListener(new View.OnClickListener() {
      @SuppressLint("MissingPermission")
      @Override
      public void onClick(View view) {

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(Uri.parse("tel:" + "*1") + Uri.encode("#")));
        MainActivity.this.startActivity(intent);
      }

    });

  }
}

