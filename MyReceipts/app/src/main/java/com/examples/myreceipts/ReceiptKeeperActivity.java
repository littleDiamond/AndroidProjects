package com.examples.myreceipts;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ReceiptKeeperActivity extends AppCompatActivity {

        private static final String TAG = "ReceiptKeeperActivity";
        private ListView mReceiptList;
        private TextView mReceiptsTotal;

        private ArrayList<Receipt> myReceipts = new ArrayList<>();
        private ReceiptKeeperAdapter mAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_receipt_keeper);
            Log.d(TAG, "onCreate: Started.");

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ActionBar ab = getSupportActionBar();
            ab.setDisplayHomeAsUpEnabled(true);

            mReceiptList = findViewById(R.id.rvReceiptList);
            mReceiptsTotal = findViewById(R.id.tvTotal);

            // get the inventory items for current user
            MyReceiptsApplication app = (MyReceiptsApplication)getApplication();
            myReceipts = app.getUserReceipts();

            // create the mAdapter to convert the array to views
            mAdapter = new ReceiptKeeperAdapter(ReceiptKeeperActivity.this, myReceipts);
            mReceiptList.setAdapter(mAdapter); //attach the mAdapter to a ListView

            mReceiptList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // open the receipt view activity
                    Receipt currentReceipt = (Receipt) mReceiptList.getItemAtPosition(i);
                    Intent receiptIntent = new Intent(ReceiptKeeperActivity.this, ReceiptActivity.class);
                    receiptIntent.putExtra("ShoppingCart", currentReceipt.getOrder());
                    receiptIntent.putExtra("caller", "ReceiptList");
                    startActivity(receiptIntent);
                }
            });
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_receipt_list, menu);

            // Associate searchable configuration with the SearchView
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) menu.findItem(R.id.searchReceipt).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // return false as we do all the filtering via text change instead of submit button
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mAdapter.getFilter().filter(newText);

                    // return true as we implement this function
                    return true;
                }
            };

            searchView.setOnQueryTextListener(queryTextListener);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.sort_date:
                    sortArrayListByDate();
                    return true;

                case R.id.sort_price:
                    sortArrayListByPrice();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        //Sort out the array list order by item name
        private void sortArrayListByDate() {
            Collections.sort(myReceipts, new Comparator<Receipt>() {
                @Override
                public int compare(Receipt r1, Receipt r2) {
                    // ascending order
                    return r1.getDateTime().compareTo(r2.getDateTime());
                }
            });
            mAdapter.notifyDataSetChanged();
        }

        //Sort out the array list order by item name
        private void sortArrayListByPrice() {
            Collections.sort(myReceipts, new Comparator<Receipt>() {
                @Override
                public int compare(Receipt r1, Receipt r2) {
                    return Double.compare(r1.getOrder().getSaleTotal(), r2.getOrder().getSaleTotal());
                }
            });
            mAdapter.notifyDataSetChanged();
        }
}
