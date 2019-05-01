package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPassword;
    private Button btnLogIn;
    private Button btnRegister;

    UserDbHandler dbHandler;
    public static final String USER_NAME_TEXT ="com.examples.myreceipts.EXTRA_TEXT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        dbHandler = new UserDbHandler(this);
        mUsername = findViewById(R.id.username);
        mPassword =findViewById(R.id.password);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String user = mUsername.getText().toString().trim();
                String pwd = mPassword.getText().toString().trim();
                Boolean res = dbHandler.checkuser( user,pwd );

                if(res == true){
                    Toast.makeText(LoginActivity.this, "Welcome",Toast.LENGTH_SHORT).show();
                    openActivityHome();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login error",Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }
    public void openActivityHome(){
        /**
         * Send username and password to next activity.
         */
        String helloUser = mUsername.getText().toString();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(USER_NAME_TEXT, helloUser);
        /**
         * Open activity method.
         */
        startActivity(intent);
    }

}