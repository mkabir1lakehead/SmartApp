package com.longfeizeng.smartapp.presenter;

import android.content.Intent;
import android.util.Log;

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
        Intent intent_Content = new Intent( loginView, ContentActivity.class );
        loginView.startActivity(intent_Content);
        loginView.finish();
    }
}
