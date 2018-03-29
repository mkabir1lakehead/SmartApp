package com.longfeizeng.smartapp.model;

/**
 * Created by zlf on 16/03/18.
 */

public interface IBaseModel {

    void sendRequestToServer();
    void setApiInterface(String apiInterface);
    void cancelRequest();

}
