package com.longfeizeng.smartapp.view;

/**
 * Created by zlf on 17/03/18.
 */

public interface IBaseView {
    void showProcess(final boolean show);

    void showOkhttpError(int errorCode, String errorDesc, String ApiInterface);

    void showServerError(int errorCode, String errorDesc);
}
