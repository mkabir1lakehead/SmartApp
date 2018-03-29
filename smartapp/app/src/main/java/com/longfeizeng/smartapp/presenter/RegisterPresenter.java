package com.longfeizeng.smartapp.presenter;

import android.content.Intent;

import com.longfeizeng.smartapp.view.ContentActivity;
import com.longfeizeng.smartapp.view.IBaseView;
import com.longfeizeng.smartapp.view.LoginActivity;
import com.longfeizeng.smartapp.view.Register;

/**
 * Created by zlf on 28/03/18.
 */

public class RegisterPresenter extends BasePresenter {

    public RegisterPresenter(IBaseView baseView) {
        super( baseView );
    }

    @Override
    public void serverResponse(String s) {
        Register register = (Register) getBaseView();
        Intent intent_Content = new Intent( register, LoginActivity.class );
        register.startActivity(intent_Content);
        register.finish();
    }
}
