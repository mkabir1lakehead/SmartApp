package com.longfeizeng.smartapp.view;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.longfeizeng.smartapp.R;
import com.longfeizeng.smartapp.bean.UserBean;
import com.longfeizeng.smartapp.model.BaseModel;
import com.longfeizeng.smartapp.permissions.PermissionsActivity;
import com.longfeizeng.smartapp.permissions.PermissionsChecker;
import com.longfeizeng.smartapp.presenter.BasePresenter;
import com.longfeizeng.smartapp.presenter.LoginPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity implements IBaseView, View.OnClickListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{



    public String SHARED_PREFERENCES_NAME = "USER";
    SharedPreferences sp_user;

    private LoginPresenter mLoginPresenter;
    private UserBean mUserBean;

    private EditText mAccount;
    private EditText mPwd;
    private Button mLoginButton;
    private TextView mRegisterTextView;
    private String api = "http://172.20.10.5:8080";


    LoginButton facebook_loginButton;
    CallbackManager callbackManager;

    SignInButton google_signinButton;
    GoogleApiClient mGoogleApiClient;
    private GoogleSignListener googleSignListener ;

    static int LOGIN_CODE = 0;
    static int RC_SIGN_IN=0;
    String user_firest_name,user_last_name,email,gender,bday;


    Handler mHandlers= new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message message) {
            Toast toast = Toast.makeText(LoginActivity.this,"CAN NOT FIND ACCOUNT",Toast.LENGTH_SHORT);
            showMyToast( toast,5*1000 );
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp_user = this.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        findWidgets();

        facebookset();

        googleset();
        mLoginButton.setOnClickListener( this );
        mRegisterTextView.setOnClickListener(this);
        mLoginPresenter = new LoginPresenter( this );
        mUserBean = new UserBean();

    }

    public boolean setLocalUser(UserBean mUserBean){
        if(mUserBean == null)
            return false;
        try{
            SharedPreferences.Editor editor = sp_user.edit();
            editor.putString("USER_INFO_PHONENUMBER", mUserBean.getUserPhone());
            editor.putString("USER_INFO_PASSWORD", mUserBean.getUserPassword());
            editor.commit();
            return true;
        }catch(Exception exception){
            return false;
        }
    }
    public boolean setParentInfo(String phone, String EMAIL,String FirstName){
        try{
            SharedPreferences.Editor editor = sp_user.edit();
            editor.putString("USER_INFO_PARENTSNUMBER", phone);
            editor.putString("USER_INFO_PARENTSEMAIL", EMAIL);
            editor.putString("USER_INFO_FIRSTNAME", FirstName);
            editor.commit();
            return true;
        }catch(Exception exception){
            return false;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn_login:{
                if(isPhoneValid() == true && isPwdValid() == true){
                    ((BaseModel)mLoginPresenter.getModel()).setApiInterface( api+"/ShowRs" );
                    String mUserJson = mUserBean.getJsonUser();
                    Log.i("run" ,"connecting to database..." );
                    mLoginPresenter.accessServer( mUserJson );
                    Log.i("run" ,"connected database" );
                }
                break;
            }
            case R.id.google_signin_button:{
                if(LOGIN_CODE==0){
                    Log.i("robin","google signin button");
                    google_Signin();
                    break;
                }else if(LOGIN_CODE==2){
                    Log.i("robin","google signin button");
                    google_SignOut();
                    break;
                }else {
                    Toast.makeText(getApplicationContext(),"already login with other way",Toast.LENGTH_SHORT).show();
                    break;
                }

            }
            case R.id.login_btn_register:
                Log.d( "-------------","************" );//登录界面的注册按钮
                Intent intent_Login_to_Register = new Intent(LoginActivity.this,Register.class) ;    //切换Login Activity至User Activity
                startActivity(intent_Login_to_Register);
                finish();
                break;
        }
    }

    private void google_Signin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void google_SignOut() {
         Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                 new ResultCallback<Status>() {
                     @Override
                     public void onResult(Status status) {
                         if ( status.isSuccess() ){
                             LOGIN_CODE = 0;
                             if ( googleSignListener != null ){

                                 googleSignListener.googleLogoutSuccess();
                             }
                         }else {
                             if ( googleSignListener!= null ){
                                 googleSignListener.googleLogoutFail();
                             }
                         }
                                    }
                 });
     }

    @Override
    public void showProcess(boolean show) {

    }

    @Override
    public void showOkhttpError(int errorCode, String errorDesc, String ApiInterface) {

    }

    @Override
    public void showServerError(int errorCode, String errorDesc) {

        workerThread();


        //Toast.makeText(LoginActivity.this,"can not find this account",Toast.LENGTH_SHORT).show();
//

    }
    void workerThread() {
        // And this is how you call it from the worker thread:
        Message message = mHandlers.obtainMessage();
        message.sendToTarget();
    }

    public interface GoogleSignListener {
        void googleLoginSuccess();
        void googleLoginFail() ;
        void googleLogoutSuccess();
        void googleLogoutFail() ;
     }

    void findWidgets() {
        facebook_loginButton = (LoginButton)findViewById(R.id.facebook_login_button);
        google_signinButton = (SignInButton)findViewById(R.id.google_signin_button);
        mAccount = (EditText)findViewById( R.id.login_edit_account );
        mPwd = (EditText)findViewById( R.id.login_edit_pwd );
        mLoginButton = (Button)findViewById( R.id.login_btn_login );
        mRegisterTextView = (TextView) findViewById(R.id.login_btn_register);

    }

    private void facebookset(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.longfeizeng.smartapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {

        }
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        facebook_loginButton.setReadPermissions(Arrays.asList("public_profile,email,user_birthday,"));

        facebook_loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                LOGIN_CODE = 1;
                Toast.makeText(getApplicationContext(),"facebook login successful",Toast.LENGTH_SHORT).show();
                AccessToken accessToken = loginResult.getAccessToken();
                getLoginInfo(accessToken);
            }

            @Override
            public void onCancel() {

                Toast.makeText(getApplicationContext(),"facebook login cancel",Toast.LENGTH_SHORT).show();
                LOGIN_CODE = 0;
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"facebook login error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignResult(result);
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignResult(GoogleSignInResult result) {
        Log.i("robin", "handleSignInResult:" + result.isSuccess());
        if(result.isSuccess()){
            LOGIN_CODE = 2;
            Log.i("robin", "成功");
            Toast.makeText(getApplicationContext(),"google login successful",Toast.LENGTH_SHORT).show();
            GoogleSignInAccount acct = result.getSignInAccount();
            if(acct!=null){
                user_firest_name = acct.getGivenName();
                user_last_name = acct.getFamilyName();
                email = acct.getEmail();
            }
        }else{
            LOGIN_CODE = 0;
            Toast.makeText(getApplicationContext(),"google login error",Toast.LENGTH_SHORT).show();
            Log.i("robin", "没有成功"+result.getStatus());
        }
    }
    public void setGoogleSignListener( GoogleSignListener googleSignListener ){
         this.googleSignListener = googleSignListener ;
     }

    public void  getLoginInfo(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object,
                                            GraphResponse response) {

                        Log.v("LoginActivity", response.toString());
                        Toast.makeText(LoginActivity.this, "run" + object.toString() , Toast.LENGTH_SHORT).show();
                        if(object != null){
                            try {

                                user_firest_name = object.getString("first_name");
                                user_last_name = object.getString("last_name");
                                email = object.getString("email");
                                gender = object.getString("gender");
                                bday = object.getString("birthday");

                                Toast.makeText(LoginActivity.this, "" + object.toString() , Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                // TODO Auto-generated catch
                                // block
                                e.printStackTrace();
                            }
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void googleset(){
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .build();
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .enableAutoManage(this, this)/* FragmentActivity *//* OnConnectionFailedListener */
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        google_signinButton.setOnClickListener(this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("robin","google登录-->onConnected,bundle=="+bundle);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("robin","google登录-->onConnectionSuspended,i=="+i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("robin","google登录-->onConnectionFailed,connectionResult=="+connectionResult);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public boolean isPhoneValid(){
        boolean valid = true;
        String phone = mAccount.getText().toString().trim();
        mUserBean.setUserPhone( phone );
        if(phone.isEmpty() || phone.length() != 10){
            mAccount.setError( "wrong phonenumber fomart" );
            valid = false;
        }else{
            mAccount.setError( null );
        }
        return valid;
    }
    public boolean isPwdValid(){
        boolean valid = true;
        String pwd = mPwd.getText().toString().trim();
        mUserBean.setUserPassword( pwd );
        if(pwd.isEmpty() || pwd.length()>20 || pwd.length()<2){
            mPwd.setError( "password number must 2< pwd <20 " );
            valid = false;
        }else {
            mPwd.setError( null );
        }
        return valid;
    }
    public UserBean getmUserBean(){
        return mUserBean;
    }
    public void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }


}
