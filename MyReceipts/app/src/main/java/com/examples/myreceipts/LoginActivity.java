package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    UserDbHandler dbHandler;
    private EditText mTextUsername, mTextPassword;
    private Button mBntLogin, mBtnRegister;
    private int counter =3;
    public static final String USER_NAME_TEXT ="com.examples.myreceipts.EXTRA_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        dbHandler = new UserDbHandler(this);
        mTextUsername = findViewById(R.id.etUsername);
        mTextPassword =findViewById(R.id.etPassword);

        mBtnRegister = findViewById(R.id.btnRegister);
        mBtnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
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

                if(result == true){
                    Toast.makeText(LoginActivity.this, "Welcome",Toast.LENGTH_SHORT).show();
                    openActivityHome();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login error,No of attempts remain: "
                                                                + counter,Toast.LENGTH_SHORT).show();
                    counter--;
                    if(counter == 0)
                        mBntLogin.setEnabled(false);    //Login fails more than 3 times, user's account will be locked
                }

            }
        });
    } // end of onCreate

   public void openActivityHome(){
        // Send username and password to next activity and open next activity
       String userName = mTextUsername.getText().toString();
       Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
       intent.putExtra(USER_NAME_TEXT, userName);
       startActivity(intent);
   }
}