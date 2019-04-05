package com.examples.myreceipts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class ItemArrayAdapter extends ArrayAdapter<InventoryItem> {
    private  static final String TAG = "ItemArrayAdapter";

   public ItemArrayAdapter(Context context, ArrayList<InventoryItem> existingItems){
       super(context, 0, existingItems);
   }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data item for this position.
        InventoryItem inventoryItem = getItem(position);

        //Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item_list, parent, false);
        }
        //look up view for data population
        TextView tvItem = convertView.findViewById(R.id.tvItem);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);
        //Populate the data into the template view using the data objects
        tvItem.setText(inventoryItem.getItemName());
        tvPrice.setText(Double.toString(inventoryItem.getItemPrice()));

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

