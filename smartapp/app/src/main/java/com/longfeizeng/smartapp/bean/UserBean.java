package com.longfeizeng.smartapp.bean;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by zlf on 16/03/18.
 */

public class UserBean {

    private String USER_INFO_ID;
    private String USER_INFO_FIRSTNAME;
    private String USER_INFO_LASTNAME;
    private String USER_INFO_PHONENUMBER;
    private String USER_INFO_EMAIL;
    private String USER_INFO_PASSWORD;
    private String USER_INFO_PARENTSNUMBER;
    private String USER_INFO_PARENTSEMAIL;

    public String getAPP() {
        return APP;
    }

    public void setAPP(String APP) {
        this.APP = APP;
    }

    private String APP;
    private Gson userGson;
    private user_Info userInfo;

    public String getUSER_INFO_FIRSTNAME() {
        return USER_INFO_FIRSTNAME;
    }

    public String getUSER_INFO_LASTNAME() {
        return USER_INFO_LASTNAME;
    }

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
    public void setUSER_INFO_FIRSTNAME(String USER_INFO_FIRSTNAME) {
        this.USER_INFO_FIRSTNAME = USER_INFO_FIRSTNAME;
    }

    public void setUSER_INFO_LASTNAME(String USER_INFO_LASTNAME) {
        this.USER_INFO_LASTNAME = USER_INFO_LASTNAME;
    }

    public String getUSER_INFO_ID() {
        return USER_INFO_ID;
    }

    public void setUSER_INFO_ID(String USER_INFO_ID) {
        this.USER_INFO_ID = USER_INFO_ID;
    }

    public String getUSER_INFO_PARENTSNUMBER() {
        return USER_INFO_PARENTSNUMBER;
    }

    public void setUSER_INFO_PARENTSNUMBER(String USER_INFO_PARENTSNUMBER) {
        this.USER_INFO_PARENTSNUMBER = USER_INFO_PARENTSNUMBER;
    }

    public String getUSER_INFO_PARENTSEMAIL() {
        return USER_INFO_PARENTSEMAIL;
    }

    public void setUSER_INFO_PARENTSEMAIL(String USER_INFO_PARENTSEMAIL) {
        this.USER_INFO_PARENTSEMAIL = USER_INFO_PARENTSEMAIL;
    }

    public String getJsonUser(){
        userGson = new Gson();
        userInfo = new user_Info();
        userInfo.USER_INFO_EMAIL=getUserEmail();
        userInfo.USER_INFO_ID =getUserID();
        userInfo.USER_INFO_PASSWORD =getUserPassword();
        userInfo.USER_INFO_PHONENUMBER =getUserPhone();
        userInfo.USER_INFO_FIRSTNAME = getUSER_INFO_FIRSTNAME();
        userInfo.USER_INFO_LASTNAME = getUSER_INFO_LASTNAME();
        userInfo.USER_INFO_PARENTSNUMBER = getUSER_INFO_PARENTSNUMBER();
        userInfo.USER_INFO_PARENTSEMAIL = getUSER_INFO_PARENTSEMAIL();
        userInfo.APP = getAPP();
        String userGsonString = userGson.toJson( userInfo );
        return  userGsonString;
    }
    class user_Info{
        public String USER_INFO_ID;
        public String USER_INFO_PHONENUMBER;
        public String USER_INFO_EMAIL;
        public String USER_INFO_PASSWORD;
        public String USER_INFO_FIRSTNAME;
        public String USER_INFO_LASTNAME;
        public String USER_INFO_PARENTSNUMBER;
        public String USER_INFO_PARENTSEMAIL;
        public String APP;
    }
}
