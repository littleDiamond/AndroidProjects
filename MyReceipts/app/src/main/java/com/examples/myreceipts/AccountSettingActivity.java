package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AccountSettingActivity extends AppCompatActivity {

    UserDbHandler dbHandler;
    User user;
    private Button mBtnFinish;
//    private TextView mUsername, mEmail;
    private EditText mUsername, mEmail, mPhoneNumber, mCompanyName, mStreetAddress, mAreaAddress, mGST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        dbHandler = new UserDbHandler(this);
        user = new User();

        mUsername = findViewById(R.id.asUserName);
        mUsername.setText(dbHandler.getUserName(user.getName()));

        mEmail = findViewById(R.id.asEmail);
        mEmail.setText(dbHandler.getUserEmail(user.getEmail()));

        mCompanyName = findViewById(R.id.asCompanyName);
        if(!mCompanyName.getText().toString().isEmpty()){
            mCompanyName.setText(dbHandler.getCompanyName(user.getCompanyName()));
        }

        mStreetAddress = findViewById(R.id.asStreetAddress);
        if(!mStreetAddress.getText().toString().isEmpty()){
            mStreetAddress.setText(dbHandler.getStreetAddress(user.getStreetAddress()));
        }

        mAreaAddress = findViewById(R.id.asAreaAddress);
        if(!mAreaAddress.getText().toString().isEmpty()){
            mAreaAddress.setText(dbHandler.getAreaAddress(user.getAreaAddress()));
        }

        mPhoneNumber = findViewById(R.id.asPhoneNumber);
        if(!mPhoneNumber.getText().toString().isEmpty()){
            mPhoneNumber.setText(dbHandler.getPhoneNumber(user.getPhoneNumber()));
        }

        mGST = findViewById(R.id.asGST);
        if(!mGST.getText().toString().isEmpty()){
            mGST.setText(dbHandler.getGSTNumber(user.getGST()));
        }

        mBtnFinish = findViewById(R.id.btnFinish);
        mBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mUsername.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String company = mCompanyName.getText().toString().trim();
                String street = mStreetAddress.getText().toString().trim();
                String area = mAreaAddress.getText().toString().trim();
                String phone = mPhoneNumber.getText().toString().trim();
                String gst = mGST.getText().toString().trim();

                user.setName(name);
                user.setEmail(email);
                user.setCompanyName(company);
                user.setStreetAddress(street);
                user.setAreaAddress(area);
                user.setPhoneNumber(phone);
                user.setGST(gst);

         //       dbHandler.updateUser(user);
                boolean result =dbHandler.updateUser(user.getId(), user);

                if(result){
                    Toast.makeText(AccountSettingActivity.this,
                            "Account setting is updated",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AccountSettingActivity.this,
                            "Account setting is fail",
                            Toast.LENGTH_SHORT).show();
                }


//                Intent intent = new Intent(AccountSettingActivity.this,
//                        HomeActivity.class);
//                startActivity(intent);

                Intent intent = new Intent(AccountSettingActivity.this, HomeActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }//end of onCreate
}
