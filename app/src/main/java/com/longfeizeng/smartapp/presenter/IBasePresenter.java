package com.longfeizeng.smartapp.presenter;

import com.longfeizeng.smartapp.model.IBaseModel;
import com.longfeizeng.smartapp.view.IBaseView;

import org.json.JSONObject;

/**
 * Created by zlf on 16/03/18.
 */

public interface IBasePresenter<P> {
    void accessServer(P params);
    void accessSucceed(String errorNum,String response);
    String getParams();
    IBaseModel getModel();
    IBaseView getBaseView();
    void cancelRequset();
    void okhttpError(int  errorCode, String errorDesc,String Apilnterface);
}
