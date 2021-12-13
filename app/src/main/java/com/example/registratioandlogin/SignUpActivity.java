package com.example.registratioandlogin;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private EditText email_edittext, password_edittext1, password_edittext2;
    private Button signup_button;
    private TextView signin_textview ;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        firebaseAuth = FirebaseAuth.getInstance();
        email_edittext=findViewById(R.id.email);
        password_edittext1=findViewById(R.id.password1);
        password_edittext2=findViewById(R.id.password2);
        signup_button=findViewById(R.id.signup_btn);
        progressDialog= new ProgressDialog(this);
        signin_textview=findViewById(R.id.signin_textview);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register ();
            }
        });
        signin_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
    private void Register () {
        String email = email_edittext.getText().toString();
        String password1 = password_edittext1.getText().toString();
        String password2 = password_edittext2.getText().toString();
        if (TextUtils.isEmpty(email)) {
            email_edittext.setError("Must enter e-mail!");
            return;
        } else if (TextUtils.isEmpty(password1)) {
            password_edittext1.setError("Must enter password!");
            return;
        } else if (TextUtils.isEmpty(password2)) {
            password_edittext2.setError("Must confirm password!");
            return;
        } else if (!password1.equals(password2)) {
            email_edittext.setError("Different password");
            return;
        } else if (password1.length() < 5) {
            password_edittext1.setError("Password length should be > 5");
            return;
        } else if (!isValidEmail(email)) {
            email_edittext.setError("Invalid email");
            return;
        }
        progressDialog.setMessage("Please wait......");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                   Intent intent = new Intent(SignUpActivity.this,DashboardActivity.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    Toast.makeText(SignUpActivity.this,"Sign up failed",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

        });

    }
    private Boolean isValidEmail( CharSequence target) {

    return  (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    }

