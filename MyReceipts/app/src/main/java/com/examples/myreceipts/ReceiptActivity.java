package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ReceiptActivity extends AppCompatActivity {
    UserDbHandler dbHandler;
    User user;
    private ShoppingCart receiptItems;
    private RecyclerView rvReceiptList;
    private RecyclerView.LayoutManager manager;
    private ReceiptAdapter mReceiptAdapter;
    private TextView tvCompany, tvStreetAddress,
            tvAreaAddress, tvPhoneNumber, tvGST,
            tvDate, tvTime, tvOrderNumber,
            tvGSTAmount, tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        dbHandler = new UserDbHandler(this);
        user = new User();

        tvCompany = findViewById(R.id.tvCompany);
        tvCompany.setText(dbHandler.getCompanyName(user.getCompanyName()));

        tvStreetAddress = findViewById(R.id.tvStreetAddress);
        tvStreetAddress.setText(dbHandler.getStreetAddress(user.getStreetAddress()));

        tvAreaAddress = findViewById(R.id.tvAreaAddress);
        tvAreaAddress.setText(dbHandler.getAreaAddress(user.getAreaAddress()));

        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvPhoneNumber.setText(dbHandler.getPhoneNumber(Integer.toString(user.getPhoneNumber())));


        tvGST = findViewById(R.id.tvGST);
        tvGST.setText(dbHandler.getGSTNumber(user.getGST()));

        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvOrderNumber = findViewById(R.id.tvOrderNumber);
        tvGSTAmount = findViewById(R.id.tvGSTAmount);

        rvReceiptList = findViewById(R.id.rvReceiptList);
        manager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        rvReceiptList.setLayoutManager(manager);

        receiptItems = getIntent().getExtras().getParcelable("ShoppingCart");
        mReceiptAdapter = new ReceiptAdapter(this, receiptItems);
        rvReceiptList.setAdapter(mReceiptAdapter);

        tvTotal = findViewById(R.id.tvTotal);
        updateSaleTotal();
    }

    public void onCloseReceiptClick(View v) {
        Toast.makeText(this, "Receipt is closed", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, PointOfSaleActivity.class);
        startActivity(intent);

        finish();
    }
    public void updateSaleTotal() {
        if (receiptItems != null) {
            tvTotal.setText(String.format("$ %.2f", receiptItems.getSaleTotal()));
        }
    }

}
