package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    UserDbHandler dbHandler;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mConfPassword;
    private EditText mEmail;
    private Button btnCreateAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbHandler = new UserDbHandler(this);
        mUsername = findViewById(R.id.etUsername);
        mPassword = findViewById(R.id.etPassword);
        mConfPassword = findViewById(R.id.etCnfPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String user = mUsername.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String pwd = mPassword.getText().toString().trim();
                String cnf_pwd = mConfPassword.getText().toString().trim();

                if (pwd.equals(cnf_pwd)) {
                    long value = dbHandler.addUser(user,email,pwd);
                    if(value > 0) {
                        Toast.makeText(SignUpActivity.this, "Account is created", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(SignUpActivity.this, "Registration error", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Password is not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
