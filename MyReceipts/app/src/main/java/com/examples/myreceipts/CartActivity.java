package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    ArrayList<CartItem> cartItems = new ArrayList<>();
    private TextView tvClose, tvTotalAmount;
    private RecyclerView rvCartList;
    private RecyclerView.LayoutManager manager;
    private CartAdapter mCartAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCartList = findViewById(R.id.rvCartList);
        manager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        rvCartList.setLayoutManager(manager);

        // populate the grid with inventory items
        ArrayList<SaleItem> saleItems = getIntent().getExtras().getParcelableArrayList("SaleItem");
        for( SaleItem item  : saleItems ) {
            cartItems.add( new CartItem(item, 1));
        }
        mCartAdapter = new CartAdapter(this, cartItems);
        rvCartList.setAdapter(mCartAdapter);

        tvClose = findViewById(R.id.tvClose);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent closeIntent = new Intent(CartActivity.this, PointOfSaleActivity.class);
                startActivity(closeIntent);
            }
        });

        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        calculateTotal();
    }

    public static void calculateTotal(){
        int i =0;
        double total =0;


    }

}
