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
    private TextView mUsername, mEmail;
    private EditText mPhoneNumber, mCompanyName, mStreetAddress, mAreaAddress, mGST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        MyReceiptsApplication app = (MyReceiptsApplication) getApplication();
        String userName = app.getCurrentUser();

        dbHandler = new UserDbHandler(this);
        user = dbHandler.getUserByName(userName);

        mUsername = findViewById(R.id.asUserName);
        mUsername.setText(userName);

        if (user != null) {
            mEmail = findViewById(R.id.asEmail);
            mEmail.setText(user.getEmail());

            mCompanyName = findViewById(R.id.asCompanyName);
            mCompanyName.setText(user.getCompanyName());

            mStreetAddress = findViewById(R.id.asStreetAddress);
            mStreetAddress.setText(user.getStreetAddress());

            mAreaAddress = findViewById(R.id.asAreaAddress);
            mAreaAddress.setText(user.getAreaAddress());

            mPhoneNumber = findViewById(R.id.asPhoneNumber);
            mPhoneNumber.setText(user.getPhoneNumber());

            mGST = findViewById(R.id.asGST);
            mGST.setText(user.getGST());
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
                boolean result = dbHandler.updateUser(user);

                if (result) {
                    Toast.makeText(AccountSettingActivity.this,
                            "Account setting is updated",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AccountSettingActivity.this,
                            "Account setting is fail",
                            Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(AccountSettingActivity.this, HomeActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }//end of onCreate
}
