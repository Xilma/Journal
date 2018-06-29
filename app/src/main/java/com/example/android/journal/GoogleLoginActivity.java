package com.example.android.journal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class GoogleLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);
    }

    public void signInGmail(View view) {
        Intent gLogin = new Intent(GoogleLoginActivity.this, MainActivity.class);
        startActivity(gLogin);
        finish();
    }
}
