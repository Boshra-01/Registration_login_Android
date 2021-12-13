package com.example.registratioandlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private EditText email_edittext, password_edittext;
    private Button signin_button;
    private TextView signup_textview;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        email_edittext=findViewById(R.id.email);
        password_edittext=findViewById(R.id.password);

        signin_button=findViewById(R.id.signin_btn);
        progressDialog= new ProgressDialog(this);
        signup_textview=findViewById(R.id.signup_textview);
        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login ();
            }
        });
        signup_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);

                finish();

            }
        });
    }
    private void Login(){
        String email = email_edittext.getText().toString();
        String password = password_edittext.getText().toString();
        if (TextUtils.isEmpty(email)) {
            email_edittext.setError("Must enter e-mail!");
            return;
        } else if (TextUtils.isEmpty(password)) {
            password_edittext.setError("Must enter password!");
            return;
        }
        progressDialog.setMessage("Please wait......");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "You are logged in!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(MainActivity.this, "Sign in failed", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

        });

    }


    }

