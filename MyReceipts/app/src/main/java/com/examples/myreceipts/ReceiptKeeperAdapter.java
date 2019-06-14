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


public class ReceiptKeeperAdapter extends ArrayAdapter<Receipt> implements Filterable {
    private static final String TAG = "InventoryAdapter";

    // a backup for all the original items in the list view
    private ArrayList<Receipt> allReceipts;

    // the filtered result from the original items
    private List<Receipt> filteredList;


    public ReceiptKeeperAdapter(Context context, ArrayList<Receipt> newReceipts) {
        super(context, 0, newReceipts);

        // important to note that we use filtered list to reference to the current content
        // instead of assigning to allReceipts.
        // because we will update the filteredList with the filter and adapter will push the update
        // to list view
        this.filteredList = newReceipts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data item for this position.
        Receipt receipt = getItem(position);

        //Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.receipt_summary_layout,
                    parent, false);
        }

        TextView mTextDate = convertView.findViewById(R.id.receiptDate);
        TextView mTextDescription = convertView.findViewById(R.id.receiptDescription);
        TextView mTextPrice = convertView.findViewById(R.id.receiptPrice);

        //Populate the data into the template view using the data objects
        mTextDate.setText(receipt.getDateStr());
        mTextDescription.setText(receipt.getDescription());
        mTextPrice.setText("$" + Double.toString(receipt.getOrder().getSaleTotal()));

        //return the completed view to render on screen
        return convertView;
    }

    public ArrayList<Receipt> getReceipts() {
        ArrayList<Receipt> currentItems = new ArrayList<Receipt>();
        for (int i = 0; i < getCount(); ++i) {
            Receipt item = getItem(i);
            currentItems.add(item);
        }
        return currentItems;
    }

    public long getItemId(int position) {
        return position;
    }

    public Filter getFilter() {
        Filter mReceiptFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                // save a copy of the original unfiltered items
                if (allReceipts == null) {
                    allReceipts = new ArrayList<>(filteredList);
                }

                // clear the current filter
                filteredList.clear();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(allReceipts);
                } else {
                    String filterPatten = constraint.toString().toLowerCase().trim();

                    for (Receipt receipt : allReceipts) {
                        String searchableContent = receipt.getDescription().toLowerCase() + receipt.getDateTimeStr();
                        if (!searchableContent.isEmpty() &&
                                searchableContent.contains(filterPatten)) {
                            filteredList.add(receipt);
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

        return mReceiptFilter;
    }
}

