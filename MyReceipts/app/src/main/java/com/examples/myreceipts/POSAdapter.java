package com.examples.myreceipts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class POSAdapter extends RecyclerView.Adapter<POSAdapter.POSHolder> {
    private Context mContext;
    private ArrayList<SaleItem> mData;
    private ShoppingCart shoppingCart = new ShoppingCart();
    private static final String TAG = "POS adapter";

    public POSAdapter(Context mContext, ArrayList<SaleItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    private void updateSelectedItemQuantity(int position, int newQuantity) {
        SaleItem item = mData.get(position);
        item.setQuantity(newQuantity);
        notifyItemChanged(position);
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void updateShoppingCart(ShoppingCart updatedCart) {
        shoppingCart = updatedCart;
    }

    public void clearShoppingCart() {
        shoppingCart.clearAllItems();
    }

    @Override
    public POSHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pos_item_list, parent, false);

        return new POSHolder(view);
    }

    @Override
    public void onBindViewHolder(POSHolder holder, int position) {
        // display the item content
        SaleItem item = mData.get(position);
        holder.btnItemSelect.setText(item.getInventoryItem().getItemName());
        holder.tvMenuItemPrice.setText(String.format("$ %.2f",
                item.getInventoryItem().getItemPrice()));
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    } // end of pos adapter

    public class POSHolder extends RecyclerView.ViewHolder {

        public Button btnItemSelect, btnIncrease, btnDecrease;
        public TextView tvMenuItemPrice, tvQuantity;
        public static final int MaxQuantity = 99;
        public static final int MinQuantity = 1;

        public POSHolder(View itemView) {
            super(itemView);

            btnItemSelect = itemView.findViewById(R.id.btnInventoryItemName);
            tvMenuItemPrice = itemView.findViewById(R.id.tvMenuItemPrice);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);

            //add the item to the shopping shoppingCart
            btnItemSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SaleItem currentItem = mData.get(getAdapterPosition());

                    // add new item to shopping cart
                    // P.S. add a new clone item from sale item instead of adding the instance itself
                    //      as we will modify the quantity of POS sale item as template
                    //      and avoid changing the item added the shopping cart
                    shoppingCart.addItem(new SaleItem(currentItem.getInventoryItem(),
                            currentItem.getQuantity()));

                    int addedQuantity = Integer.parseInt(tvQuantity.getText().toString());

                    Toast.makeText(mContext, String.format("Added %s x %d",
                            currentItem.getInventoryItem().getItemName(),
                            addedQuantity), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, String.format("Add item to shopping list: %s", currentItem));
                }
            });

            btnIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaleItem currentItem = mData.get(getAdapterPosition());
                    int quantityCount = currentItem.getQuantity();
                    if (quantityCount >= MaxQuantity)
                        return;

                    ++quantityCount;
                    tvQuantity.setText(String.valueOf(quantityCount));

                    updateSelectedItemQuantity(getAdapterPosition(), quantityCount);
                }
            });

            btnDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaleItem currentItem = mData.get(getAdapterPosition());
                    int quantityCount = currentItem.getQuantity();
                    if (quantityCount <= MinQuantity)
                        return;

                    --quantityCount;
                    tvQuantity.setText(String.valueOf(quantityCount));

                    updateSelectedItemQuantity(getAdapterPosition(), quantityCount);
                }
            });
        }
    }
}
