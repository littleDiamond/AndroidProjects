package com.examples.myreceipts;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        // populate the grid with inventory items
        shoppingCart = getIntent().getExtras().getParcelable("ShoppingCart");

        mCartAdapter = new CartAdapter(this, shoppingCart);
//        mCartAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onItemRangeChanged(int positionStart, int itemCount) {
//                super.onItemRangeChanged(positionStart, itemCount);
//                // update the sales total when any sale item quantity is updated
//                updateSaleTotal();
//            }
//        });

        mCartAdapter.setQuantityChangedListener(new CartAdapter.QuantityChangedListener(){
            @Override
            public void onItemQuantityChange(SaleItem changedItem,
                                             int oldQuantity, int newQuantity) {

                shoppingCart.updateSaleTotal(changedItem, oldQuantity, newQuantity);
                updateSaleTotal();
            }
        });

        rvCartList.setAdapter(mCartAdapter);

        tvClose = findViewById(R.id.tvClose);

        // FIXME: instead of creating an empty POS activity, we need to set the activity type to stack
        // so that we can go back and maintain the data (singleTop ?? )
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent closeIntent = new Intent(CartActivity.this, PointOfSaleActivity.class);
                startActivity(closeIntent);
            }
        });

        tvTotalAmount = findViewById(R.id.tvTotalAmount);

        updateSaleTotal();
    }

    public void updateSaleTotal()
    {
        if ( shoppingCart != null )
        {
            tvTotalAmount.setText(String.format("$ %.2f", shoppingCart.getSaleTotal()));
        }
    }

}
