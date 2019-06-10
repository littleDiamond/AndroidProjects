package com.examples.myreceipts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    private Context mContext;
    private ArrayList<CartItem> mCartData;
    private static final String TAG = "POS adapter";


    public CartAdapter(Context mContext, ArrayList<CartItem> mCartData){
        this.mContext = mContext;
        this.mCartData = mCartData;
    }

//    private void updateCartItemQuantity(int position, SaleItem newQuantity) {
//        CartItem item = mCartData.get(position);
//        item.setSaleItem(newQuantity);
//        notifyItemChanged(position);
//    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_list,parent,false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(CartHolder holder, int position) {
        CartItem item = mCartData.get(position);
        holder.tvCartItem.setText(item.getSaleItem().getInventoryItem().getItemName());
        holder.tvCartItemPrice.setText(String.format("$ %.2f",
                item.getSaleItem().getInventoryItem().getItemPrice()));
        holder.tvCartQuantity.setText(String.valueOf(item.getSaleItem().getQuantity()));

    }

    @Override
    public int getItemCount() {
        return mCartData.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder{
        public Button btnIncrease, btnDecrease;
        public TextView tvCartItem,tvCartItemPrice, tvCartQuantity;
        public static final int MaxQuantity = 99;
        public static final int MinQuantity = 1;


        public CartHolder(View itemView) {
            super(itemView);

            btnIncrease = itemView.findViewById(R.id.btnCartIncrease);
            btnDecrease = itemView.findViewById(R.id.btnCartDecrease);
            tvCartItem = itemView.findViewById(R.id.tvCartItem);
            tvCartItemPrice = itemView.findViewById(R.id.tvCartPrice);
            tvCartQuantity = itemView.findViewById(R.id.tvCartQuantity);

//            btnIncrease.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    CartItem currentItem = mCartData.get(getAdapterPosition());
//                    int quantityCount = currentItem.getSaleItem().getQuantity();
//                    if ( quantityCount >= MaxQuantity )
//                        return;
//
//                    ++quantityCount;
//                    tvCartQuantity.setText(String.valueOf(quantityCount));
//
//                    updateCartItemQuantity(getAdapterPosition(), quantityCount);
//                }
//            });
//
//            btnDecrease.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    CartItem currentItem = mCartData.get(getAdapterPosition());
//                    int quantityCount = currentItem.getSaleItem().getQuantity();
//                    if ( quantityCount <= MinQuantity )
//                        return;
//
//                    --quantityCount;
//                    tvCartQuantity.setText(String.valueOf(quantityCount));
//
//                    updateCartItemQuantity(getAdapterPosition(), quantityCount);
//                }
//            });
        }

    }
}
