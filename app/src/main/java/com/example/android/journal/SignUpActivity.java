package com.example.android.journal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private AutoCompleteTextView tv_email_signup;
    private EditText tv_password_signup;
    private TextView signinTextView;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tv_email_signup = findViewById(R.id.email_signup);
        tv_password_signup = findViewById(R.id.password_signup);
        signinTextView = findViewById(R.id.signin_text);
        signinTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent oldUser = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(oldUser);
                finish();
            }
        });

        progressBar = findViewById(R.id.signup_progress);
        signupButton = findViewById(R.id.sign_up_button_email);

        mAuth = FirebaseAuth.getInstance();

    }

    public void registerUser(View view) {
        String email = tv_email_signup.getText().toString().trim();
        String password = tv_password_signup.getText().toString().trim();

        if(email.isEmpty()){
            tv_email_signup.setError("Email is required");
            tv_email_signup.requestFocus();
            return;
        }

        //If email is invalid
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            tv_email_signup.setError("Please enter a valid email address");
            tv_email_signup.requestFocus();
            return;
        }

        if(password.isEmpty()){
            tv_password_signup.setError("Password is required");
            tv_password_signup.requestFocus();
            return;
        }

        //If password length is less than 6
        if(password.length()<8){
            tv_password_signup.setError("Password should be a minimum of 8 characters");
            tv_password_signup.requestFocus();
            return;
        }

        showProgress();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgress();
                        if (task.isSuccessful()) {
                            // Sign in is successful
                            Toast.makeText(SignUpActivity.this, R.string.success_message,
                                    Toast.LENGTH_LONG).show();
                            Intent mainPage = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(mainPage);
                            finish();
                        }

                        // If sign in fails, display a message to the user.
                        else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(SignUpActivity.this, R.string.user_exists,
                                        Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    public void hideProgress(){
        progressBar.setVisibility(View.GONE);
        signinTextView.setVisibility(View.VISIBLE);
        signupButton.setVisibility(View.VISIBLE);
    }

    public void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
        signinTextView.setVisibility(View.GONE);
        signupButton.setVisibility(View.GONE);
    }
}
