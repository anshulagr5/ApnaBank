package com.anshul.apnabank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.anshul.apnabank.Adapter.TransactionAdapter;
import com.anshul.apnabank.Data.DatabaseHandler;
import com.anshul.apnabank.Model.Customer;
import com.anshul.apnabank.Model.Transaction;
import com.anshul.apnabank.Model.TransactionList;

import java.util.ArrayList;
import java.util.List;

public class TransactionDetailsActivity extends AppCompatActivity {
    private List<Transaction> transactionList;
    private RecyclerView transactionRV;
    private TransactionAdapter transactionAdapter;
    private DatabaseHandler handler;
    TextView noTransaction;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        transactionRV = findViewById(R.id.transactionRV);
        transactionRV.setHasFixedSize(true);
        transactionRV.setLayoutManager(new LinearLayoutManager(this));

        handler = new DatabaseHandler(this);
        transactionList = handler.getAllTransactions();
        transactionAdapter = new TransactionAdapter(transactionList);
        transactionRV.setAdapter(transactionAdapter);

        searchView = findViewById(R.id.searchView);
        noTransaction=findViewById(R.id.noTransactionTextView);

        if(transactionList.isEmpty())
        {
            noTransaction.setVisibility(View.VISIBLE);
        }

        // Setup search functionality
        setupSearchView();

        // Make the search bar fully interactive
        makeSearchBarInteractive();
    }
    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCustomers(newText);
                return true;
            }
        });
    }

    private void makeSearchBarInteractive() {
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false); // Expand the search view when clicked
            }
        });
    }

    private void filterCustomers(String query) {
        List<Transaction> filteredList = new ArrayList<>();

        for (Transaction transaction : transactionList) {
            if (transaction.getSenderAccountId().toLowerCase().contains(query.toLowerCase()) ||
                    transaction.getReceiverAccountId().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(transaction);
            }
        }

        transactionAdapter.filterList(filteredList);
    }

}