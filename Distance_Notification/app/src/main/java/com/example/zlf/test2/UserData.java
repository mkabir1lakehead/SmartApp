package com.example.zlf.test2;
/**
 * Created by FoolishFan on 2016/7/14.
 */

public class UserData {
    private String userFirstName;
    private String userLastName;
    private String userEmail;                  //用户名
    private String userPwd;                   //用户密码
    private int userId;                       //用户ID号
    public int pwdresetFlag=0;

    public String getUserFirstName() {             //获取用户名
        return userFirstName;
    }
    //设置用户名
    public void setUserFirstName(String userFirstName) {  //输入用户名
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {             //获取用户名
        return userLastName;
    }
    //设置用户名
    public void setUserLastName(String userLastName) {  //输入用户名
        this.userLastName = userLastName;
    }

    //获取用户名
    public String getUserEmail() {             //获取用户名
        return userEmail;
    }
    //设置用户名
    public void setUserEmail(String userEmail) {  //输入用户名
        this.userEmail = userEmail;
    }
    //获取用户密码
    public String getUserPwd() {                //获取用户密码
        return userPwd;
    }
    //设置用户密码
    public void setUserPwd(String userPwd) {     //输入用户密码
        this.userPwd = userPwd;
    }
    //获取用户id
    public int getUserId() {                   //获取用户ID号
        return userId;
    }
    //设置用户id
    public void setUserId(int userId) {       //设置用户ID号
        this.userId = userId;
    }

   /* public UserData(String userName, String userPwd, int userId) {    //用户信息
        super();
        this.userName = userName;
        this.userPwd = userPwd;
        this.userId = userId;
    }*/

    public UserData(String userEmail, String userPwd) {  //这里只采用用户名和密码
        super();
        this.userEmail = userEmail;
        this.userPwd = userPwd;
    }

}
