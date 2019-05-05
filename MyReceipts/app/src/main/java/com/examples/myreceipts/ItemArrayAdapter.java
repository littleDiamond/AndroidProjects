package com.examples.myreceipts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class  ItemArrayAdapter extends RecyclerView.Adapter<ItemArrayAdapter.ItemArrayHolder> {
    private static final String TAG = "ItemArrayAdapter";
    private ArrayList<InventoryItem> mItemList;
    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    public static  class ItemArrayHolder extends RecyclerView.ViewHolder{
        public TextView mTextItem, mTextPrice;
//        private InventoryItem item;
//        private Context context;

        public ItemArrayHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
//            this.context = context; //Set the context
            this.mTextItem = itemView.findViewById(R.id.tvItem);    //Set up the UI widgets of the holder
            this.mTextPrice = itemView.findViewById(R.id.tvPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });  //Set the "onClick" listener of the holder
        }

        public ItemArrayAdapter(ArrayList<InventoryItem> itemArrayList){
            mItemList = itemArrayList;
        }
        @Override
        public ItemArrayHolder onCreateViewHolder(ViewGroup parent, int viewTpye){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_list,
                    parent, false);
            ItemArrayHolder itemHolder = new ItemArrayHolder(v, mListener);
            return  itemHolder;
        }

        @Override
        public void onBindViewHolder(ItemArrayHolder holder, int position){
            InventoryItem currentItem  = mItemList.get(position);

            holder.mTextItem.setText(currentItem.getItemName());
            holder.mTextPrice.setText(Double.parseDouble(currentItem.getItemPrice()));
        }
        @Override
        public int getItemCount(){

        }
    }
   public ItemArrayAdapter(Context context, ArrayList<InventoryItem> existingItems){
       super(context, 0, existingItems);
   }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data item for this position.
        InventoryItem inventoryItem = getItem(position);

        //Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item_list,
                                                                parent, false);
        }
        //look up view for data population
        TextView mTextItem = convertView.findViewById(R.id.tvItem);
        TextView mTextPrice = convertView.findViewById(R.id.tvPrice);
        //Populate the data into the template view using the data objects
        mTextItem.setText(inventoryItem.getItemName());
        mTextPrice.setText(Double.toString(inventoryItem.getItemPrice()));

        //return the completed view to render on screen
        return convertView;
    }

    public ArrayList<InventoryItem> getAllItems()
    {
        ArrayList<InventoryItem> currentItems = new ArrayList<InventoryItem>();
        for(int i=0 ; i<getCount(); ++i){
            InventoryItem item = getItem(i);
            currentItems.add(item);
        }

        return currentItems;
    }

}

