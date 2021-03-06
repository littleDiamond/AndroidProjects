package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    UserDbHandler dbHandler;
    private Button mBtnCreateAccount;
    private EditText mTextUsername, mTextEmail, mTextPassword, mTextCnfPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Log.d(TAG, "onCreate: Started.");

        dbHandler = new UserDbHandler(this);

        mTextUsername = findViewById(R.id.etUsername);
        mTextEmail = findViewById(R.id.etEmail);
        mTextPassword = findViewById(R.id.etPassword);
        mTextCnfPassword = findViewById(R.id.etCnfPassword);

        mBtnCreateAccount = findViewById(R.id.btnCreateAccount);
        mBtnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                String name = mTextUsername.getText().toString().trim();
                String email = mTextEmail.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String cnf_pwd = mTextCnfPassword.getText().toString().trim();

                user.setName(name);
                user.setEmail(email);
                user.setPassword(pwd);

                Boolean result = dbHandler.doesEmailExist(email);

                //check if user's email has been register before
                if (result) {
                    Toast.makeText(SignUpActivity.this,
                            "Email exists. Try another one",
                            Toast.LENGTH_SHORT).show();
                    return;

                }

                //check if password and confirm password match
                if (pwd.equals(cnf_pwd)) {
                    long value = dbHandler.addUser(user);
                    if (value > 0) {
                        Toast.makeText(SignUpActivity.this,
                                "Account is created",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignUpActivity.this,
                                "Registration error",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this,
                            "Password is not match",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
