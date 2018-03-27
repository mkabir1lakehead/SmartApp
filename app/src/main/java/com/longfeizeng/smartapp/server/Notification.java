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
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Toast;

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
    private AlarmManager am = null;
    private final IBinder mBinder = new LocalBinder();
    private static final int ONE_Miniute=1*60*1000;
    private static final int PENDING_REQUEST=0;

    BroadcastReceiver alarmReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.vegetables_source.alarm".equals(intent.getAction())) {

                getTopAppInfo();

            }

        }
    };

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
    public void init(){

        am= (AlarmManager) getSystemService(ALARM_SERVICE);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.vegetables_source.alarm");
        registerReceiver(alarmReceiver, filter);

        Intent intent = new Intent();
        intent.setAction("com.vegetables_source.alarm");
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);

        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                1000*10, pi);
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

    public void getTopAppInfo(){
        String topActivity = "";
        Log.d( "FFFFFFFFFF","XXXXXXXX" );
        Long time;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager m = (UsageStatsManager) this.getSystemService( Context.USAGE_STATS_SERVICE);
            if (m != null) {
                long now = System.currentTimeMillis();
                List<UsageStats> stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 60 * 1000, now);


                if ((stats != null) && (!stats.isEmpty())) {
                    int j = 0;
                    for (int i = 0; i < stats.size(); i++) {
                        if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
                            j = i;
                        }
                        topActivity = stats.get(j).getPackageName();
                        time = stats.get( j ).getTotalTimeInForeground();
                        if(time>15*1000 ){
                            Toast toast = Toast.makeText(this, "You are using " + topActivity + " more than 30 minutes. Time to leave your phone and take a break.",
                                    Toast.LENGTH_LONG);
                            showMyToast(toast, 10*1000);
                        }else if (time>30*1000){
                            Toast toast2 = Toast.makeText(this, "You are using " + topActivity + " more than 1 hour, leave your phone please. Using phone abnormally results in creating fatigue!",
                                    Toast.LENGTH_LONG);
                            showMyToast(toast2, 10*1000);
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