package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CartActivity extends AppCompatActivity {
    private ShoppingCart shoppingCart;
    private TextView tvTotalAmount;
    private RecyclerView rvCartList;
    private RecyclerView.LayoutManager manager;
    private CartAdapter mCartAdapter;
    private Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCartList = findViewById(R.id.rvCartList);
        manager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        rvCartList.setLayoutManager(manager);

        // populate the selected items into the shopping cart
        shoppingCart = getIntent().getExtras().getParcelable("ShoppingCart");

        mCartAdapter = new CartAdapter(this, shoppingCart);
        mCartAdapter.setQuantityChangedListener(new CartAdapter.QuantityChangedListener() {
            @Override
            public void onItemQuantityChange(SaleItem changedItem,
                                             int oldQuantity, int newQuantity) {

                shoppingCart.updateSaleTotal(changedItem, oldQuantity, newQuantity);
                updateSaleTotal();
            }
        });
        rvCartList.setAdapter(mCartAdapter);

        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        updateSaleTotal();

        btnCheckout = findViewById(R.id.btnCheckOut);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent receiptIntent = new Intent(CartActivity.this, ReceiptActivity.class);
                receiptIntent.putExtra("ShoppingCart", shoppingCart);
                receiptIntent.putExtra("caller", "ShoppingCart");
                startActivity(receiptIntent);
            }
        });
    }

    public void onCloseClick(View v) {
        Toast.makeText(this, "Cart is closed", Toast.LENGTH_SHORT).show();

        // pass the updated shopping cart back to the POS activity
        Intent intent = new Intent(this, PointOfSaleActivity.class);
        intent.putExtra("ShoppingCart", shoppingCart);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void updateSaleTotal() {
        if (shoppingCart != null) {
            tvTotalAmount.setText(String.format("$ %.2f", shoppingCart.getSaleTotal()));
        }
    }
}
