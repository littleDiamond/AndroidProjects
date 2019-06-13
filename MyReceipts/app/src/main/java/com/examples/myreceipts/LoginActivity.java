package com.examples.myreceipts;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    UserDbHandler dbHandler;
    private Button mBntLogin, mBtnRegister;
    private EditText mTextUsername, mTextPassword;
    private int mCounter = 3;
    private Handler handler;

    public static final String USER_NAME_TEXT = "com.examples.myreceipts.EXTRA_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Log.d(TAG, "onCreate: Started.");

        dbHandler = new UserDbHandler(this);
        mTextUsername = findViewById(R.id.etUsername);
        mTextPassword =findViewById(R.id.etPassword);

        mBtnRegister = findViewById(R.id.btnRegister);
        mBtnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(LoginActivity.this,
                                                        SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

        mBntLogin = findViewById(R.id.btnLogIn);
        mBntLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                Boolean result = dbHandler.checkUser(user,pwd);

                // Check user input the username and password match to database info
                if(result == true){
                    Toast.makeText(LoginActivity.this,
                            "Welcome",Toast.LENGTH_SHORT).show();
                    openActivityHome();
                }
                else{
                        Toast.makeText(LoginActivity.this,
                            "Login error,No of attempts remain: "
                                    + mCounter,Toast.LENGTH_SHORT).show();

                        mCounter--;
                            if(mCounter == 0){
                                //Login fails more than 3 times, user's account will be locked
                                mBntLogin.setEnabled(false);
                                Toast.makeText(LoginActivity.this,
                                        "Login Disabled for 2 minutes",
                                        Toast.LENGTH_SHORT).show();
                                handler = new Handler();


                                handler.postDelayed(new Runnable()
                                {   @Override
                                public void run()
                                {   mBntLogin.setEnabled(true);
                                    mCounter = 3;
                                }
                                }, 120000);
                            }
                }
            }
        });
    } // end of onCreate

   public void openActivityHome(){
        // Send username and password to next activity and open next activity
       String userName = mTextUsername.getText().toString();
       Intent intent = new Intent(LoginActivity.this,
                                            HomeActivity.class);
       intent.putExtra(USER_NAME_TEXT, userName);
       startActivity(intent);
   }
}