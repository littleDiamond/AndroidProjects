package com.examples.myreceipts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class  ItemArrayAdapter extends ArrayAdapter<InventoryItem> implements Filterable {
    private  static final String TAG = "ItemArrayAdapter";
    private ArrayList<InventoryItem> existingItems;


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

        TextView mTextItem = convertView.findViewById(R.id.tvItem);
        TextView mTextPrice = convertView.findViewById(R.id.tvPrice);

        //Populate the data into the template view using the data objects
        mTextItem.setText(inventoryItem.getItemName());
        mTextPrice.setText(Double.toString(inventoryItem.getItemPrice()));

        //return the completed view to render on screen
        return convertView;
    }

    public ArrayList<InventoryItem> getAllItems() {
        ArrayList<InventoryItem> currentItems = new ArrayList<InventoryItem>();
        for(int i = 0 ; i < getCount(); ++i){
            InventoryItem item = getItem(i);
            currentItems.add(item);
        }
        return currentItems;
    }

    public long getItemId(int position){
        return position;
    }

    private Filter mItemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<InventoryItem> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(existingItems);
            }else {
                String filterPatten = constraint.toString().toUpperCase().trim();

                for (InventoryItem item: existingItems ){
                    if (item.getItemName().toUpperCase().contains(filterPatten)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            existingItems.clear();
            existingItems.addAll((List) results.values);

        }
    };
}

