package com.examples.myreceipts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class ItemArrayAdapter extends ArrayAdapter<NewItem> {
    private  static final String TAG = "ItemArrayAdapter";

   public ItemArrayAdapter(Context context, ArrayList<NewItem> existingItems){
       super(context, 0, existingItems);
   }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data item for this position.
        NewItem newItem = getItem(position);

        //Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item_list, parent, false);
        }
        //look up view for data population
        TextView tvItem = convertView.findViewById(R.id.tvItem);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);
        //Populate the data into the template view using the data objects
        tvItem.setText(newItem.getItem());
        tvPrice.setText(newItem.getPrice());

        //return the completed view to render on screen
        return convertView;
    }

    public ArrayList<NewItem> getAllItems()
    {
        ArrayList<NewItem> currentItems = new ArrayList<NewItem>();
        for(int i=0 ; i<getCount(); ++i){
            NewItem item = getItem(i);
            currentItems.add(item);
        }

        return currentItems;
    }

}

