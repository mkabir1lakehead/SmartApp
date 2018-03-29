package com.longfeizeng.smartapp.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.longfeizeng.smartapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.longfeizeng.smartapp.Statistics.AppInformation;
import com.longfeizeng.smartapp.Statistics.StatisticsInfo;
import com.longfeizeng.smartapp.server.AlarmReceive;


public class AppStatisticsList extends AppCompatActivity {
    public List<Map<String,Object>> datalist;
    private int style;
    public static long totalTime;
    public static int mostTimes;
    public static long mostTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.activity_app_statistics_list);
        this.style = StatisticsInfo.DAY;
        // 隐藏左上角图标
        getSupportActionBar().setDisplayShowTitleEnabled(false  );
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        // 显示左上角返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button buttonday = (Button) findViewById(R.id.daybuttonlist3);
        buttonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(style != StatisticsInfo.DAY) {
                    style = StatisticsInfo.DAY;
                    onResume();
                }
            }
        });
        Button buttonweek = (Button) findViewById(R.id.weekbuttonlist3);
        buttonweek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(style != StatisticsInfo.WEEK) {
                    style = StatisticsInfo.WEEK;
                    onResume();
                }
            }
        });
        Button buttonmonth = (Button) findViewById(R.id.monthbuttonlist3);
        buttonmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(style != StatisticsInfo.MONTH) {
                    style = StatisticsInfo.MONTH;
                    onResume();
                }
            }
        });

    }

    private void SetButtonColor() {
        Button buttonday = (Button) findViewById(R.id.daybuttonlist3);
        Button buttonmonth = (Button) findViewById(R.id.monthbuttonlist3);
        Button buttonweek = (Button) findViewById(R.id.weekbuttonlist3);

        buttonday.setTextColor( Color.WHITE);
        buttonmonth.setTextColor( Color.WHITE);
        buttonweek.setTextColor( Color.WHITE);

        switch (style) {
            case StatisticsInfo.DAY:
                buttonday.setTextColor( Color.GREEN);
                break;
            case StatisticsInfo.MONTH:
                buttonmonth.setTextColor( Color.GREEN);
                break;
            case StatisticsInfo.WEEK:
                buttonweek.setTextColor( Color.GREEN);
                break;
        }

    }

    //每次重新进入界面的时候加载listView
    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();

        SetButtonColor();


        //List<Map<String,Object>> datalist = null;

        StatisticsInfo statisticsInfo = new StatisticsInfo(this,this.style);
        totalTime = statisticsInfo.getTotalTime();
        mostTimes = statisticsInfo.getMostTimes();
        mostTime = statisticsInfo.getMostTime();
        datalist = getDataList(statisticsInfo.getShowList());

        ListView listView = (ListView)findViewById(R.id.AppStatisticsList);
        SimpleAdapter adapter = new SimpleAdapter(this,datalist,R.layout.inner_time_total,
                new String[]{"label","info","icon"},
                new int[]{R.id.label,R.id.time,R.id.icon});
        listView.setAdapter(adapter);

//        int number = listView.getCount();
//        for(int position=0;position<number;position++){
//            View view = listView.getChildAt(position-listView.getFirstVisiblePosition());
//            ImageView timebar = (ImageView)view.findViewById( R.id.tiem_bar );
           // RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) timebar.getLayoutParams();
            //params.width = position*10;
           // timebar.setLayoutParams(params);
   //     }


        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if(view instanceof ImageView && o instanceof Drawable){

                    ImageView iv=(ImageView)view;
                    iv.setImageDrawable((Drawable)o);
                    return true;
                }
                else return false;
            }
        });

//        TextView textView = (TextView)findViewById(R.id.text1);
//        textView.setText("运行总时间: " + DateUtils.formatElapsedTime(totalTime / 1000));
    }

    private List<Map<String,Object>> getDataList(ArrayList<AppInformation> ShowList) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("label","全部应用");
        map.put("info","运行时间: " + DateUtils.formatElapsedTime(totalTime / 1000));
        map.put("times","本次zuida次数: " + mostTimes);
        map.put("icon",R.drawable.common_plus_signin_btn_icon_dark);
        dataList.add(map);

        for(AppInformation appInformation : ShowList) {
            map = new HashMap<String,Object>();
            map.put("label",appInformation.getLabel());
            map.put("info","运行时间: " + DateUtils.formatElapsedTime(appInformation.getUsedTimebyDay() / 1000));
            map.put("times","本次开机操作次数: " + appInformation.getTimes());
            map.put("icon",appInformation.getIcon());
            dataList.add(map);
        }

        return dataList;
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
                Intent intent3 = new Intent( AppStatisticsList.this,InfoView.class );
                startActivity( intent3 );
                break;
            case R.id.camera_settings:
                //Toast.makeText(this, "你点击了第二", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent( AppStatisticsList.this,PhoneSwitch.class );
                startActivity( intent );
                break;
            case R.id.app_settings:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    try {
                        startActivity(new Intent( Settings.ACTION_USAGE_ACCESS_SETTINGS));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return true;
            case R.id.notify_settings:
                Intent intent1 = new Intent( AppStatisticsList.this,NotificationSwitch.class );
                startActivity( intent1 );
                break;
            case R.id.action_settings:
                Intent intent4 = new Intent( AppStatisticsList.this,Supervise.class );
                startActivity( intent4 );
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


