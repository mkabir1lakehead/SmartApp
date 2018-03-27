package com.longfeizeng.smartapp.view;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.longfeizeng.smartapp.R;
import com.longfeizeng.smartapp.Statistics.StatisticsInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by zlf on 18/03/18.
 */

public class ContentActivity extends AppCompatActivity {
    boolean permission;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_content);

        // 隐藏左上角图标
        getSupportActionBar().setDisplayShowTitleEnabled(false  );
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        // 显示左上角返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = (TextView)findViewById( R.id.no_service );

    }

    protected void onResume() {
        super.onResume();
        permission = checkUsagePermission( this );
        if(permission == true){
            Intent intent3 = new Intent(ContentActivity.this, AppStatisticsList.class);
            startActivity(intent3);
            finish();

        }else{

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.info_settings:

                break;
            case R.id.camera_settings:
                Intent intent = new Intent( ContentActivity.this,PhoneSwitch.class );
                startService( intent );
                break;
            case R.id.app_settings:
                textView.setText( "dafdf" );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    try {
                        startActivity(new Intent( Settings.ACTION_USAGE_ACCESS_SETTINGS));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
            case R.id.notify_settings:
                Intent intent1 = new Intent( ContentActivity.this,NotificationSwitch.class );
                startActivity( intent1 );
                break;
        }

        return false;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static boolean checkUsagePermission(Context context) {
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(),context.getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }

}
