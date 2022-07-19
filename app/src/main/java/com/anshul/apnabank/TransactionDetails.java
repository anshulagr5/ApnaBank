package com.anshul.apnabank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import com.anshul.apnabank.Adapter.TransactionAdapter;
import com.anshul.apnabank.Model.Transaction;
import com.anshul.apnabank.Model.TransactionList;

import java.util.List;

public class TransactionDetails extends AppCompatActivity {
    List<Transaction> transactionList;
    private RecyclerView transactionRV;
    private TransactionAdapter transactionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        getSupportActionBar().hide();

        transactionRV = findViewById(R.id.transactionRV);
        transactionRV.setHasFixedSize(true);
        transactionRV.setLayoutManager(new LinearLayoutManager(this));

        transactionList = TransactionList.getTransactionList();
        transactionAdapter = new TransactionAdapter(transactionList);
        transactionRV.setAdapter(transactionAdapter);
    }
}