package com.examples.myreceipts;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CartActivity extends AppCompatActivity {
    private ShoppingCart shoppingCart;
    private TextView tvClose, tvTotalAmount;
    private RecyclerView rvCartList;
    private RecyclerView.LayoutManager manager;
    private CartAdapter mCartAdapter;

    private CartAdapter.QuantityChangedListener saleItemChangedListener;


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
        mCartAdapter.setQuantityChangedListener(new CartAdapter.QuantityChangedListener(){
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
    }

    public void onCloseClick(View v){
        tvClose = this.findViewById(R.id.tvClose);
        Toast.makeText(this,"Cart is closed", Toast.LENGTH_SHORT).show();
        ShoppingCart currentItem = shoppingCart;
        Intent intent = new Intent(this,PointOfSaleActivity.class);

        intent.putExtra("ShoppingCart",currentItem);
        setResult(RESULT_OK);
        finish();

    }

    public void updateSaleTotal() {
        if ( shoppingCart != null ) {
            tvTotalAmount.setText(String.format("$ %.2f", shoppingCart.getSaleTotal()));
        }
    }

}
