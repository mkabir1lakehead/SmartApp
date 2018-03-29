package com.longfeizeng.smartapp.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.longfeizeng.smartapp.R;
import com.longfeizeng.smartapp.bean.UserBean;
import com.longfeizeng.smartapp.model.BaseModel;

/**
 * Created by zlf on 28/03/18.
 */

public class Supervise extends AppCompatActivity implements IBaseView, View.OnClickListener{
    Button startservice;
    Button stopservice;

    public String SHARED_PREFERENCES_NAME = "USER";
    SharedPreferences sp_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.supervise );
        startservice = (Button)findViewById( R.id.start );
        stopservice = (Button)findViewById( R.id.stop );
        sp_user = this.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        startservice.setOnClickListener(this );
        stopservice.setOnClickListener( this );

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:                       //确认按钮的监听事件
                SharedPreferences.Editor editor = sp_user.edit();
                editor.putString("USER_SUPERVISE","TRUE");
                editor.commit();
                break;
            case R.id.stop:                     //取消按钮的监听事件,由注册界面返回登录界面
                SharedPreferences.Editor editor2 = sp_user.edit();
                editor2.putString("USER_SUPERVISE","FALSE");
                editor2.commit();
                break;
        }
    }

    @Override
    public void showProcess(boolean show) {

    }

    @Override
    public void showOkhttpError(int errorCode, String errorDesc, String ApiInterface) {

    }

    @Override
    public void showServerError(int errorCode, String errorDesc) {

    }
}
