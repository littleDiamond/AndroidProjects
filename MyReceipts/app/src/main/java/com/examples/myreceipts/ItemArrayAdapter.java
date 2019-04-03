package com.examples.myreceipts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class ItemArrayAdapter<NewItem> extends ArrayAdapter<NewItem> {

    private static final String TAG = "ItemArrayAdapter";
    private Context mContext;
    int mResorce;

    /**
     * Default constructor for the ItemArrayAdapter
     * @param context
     * @param resource
     * @param objects
     */

    public ItemArrayAdapter(Context context, int resource, ArrayList<com.examples.myreceipts.NewItem> objects) {
        super(context,resource,objects);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the item info
        String addItem = getItem(position).getAddItem();
        double addPrice = getItem(position).getAddPrice();

        //create the item object with info
        NewItem newItem = new NewItem(addItem,addPrice);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResorce, parent, false);

        TextView tvItem = convertView.findViewById(R.id.tvItem);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);

        tvItem.setText(addItem);
        tvPrice.setText("" +addPrice);

        return convertView;
    }
}
