package com.example.android.journal;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void emailSignIn(View view) {
        Intent eLogin = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(eLogin);
        finish();
    }

    public void gmailSignIn(View view) {
        Intent gLogin = new Intent(SplashActivity.this, GoogleLoginActivity.class);
        startActivity(gLogin);
        finish();
    }
}
