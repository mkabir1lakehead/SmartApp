package com.longfeizeng.smartapp.model;

import android.util.Log;

import com.longfeizeng.smartapp.presenter.IBasePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http2.ErrorCode;

import java.io.IOException;
import java.lang.reflect.Method;
/**
 * Created by zlf on 17/03/18.
 */

public class BaseModel implements IBaseModel {

    private String apiInterface;
    private IBasePresenter basePresenter;

    public BaseModel(IBasePresenter basePresenter){
        this.basePresenter = basePresenter;
    }

    @Override
    public void sendRequestToServer() {

        Log.i("run","toServer");

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse( "application/json; charset=utf-8" );
        RequestBody requestBody = RequestBody.create(JSON, basePresenter.getParams().toString() );
        final Request request = new Request.Builder().url( apiInterface ).post( requestBody ).build();

        okHttpClient.newCall( request ).enqueue( new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                basePresenter.okhttpError( 404,null,apiInterface );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i( "run","response" );
                String jsonData = response.body().string();
                String errorNum = response.header( "errorNum" );//errorNum = "0",相映成功
                Log.i( "errorNum: ",errorNum +"\n" + jsonData );
                basePresenter.accessSucceed( errorNum, jsonData );
            }
        } );
    }

    @Override
    public void setApiInterface(String apiInterface) {
        this.apiInterface = apiInterface;
    }

    @Override
    public void cancelRequest() {

    }
}
