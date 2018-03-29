package com.longfeizeng.smartapp.server;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.longfeizeng.smartapp.bean.UserBean;
import com.longfeizeng.smartapp.model.BaseModel;
import com.longfeizeng.smartapp.presenter.SupervisePresenter;
import com.longfeizeng.smartapp.view.IBaseView;
import com.longfeizeng.smartapp.view.Supervise;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zlf on 26/03/18.
 */

public class Notification extends Service{


    /**
     * 每1分钟更新一次数据
     */

    public String SHARED_PREFERENCES_NAME = "USER";
    SharedPreferences sp_user;
    private UserBean mUserBean;

    SupervisePresenter supervisePresenter;
    private String api = "http://172.20.10.5:8080";
    private Supervise ss;

    public long TIME_INTERVAL = 20*1000;
    PendingIntent pi;
    private AlarmManager am = null;
    private final IBinder mBinder = new LocalBinder();
    private static final int ONE_Miniute=5*30*1000;
    private static final int PENDING_REQUEST=0;

    BroadcastReceiver alarmReceiver = new BroadcastReceiver() {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.vegetables_source.alarm".equals(intent.getAction())) {

                try {
                    getTopAppInfo();
                    setAlarmTime(context, System.currentTimeMillis() + TIME_INTERVAL,
                            intent);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }
    };

    public static void setAlarmTime(Context context, long timeInMillis, Intent intent) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(context, intent.getIntExtra("id", 0),
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        int interval = (int) intent.getLongExtra("intervalMillis", 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setWindow(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate() {

        init();
    }
    public class LocalBinder extends Binder {
        public LocalBinder getService() {
            return LocalBinder.this;
        }

    }

    public Notification() {

    }

    /**
     * 调用Service都会执行到该方法
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void init(){


        sp_user= this.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        supervisePresenter = new SupervisePresenter( ss );


        am= (AlarmManager) getSystemService(ALARM_SERVICE);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.vegetables_source.alarm");
        registerReceiver(alarmReceiver, filter);

        Intent intent = new Intent();
        intent.setAction("com.vegetables_source.alarm");
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);

        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                +30*1000, pi);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void getTopAppInfo() throws PackageManager.NameNotFoundException {
        String topActivity = "";
        Long time;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager m = (UsageStatsManager) this.getSystemService( Context.USAGE_STATS_SERVICE );
            if (m != null) {
                long now = System.currentTimeMillis();
                List<UsageStats> stats = m.queryUsageStats( UsageStatsManager.INTERVAL_BEST, now - 60 * 1000, now );


                if ((stats != null) && (!stats.isEmpty())) {
                    int j = 0;
                    for (int i = 0; i < stats.size(); i++) {
                        if (stats.get( i ).getLastTimeUsed() > stats.get( j ).getLastTimeUsed()) {
                            j = i;
                        }
                    }
                    topActivity = stats.get( j ).getPackageName();
                    PackageManager packageManager = this.getPackageManager();
                    if (topActivity != null && !topActivity.equals( "" )) {
                        ApplicationInfo applicationInfo = packageManager.getApplicationInfo( topActivity, 0 );
                        topActivity = (String) packageManager.getApplicationLabel( applicationInfo );
                    }
                    time = stats.get( j ).getTotalTimeInForeground();
                    Log.d( "topapp", topActivity + "--------- runtime " + time / (60 * 1000) + "---------" );

                    if (time > 30 * 1000 && time < 60 * 1000) {
                        Toast toast = Toast.makeText( this, "You are using " + topActivity + " more than 30 seconds. Time to leave your phone and take a break.",
                                Toast.LENGTH_LONG );
                        showMyToast( toast, 10 * 1000 );
                    } else if (time > 60 * 1000) {
                        Toast toast2 = Toast.makeText( this, "You are using " + topActivity + " more than 1 minute, leave your phone please. Using phone abnormally results in creating fatigue!",
                                Toast.LENGTH_LONG );
                        showMyToast( toast2, 10 * 1000 );
                        String supervise = sp_user.getString( "USER_SUPERVISE","" );
                        if(supervise.equals( "TRUE" )){
                            String firstname = sp_user.getString( "USER_INFO_FIRSTNAME","" );
                            String parentnum = sp_user.getString( "USER_INFO_PARENTSNUMBER","" );
                            ((BaseModel)supervisePresenter.getModel()).setApiInterface( api+"/Supervise" );
                            mUserBean = new UserBean();
                            mUserBean.setUSER_INFO_FIRSTNAME( firstname );
                            mUserBean.setUSER_INFO_PARENTSNUMBER( parentnum );
                            mUserBean.setAPP( topActivity );
                            String userInfo = mUserBean.getJsonUser();
                            supervisePresenter.accessServer( userInfo );
                        }

                    }
                }
            }
        }
    }


    public void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }
}