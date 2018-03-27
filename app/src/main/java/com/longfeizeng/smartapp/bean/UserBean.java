package com.longfeizeng.smartapp.bean;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by zlf on 16/03/18.
 */

public class UserBean {

    private String USER_INFO_ID;
    private String USER_INFO_PHONENUMBER;
    private String USER_INFO_EMAIL;
    private String USER_INFO_PASSWORD;
    private Gson userGson;
    private user_Info userInfo;

    public String getUserID() {
        return USER_INFO_ID;
    }

    public void setUserID(String userID) {
        this.USER_INFO_ID = userID;
    }

    public String getUserPhone() {
        return USER_INFO_PHONENUMBER;
    }

    public void setUserPhone(String userPhone) {
        this.USER_INFO_PHONENUMBER = userPhone;
    }

    public String getUserEmail() {
        return USER_INFO_EMAIL;
    }

    public void setUserEmail(String userEmail) {
        this.USER_INFO_EMAIL = userEmail;
    }

    public String getUserPassword() {
        return USER_INFO_PASSWORD;
    }

    public void setUserPassword(String userPassword) {
        this.USER_INFO_PASSWORD = userPassword;
    }

    public String getJsonUser(){
        userGson = new Gson();
        userInfo = new user_Info();
        userInfo.USER_INFO_EMAIL=getUserEmail();
        userInfo.USER_INFO_ID =getUserID();
        userInfo.USER_INFO_PASSWORD =getUserPassword();
        userInfo.USER_INFO_PHONENUMBER =getUserPhone();
        String userGsonString = userGson.toJson( userInfo );
        return  userGsonString;
    }
    class user_Info{
        public String USER_INFO_ID;
        public String USER_INFO_PHONENUMBER;
        public String USER_INFO_EMAIL;
        public String USER_INFO_PASSWORD;
    }
}
