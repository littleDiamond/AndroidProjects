package com.examples.myreceipts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    private Context mContext;
    private ShoppingCart mCartData;
    private static final String TAG = "Cart adapter";
    QuantityChangedListener listener;

    public interface QuantityChangedListener {
        void onItemQuantityChange( SaleItem changedItem,
                                   int oldQuantity,
                                   int newQuantity );
    }

    public void setQuantityChangedListener( QuantityChangedListener newListener) {
        listener = newListener;
    }


    public CartAdapter(Context mContext, ShoppingCart shoppingCart){
        this.mContext = mContext;
        this.mCartData = shoppingCart;
    }

    private void updateCartItemQuantity(int position, int newQuantity, boolean skipItemChangedEvent )
    {
        SaleItem item = mCartData.getItem(position);
        int oldQuantity = item.getQuantity();
        item.setQuantity(newQuantity);

        if ( listener != null ) {
            listener.onItemQuantityChange( item, oldQuantity, newQuantity );
        }

        if ( !skipItemChangedEvent ) {
            notifyItemChanged(position);
        }
    }

    private void updateCartItemQuantity(int position, int newQuantity) {
        updateCartItemQuantity(position, newQuantity, false);
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
        public TextView tvCartItem,tvCartItemPrice,tvDelete;
        public EditText etCartQuantity;
        public static final int MaxQuantity = 99;
        public static final int MinQuantity = 1;

        public CartHolder(View itemView) {
            super(itemView);

            btnIncrease = itemView.findViewById(R.id.btnCartIncrease);
            btnDecrease = itemView.findViewById(R.id.btnCartDecrease);
            tvCartItem = itemView.findViewById(R.id.tvCartItem);
            tvCartItemPrice = itemView.findViewById(R.id.tvCartPrice);
            etCartQuantity = itemView.findViewById(R.id.etCartQuantity);
            tvDelete =itemView.findViewById(R.id.tvDelete);


            etCartQuantity.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {}

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    try {
                        int newQuantity = Integer.parseInt(s.toString());
                        updateCartItemQuantity(getAdapterPosition(), newQuantity, true);
                    } catch(NumberFormatException e) {
                    }

                }
            });

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

            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    SaleItem deleteItem = mCartData.getItem(position);
                    Toast.makeText(mContext,"Removed " + deleteItem.getQuantity()
                                    + " " + deleteItem.getInventoryItem().getItemName(),
                            Toast.LENGTH_SHORT).show();

                    mCartData.removeItem(position);
                    notifyItemRemoved(position);
                }

            });
        }
    }
}
