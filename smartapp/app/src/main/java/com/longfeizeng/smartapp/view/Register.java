package com.longfeizeng.smartapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.login.Login;
import com.longfeizeng.smartapp.R;
import com.longfeizeng.smartapp.bean.UserBean;
import com.longfeizeng.smartapp.model.BaseModel;
import com.longfeizeng.smartapp.presenter.RegisterPresenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zlf on 28/03/18.
 */

public class Register extends AppCompatActivity implements IBaseView, View.OnClickListener{


    String userFirstName;
    String userLastName;
    String userPhone;
    String userPwd;
    String userPwdCheck;

    private EditText mfirstname;
    private EditText mlastname;//用户名编辑
    private EditText mphone;
    private EditText mPwd;                            //密码编辑
    private EditText mPwdCheck;                       //密码编辑
    private Button mSureButton;                       //确定按钮
    private Button mCancelButton;
    //取消按钮
    private RegisterPresenter registerPresenter;         //用户数据管理类
    private UserBean mUserBean;
    private String api = "http://172.20.10.5:8080";

    Handler mHandlers= new Handler( Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message message) {
            Toast toast = Toast.makeText(Register.this,"Account already existed",Toast.LENGTH_SHORT);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.register);
        mfirstname = (EditText) findViewById(R.id.first_name);
        mlastname = (EditText)findViewById( R.id.last_name );
        mphone = (EditText)findViewById( R.id.phone );
        mPwd = (EditText) findViewById(R.id.resetpwd_edit_pwd_old);
        mPwdCheck = (EditText) findViewById(R.id.resetpwd_edit_pwd_new);

        mSureButton = (Button) findViewById(R.id.register_btn_sure);
        mCancelButton = (Button) findViewById(R.id.register_btn_cancel);
        mSureButton.setOnClickListener( this );
        mCancelButton.setOnClickListener( this );
        registerPresenter = new RegisterPresenter( this );


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn_sure:                       //确认按钮的监听事件
                if(register_check()){
                        mUserBean = new UserBean();
                        mUserBean.setUSER_INFO_FIRSTNAME( userFirstName );
                        mUserBean.setUSER_INFO_LASTNAME( userLastName );
                        mUserBean.setUserPhone( userPhone );
                        mUserBean.setUserPassword( userPwd );
                        String userInfo = mUserBean.getJsonUser();
                        ((BaseModel)registerPresenter.getModel()).setApiInterface( api+"/Register" );
                        registerPresenter.accessServer( userInfo );
                }
                break;
            case R.id.register_btn_cancel:                     //取消按钮的监听事件,由注册界面返回登录界面
                Intent intent_Register_to_Login = new Intent(Register.this,LoginActivity.class) ;    //切换User Activity至Login Activity
                startActivity(intent_Register_to_Login);
                finish();
                break;
        }
    }
    public boolean register_check() {                                //确认按钮的监听事件
        if (isUserNameAndPwdValid()) {
            userFirstName = mfirstname.getText().toString().trim();
            userLastName = mlastname.getText().toString().trim();
            userPhone = mphone.getText().toString().trim();
            userPwd = mPwd.getText().toString().trim();
            userPwdCheck = mPwdCheck.getText().toString().trim();

        }

        if(!checkPhoneNumber( userPhone )) {
                Toast.makeText(this, "Phone Number Format Wrong",Toast.LENGTH_SHORT).show();
                return false;
        }
        return true;
    }

    public static boolean checkPhoneNumber(String phoneNumber){
        Pattern pattern=Pattern.compile("[0-9]{10}$");
        Matcher matcher=pattern.matcher(phoneNumber);
        return matcher.matches();
    }
    public boolean isUserNameAndPwdValid() {

        boolean valid = true;

        String password = mPwd.getText().toString();
        String checkpassword = mPwdCheck.getText().toString();

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            mPwd.setError(null);
        }

        if (checkpassword.compareTo(password)!=0){
            Toast.makeText(this, "Defferent Password",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        }
        Log.d("valid---------",""+valid);
        return valid;
    }

    @Override
    public void showProcess(boolean show) {

    }

    @Override
    public void showOkhttpError(int errorCode, String errorDesc, String ApiInterface) {

    }

    @Override
    public void showServerError(int errorCode, String errorDesc) {

        workerThread();


        //Toast.makeText(LoginActivity.this,"can not find this account",Toast.LENGTH_SHORT).show();
//

    }
    void workerThread() {
        // And this is how you call it from the worker thread:
        Message message = mHandlers.obtainMessage();
        message.sendToTarget();
    }
}
