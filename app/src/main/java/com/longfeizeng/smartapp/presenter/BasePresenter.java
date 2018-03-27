package com.longfeizeng.smartapp.presenter;

import android.util.Log;

import com.longfeizeng.smartapp.model.BaseModel;
import com.longfeizeng.smartapp.model.IBaseModel;
import com.longfeizeng.smartapp.view.IBaseView;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by zlf on 17/03/18.
 */

public abstract class BasePresenter<Params extends String> implements IBasePresenter<Params>{

    private IBaseModel baseModel;
    private IBaseView baseView;
    private Params params;

    public abstract void serverResponse(String s);

    public BasePresenter(IBaseView baseView){
        this.baseView = baseView;
        this.baseModel = new BaseModel( this );
    }
    @Override
    public IBaseModel getModel(){
        return baseModel;
    }
    @Override
    public void accessServer(Params params){
        this.params = params;
        //baseView.showProcess( true );
        baseModel.sendRequestToServer();
    }
    @Override
    public String getParams(){
        if(params != null){
            return params;
        }else {
            return null;
        }
    }
    @Override
    public void accessSucceed(String errorNum, String response){
        Log.i("presenter","accessSucceed");
        //baseView.showProcess( false );
        if(Integer.parseInt( errorNum ) == 0){
            Log.i( "error","equals 0" );
            if(response != null){
                Log.i( "serverResponse","errorNum=0" );
                serverResponse(null);
            }else{
                serverResponse( response );
            }
        }else{
            baseView.showServerError( Integer.parseInt( errorNum ),null );
        }
    }
    @Override
    public void okhttpError(int errorCode, String errorDesc, String apiInterface){
        baseView.showOkhttpError( errorCode,errorDesc,apiInterface );
    }
    @Override
    public void cancelRequset(){
        baseModel.cancelRequest();
    }

    @Override
    public IBaseView getBaseView(){
        return baseView;
    }

}
