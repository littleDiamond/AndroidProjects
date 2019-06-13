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

        dbHandler = new UserDbHandler(this);
        user = new User();

        mUsername = findViewById(R.id.asUserName);
        mUsername.setText(dbHandler.getUserName(user.getName()));

        mEmail = findViewById(R.id.asEmail);
        mEmail.setText(dbHandler.getUserEmail(user.getEmail()));

        mPhoneNumber = findViewById(R.id.asPhoneNumber);
        mCompanyName = findViewById(R.id.asCompanyName);
        mStreetAddress = findViewById(R.id.asStreetAddress);
        mAreaAddress = findViewById(R.id.asAreaAddress);
        mGST = findViewById(R.id.asGST);

        mBtnFinish = findViewById(R.id.btnFinish);
        mBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String company = mCompanyName.getText().toString().trim();
                String street = mStreetAddress.getText().toString().trim();
                String area = mAreaAddress.getText().toString().trim();
                String phone = mPhoneNumber.getText().toString().trim();
                String gst = mGST.getText().toString().trim();

                user.setCompanyName(company);
                user.setStreetAddress(street);
                user.setAreaAddress(area);
                user.setPhoneNumber(Integer.parseInt(phone));
                user.setGST(gst);

                dbHandler.finishUserAccount(user);

                Toast.makeText(AccountSettingActivity.this,
                        "Account setting is updated",
                        Toast.LENGTH_SHORT).show();
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
