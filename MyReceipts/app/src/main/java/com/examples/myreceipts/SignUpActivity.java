package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    private Button btn_create_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /**
         * Tap create account button and go back to login activity
         */

        btn_create_account = (Button) findViewById(R.id.btn_create_account);
        btn_create_account.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openActivityMain();
            }
        });
    }

    public void openActivityMain(){
        /**
         * Get back to Main activity.
         */
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
