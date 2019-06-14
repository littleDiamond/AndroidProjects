package com.examples.myreceipts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class InventoryAdapter extends ArrayAdapter<InventoryItem> implements Filterable {
    private static final String TAG = "InventoryAdapter";

    // a backup for all the original items in the list view
    private ArrayList<InventoryItem> originalItems;

    // the filtered result from the original items
    private List<InventoryItem> filteredList;


    public InventoryAdapter(Context context, ArrayList<InventoryItem> originalItems) {
        super(context, 0, originalItems);

        // important to note that we use filtered list to reference to the current content
        // instead of assigning to originalItems.
        // because we will update the filteredList with the filter and adapter will push the update
        // to list view
        this.filteredList = originalItems;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data item for this position.
        InventoryItem inventoryItem = getItem(position);

        //Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inventory_item_list_layout,
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
        for (int i = 0; i < getCount(); ++i) {
            InventoryItem item = getItem(i);
            currentItems.add(item);
        }
        return currentItems;
    }

    public long getItemId(int position) {
        return position;
    }

    public Filter getFilter() {
        Filter mItemFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                // save a copy of the original unfiltered items
                if (originalItems == null) {
                    originalItems = new ArrayList<>(filteredList);
                }

                // clear the current filter
                filteredList.clear();

                if (constraint == null || constraint.length() == 0) {
                    //   newFilterList .addAll(originalItems);
                    filteredList.addAll(originalItems);
                } else {
                    String filterPatten = constraint.toString().toLowerCase().trim();

                    for (InventoryItem item : originalItems) {
                        if (!item.getItemName().isEmpty() &&
                                item.getItemName().toLowerCase().contains(filterPatten)) {
                            //  newFilterList .add(item);
                            filteredList.add(item);
                        }
                    }
                }

                // create the result from filtered list
                FilterResults results = new FilterResults();

                // need to set both count and values
                results.count = filteredList.size();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                // update the filtered list with results
                filteredList = (List) results.values;
                notifyDataSetChanged();  // notifies the data with new filtered values

            }
        };

        return mItemFilter;
    }


}

