package com.example.zlf.test2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class User extends AppCompatActivity {
    private Button mReturnButton;
    Intent intent3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        mReturnButton = (Button)findViewById(R.id.returnback);

    }
    public void back_to_login(View view) {
        //setContentView(R.layout.login);
        intent3 = new Intent(User.this,LocalService.class) ;
        startService(intent3);

    }
    public void stops(View view) {
        //setContentView(R.layout.login);
        ;
        stopService(intent3);
        finish();

    }
}
