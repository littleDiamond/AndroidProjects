package com.examples.myreceipts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ReceiptHolder> {
    private Context mContext;
    private ShoppingCart mReceiptData;

    public ReceiptAdapter(Context mContext, ShoppingCart shoppingCart) {
        this.mContext = mContext;
        this.mReceiptData = shoppingCart;
    }

    @Override
    public ReceiptHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.receipt_layout, parent, false);

        return new ReceiptHolder(view);
    }

    @Override
    public void onBindViewHolder(ReceiptHolder holder, int position) {
        SaleItem item = mReceiptData.getItem(position);
        holder.receiptQTY.setText(String.valueOf(item.getQuantity()));
        holder.receiptItem.setText(item.getInventoryItem().getItemName());
        holder.receiptPrice.setText(String.format("$ %.2f",
                item.getInventoryItem().getItemPrice()));
    }

    @Override
    public int getItemCount() {
        return mReceiptData.getItemCount();
    }

    public class ReceiptHolder extends RecyclerView.ViewHolder {
        public TextView receiptQTY, receiptItem, receiptPrice;

        public ReceiptHolder(View itemView) {
            super(itemView);

            receiptQTY = itemView.findViewById(R.id.receiptQTY);
            receiptItem = itemView.findViewById(R.id.receiptItem);
            receiptPrice = itemView.findViewById(R.id.receiptPrice);
        }
    }
}
