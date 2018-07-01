package com.example.android.journal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import java.util.Objects;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView emailTextView;
    private EditText passwordEditView;
    private TextView newUserTextView;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Button loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTextView = findViewById(R.id.email);
        passwordEditView = findViewById(R.id.password);
        newUserTextView = findViewById(R.id.signup_text);
        newUserTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newUser = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(newUser);
                finish();
            }
        });

        progressBar = findViewById(R.id.login_progress);
        loginButton = findViewById(R.id.email_sign_in_button);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart(){
        super.onStart();
        //Check if user is signed in (non-null) and update
    }

    public void loginEmail(View view) {
        String email = emailTextView.getText().toString().trim();
        String password = passwordEditView.getText().toString().trim();

        if(email.isEmpty()){
            emailTextView.setError("Email is required");
            emailTextView.requestFocus();
            return;
        }

        //If email is invalid
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailTextView.setError("Please enter a valid email address");
            emailTextView.requestFocus();
            return;
        }

        if(password.isEmpty()){
            passwordEditView.setError("Password is required");
            passwordEditView.requestFocus();
            return;
        }

        //If password length is less than 6
        if(password.length()<8){
            passwordEditView.setError("Password should be a minimum of 8 characters");
            passwordEditView.requestFocus();
            return;
        }

        showProgress();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgress();
                        if (task.isSuccessful()) {
                            // Sign in is successful
                            Toast.makeText(LoginActivity.this, R.string.success_message,
                                    Toast.LENGTH_LONG).show();
                            Intent main = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(main);
                            finish();
                        }

                        // If sign in fails, display a message to the user.
                        else {
                              Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                });


    }

    public void hideProgress(){
        progressBar.setVisibility(View.GONE);
        newUserTextView.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.VISIBLE);
    }

    public void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
        newUserTextView.setVisibility(View.GONE);
        loginButton.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        LoginActivity.this.finish();
    }
}

