package com.examples.myreceipts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    private Context mContext;
    private ShoppingCart mCartData;

    private static final String TAG = "Cart adapter";


    public CartAdapter(Context mContext, ShoppingCart shoppingCart){
        this.mContext = mContext;
        this.mCartData = shoppingCart;
    }

    private void updateCartItemQuantity(int position, int newQuantity) {
        SaleItem item = mCartData.getItem(position);
        item.setQuantity(newQuantity);

        Log.d(TAG, mCartData.toString());
        notifyItemChanged(position);
    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_list,parent,false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(CartHolder holder, int position) {
        SaleItem item = mCartData.getItem(position);

        // display an sale item in shopping cart
        holder.tvCartItem.setText(item.getInventoryItem().getItemName());
        holder.tvCartItemPrice.setText(String.format("$ %.2f",
                item.getInventoryItem().getItemPrice()));
        holder.etCartQuantity.setText(String.valueOf(item.getQuantity()));
    }


    @Override
    public int getItemCount() {
        return mCartData.getItemCount();
    }

    public class CartHolder extends RecyclerView.ViewHolder{
        public Button btnIncrease, btnDecrease;
        public TextView tvCartItem,tvCartItemPrice;
        public EditText etCartQuantity;
        public ImageView ivDelete;
        public static final int MaxQuantity = 99;
        public static final int MinQuantity = 1;

        public CartHolder(View itemView) {
            super(itemView);

            btnIncrease = itemView.findViewById(R.id.btnCartIncrease);
            btnDecrease = itemView.findViewById(R.id.btnCartDecrease);
            tvCartItem = itemView.findViewById(R.id.tvCartItem);
            tvCartItemPrice = itemView.findViewById(R.id.tvCartPrice);
            etCartQuantity = itemView.findViewById(R.id.etCartQuantity);
            ivDelete =itemView.findViewById(R.id.ivDelete);

//            etCartQuantity.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public  boolean onTouch(View v, MotionEvent event) {
//                    switch (event.getAction()){
//                        case MotionEvent.ACTION_DOWN:
//                            count++;
//                            if( count == 1){
//                                etCartQuantity.requestFocus();
//                                InputMethodManager manager = Context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                                manager.showSoftInput(etCartQuantity, 0);
//                            }
//                            break;
//                            default:
//                                break;
//                    }
//                    return true;
//                }
//            });

            btnIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaleItem currentItem = mCartData.getItem(getAdapterPosition());
                    int quantityCount = currentItem.getQuantity();
                    if ( quantityCount >= MaxQuantity )
                        return;

                    ++quantityCount;
                    etCartQuantity.setText(String.valueOf(quantityCount));

                    updateCartItemQuantity(getAdapterPosition(), quantityCount);
                }
            });

            btnDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaleItem currentItem = mCartData.getItem(getAdapterPosition());
                    int quantityCount = currentItem.getQuantity();
                    if ( quantityCount <= MinQuantity )
                        return;

                    --quantityCount;
                    etCartQuantity.setText(String.valueOf(quantityCount));

                    updateCartItemQuantity(getAdapterPosition(), quantityCount);
                }
            });
        }

    }
}
