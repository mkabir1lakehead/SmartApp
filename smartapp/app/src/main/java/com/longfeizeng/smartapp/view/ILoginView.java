package com.longfeizeng.smartapp.view;

/**
 * Created by zlf on 09/03/18.
 */

public interface ILoginView {

    String getPhoneNum();
    String getEmail();
    String getVerificationCode();
    String getFristName();
    String getLastName();

    void setPhoneNum(String phone);
    void setEmail(String Email);
    void setFirstName(String firstName);
    void setLastName(String lastName);
}
