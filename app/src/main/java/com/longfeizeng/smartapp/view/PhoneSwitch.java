package com.longfeizeng.smartapp.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.longfeizeng.smartapp.R;
import com.longfeizeng.smartapp.server.LocalService;

/**
 * Created by zlf on 26/03/18.
 */

public class PhoneSwitch extends AppCompatActivity {

    private Button mReturnButton;

    Intent intent3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Log.i("TEST","Granted");
            //init(barcodeScannerView, getIntent(), null);
        } else {
            ActivityCompat.requestPermissions( this,
                    new String[]{Manifest.permission.CAMERA}, 1 );//1 can be another integer
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_switch);
        mReturnButton = (Button)findViewById(R.id.returnback);

    }
    public void back_to_login(View view) {
        //setContentView(R.layout.login);
        intent3 = new Intent(PhoneSwitch.this,LocalService.class) ;
        startService(intent3);

    }
    public void stops(View view) {
        //setContentView(R.layout.login);
        ;
        stopService(intent3);
        finish();

    }
}