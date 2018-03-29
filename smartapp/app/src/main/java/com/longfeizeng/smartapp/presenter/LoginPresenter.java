package com.longfeizeng.smartapp.presenter;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.longfeizeng.smartapp.view.ContentActivity;
import com.longfeizeng.smartapp.view.IBaseView;
import com.longfeizeng.smartapp.view.LoginActivity;

/**
 * Created by zlf on 18/03/18.
 */

public class LoginPresenter extends BasePresenter {
    public LoginPresenter(IBaseView baseView) {
        super( baseView );
    }

    @Override
    public void serverResponse(String s) {
        Log.i( "real serverResponse","going to new page" );
        LoginActivity loginView = (LoginActivity) getBaseView();

        loginView.setLocalUser(  loginView.getmUserBean()  );

        Gson userGson = new Gson();
        parents_Info parents_info;
        parents_info = userGson.fromJson(s,parents_Info.class);

        loginView.setParentInfo( parents_info.USER_INFO_PARENTSNUMBER,parents_info.USER_INFO_PARENTSEMAIL,parents_info.USER_INFO_FIRSTNAME);

        Intent intent_Content = new Intent( loginView, ContentActivity.class );
        loginView.startActivity(intent_Content);
        loginView.finish();
    }

}
class parents_Info{
    public String USER_INFO_PARENTSNUMBER;
    public String USER_INFO_PARENTSEMAIL;
    public String USER_INFO_FIRSTNAME;

}
