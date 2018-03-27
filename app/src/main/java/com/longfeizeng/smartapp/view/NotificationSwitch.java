package com.longfeizeng.smartapp.view;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.longfeizeng.smartapp.R;
import com.longfeizeng.smartapp.server.AlarmReceive;
import com.longfeizeng.smartapp.server.LocalService;
import com.longfeizeng.smartapp.server.Notification;

import static android.content.Intent.ACTION_BOOT_COMPLETED;
import static com.longfeizeng.smartapp.view.ContentActivity.checkUsagePermission;

/**
 * Created by zlf on 26/03/18.
 */

public class NotificationSwitch extends AppCompatActivity{


    private Button mReturnButton;
    boolean permission;
    AlarmReceive mBroadcastReceiver;
    Intent intent3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.phone_switch);
        mReturnButton = (Button)findViewById(R.id.returnback);

    }
    public void back_to_login(View view) {

        permission = checkUsagePermission( this );
        if(permission == true){
            intent3 = new Intent(NotificationSwitch.this,Notification.class) ;
            startService(intent3);

        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {
                    startActivity(new Intent( Settings.ACTION_USAGE_ACCESS_SETTINGS));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void stops(View view) {
        //setContentView(R.layout.login);
        ;
        unregisterReceiver(mBroadcastReceiver);

    }
    @Override
    protected void onStop()
    {

        super.onStop();
    }
}