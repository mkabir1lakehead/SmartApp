package com.longfeizeng.smartapp.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.longfeizeng.smartapp.R;
import com.longfeizeng.smartapp.bean.UserBean;
import com.longfeizeng.smartapp.model.BaseModel;
import com.longfeizeng.smartapp.presenter.InfoPresenter;
import com.longfeizeng.smartapp.presenter.LoginPresenter;


/**
 * Created by zlf on 27/03/18.
 */

public class InfoView extends AppCompatActivity implements IBaseView,View.OnClickListener{
    private InfoPresenter infoPresenter;
    private UserBean userBean;

    public String SHARED_PREFERENCES_NAME = "USER";
    SharedPreferences sp_user;

    public EditText phone;
    public EditText email;
    private Button confirm;
    private String api = "http://172.20.10.5:8080";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView( R.layout.info_setting);
        sp_user= this.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        //confirm.setOnClickListener( this );

        init();
        getInfo();

    }

    public void init(){
        confirm = (Button)findViewById( R.id.confirm );
        email = (EditText) findViewById( R.id.email );
        phone = (EditText) findViewById( R.id.phone );
    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm:{
//                if(isPhoneValid() == true && isPwdValid() == true){
//                    ((BaseModel)mLoginPresenter.getModel()).setApiInterface( api+"/ShowRs" );
//                    String mUserJson = mUserBean.getJsonUser();
//                    Log.i("run" ,"connecting to database..." );
//                    mLoginPresenter.accessServer( mUserJson );
//                    Log.i("run" ,"connected database" );
//                }

                break;
            }
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
    public void getInfo(){
        String parent_phone = sp_user.getString( "USER_INFO_PARENTSNUMBER","" );
        String parent_email = sp_user.getString( "USER_INFO_PARENTSEMAIL","" );
        phone.setText( parent_phone );
        email.setText( parent_email );
    }
}
