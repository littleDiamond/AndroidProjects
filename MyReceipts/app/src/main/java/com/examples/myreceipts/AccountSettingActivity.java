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
    private Button mBtnFinish;
    private EditText  mEmail,mPhoneNumber, mCompanyName, mStreetAddress, mAreaAddress, mGST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        dbHandler = new UserDbHandler(this);

        mEmail = findViewById(R.id.asEmail);
        mPhoneNumber = findViewById(R.id.asPhoneNumber);
        mCompanyName = findViewById(R.id.etCnfPassword);
        mStreetAddress = findViewById(R.id.asStreetAddress);
        mAreaAddress = findViewById(R.id.asAreaAddress);
        mGST = findViewById(R.id.asGST);

        mBtnFinish = findViewById(R.id.btnFinish);
        mBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();

//                if (email.equals(user.getEmail())) {

//                    String email = user.getEmail();
                String email = mEmail.getText().toString().trim();

                String phoneNumber = mPhoneNumber.getText().toString().trim();
                String company = mCompanyName.getText().toString().trim();
                String street = mStreetAddress.getText().toString().trim();
                String area = mAreaAddress.getText().toString().trim();
                String gst =  mGST.getText().toString().trim();

                user.setEmail(email);
                user.setPhoneNumber(Integer.parseInt(phoneNumber));
                user.setCompanyName(company);
                user.setStreetAddress(street);
                user.setAreaAddress(area);
                user.setGST(Integer.parseInt(gst));


                    long value = dbHandler.addUser(user);
                    if (value > 0) {
                        Toast.makeText(AccountSettingActivity.this,
                                "Account setting is updated",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AccountSettingActivity.this,
                                HomeActivity.class);
                        startActivity(intent);
                    }
                }

        });

    }//end of onCreate
}
