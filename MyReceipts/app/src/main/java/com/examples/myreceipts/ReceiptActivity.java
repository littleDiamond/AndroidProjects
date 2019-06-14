package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReceiptActivity extends AppCompatActivity {
    private UserDbHandler dbHandler;
    private User user;
    private ShoppingCart order;
    private Receipt newReceipt;
    private RecyclerView rvReceiptList;
    private RecyclerView.LayoutManager manager;
    private ReceiptAdapter mReceiptAdapter;
    private TextView tvCompany, tvStreetAddress,
            tvAreaAddress, tvPhoneNumber, tvGST,
            tvDate, tvOrderNumber,
            tvGSTAmount, tvTotal;

    private String caller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        MyReceiptsApplication app = (MyReceiptsApplication)getApplication();
        String userName = app.getCurrentUser();

        dbHandler = new UserDbHandler(this);
        user = dbHandler.getUserByName(userName);

        // =============  User Data =======================

        tvCompany = findViewById(R.id.tvCompany);
        tvCompany.setText(user.getCompanyName());

        tvStreetAddress = findViewById(R.id.tvStreetAddress);
        tvStreetAddress.setText(user.getStreetAddress());

        tvAreaAddress = findViewById(R.id.tvAreaAddress);
        tvAreaAddress.setText(user.getAreaAddress());

        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvPhoneNumber.setText(user.getPhoneNumber());

        tvGST = findViewById(R.id.tvGST);
        tvGST.setText(user.getGST());

        // =============  Receipt data =======================

        order = getIntent().getExtras().getParcelable("ShoppingCart");
        caller = getIntent().getExtras().getString("caller");

        newReceipt = new Receipt(userName, order);

        tvDate = findViewById(R.id.tvDate);
        tvDate.setText(newReceipt.getDateTimeStr());

        tvOrderNumber = findViewById(R.id.tvOrderNumber);
        tvOrderNumber.setText(String.valueOf(newReceipt.getReceiptID()));

        rvReceiptList = findViewById(R.id.rvReceiptList);
        manager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        rvReceiptList.setLayoutManager(manager);

        mReceiptAdapter = new ReceiptAdapter(this, order);
        rvReceiptList.setAdapter(mReceiptAdapter);

        tvGSTAmount = findViewById(R.id.tvGSTAmount);
        tvTotal = findViewById(R.id.tvTotal);
        updateSaleTotal();
    }

    public void onCloseReceiptClick(View v) {
        if ( caller.equals("ShoppingCart") )
        {
            MyReceiptsApplication app = (MyReceiptsApplication)getApplication();
            app.saveReceipt(newReceipt);

            Intent intent = new Intent(this, PointOfSaleActivity.class);
            setResult(RESULT_OK, intent);
        }
        else if ( caller.equals("ReceiptList") )
        {
            Intent intent = new Intent(this, ReceiptListActivity.class);
            setResult(RESULT_OK, intent);
        }

        finish();
    }
    public void updateSaleTotal() {
        if (order != null) {
            double total = order.getSaleTotal();
            tvGSTAmount.setText(String.format("$ %.2f", total*0.15));
            tvTotal.setText(String.format("$ %.2f", total));
        }
    }

}
