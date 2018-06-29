package com.example.android.journal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    private AutoCompleteTextView tv_email_signup;
    private EditText tv_password_signup;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tv_email_signup = findViewById(R.id.email_signup);
        tv_password_signup = findViewById(R.id.password_signup);

    }
}
