package com.examples.myreceipts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class POSAdapter extends RecyclerView.Adapter<POSAdapter.POSHolder> {
    private Context mContext;
    private ArrayList<InventoryItem> mData;

    public POSAdapter(Context mContext, ArrayList<InventoryItem> mData){
        this.mContext = mContext;
        this.mData = mData;
    }


    @Override
    public POSHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.cardview_pos_item,parent,false);
        return new POSHolder(view);
    }

    @Override
    public void onBindViewHolder(POSHolder holder, int position) {
        holder.tvMenuItem.setText(mData.get(position).getItemName());
        holder.tvMenuItemPrice.setText(Double.toString(mData.get(position).getItemPrice()));


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class POSHolder extends RecyclerView.ViewHolder{

        TextView tvMenuItem;
        TextView tvMenuItemPrice;

        public POSHolder(View itemView){
            super(itemView);

            tvMenuItem = itemView.findViewById(R.id.tvMenuItem);
            tvMenuItemPrice = itemView.findViewById(R.id.tvMenuItemPrice);
        }
    }
}
