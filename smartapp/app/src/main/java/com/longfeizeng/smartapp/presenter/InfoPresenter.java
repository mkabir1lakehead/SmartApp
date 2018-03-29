package com.longfeizeng.smartapp.presenter;

import com.longfeizeng.smartapp.view.IBaseView;
import com.longfeizeng.smartapp.view.InfoView;

/**
 * Created by zlf on 27/03/18.
 */

public class InfoPresenter extends BasePresenter {
    public InfoPresenter(IBaseView baseView) {
        super( baseView );
    }
    @Override
    public void serverResponse(String s) {
        InfoView infoView = (InfoView) getBaseView();
        infoView.email.setText( "sdfa" );
        infoView.phone.setText( "" );
    }
}
