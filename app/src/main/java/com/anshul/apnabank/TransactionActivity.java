package com.anshul.apnabank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anshul.apnabank.Data.DatabaseHandler;
import com.anshul.apnabank.Model.Customer;
import com.anshul.apnabank.Model.Transaction;
import com.anshul.apnabank.Model.TransactionList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {

    private List<Customer> customerDetailsList;
    private Customer senderCustomer;
    private List<String> receiverList;
    private Spinner receiverSpinner;
    private EditText amountEditText, searchEditText;
    private TextView senderBalanceTextView, senderAccountTextView;
    private DatabaseHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        handler = new DatabaseHandler(this);

        receiverSpinner = findViewById(R.id.receiverSpinner);
        amountEditText = findViewById(R.id.enterAmount);
        searchEditText = findViewById(R.id.searchField);
        senderAccountTextView=findViewById(R.id.details_account);
        senderBalanceTextView = findViewById(R.id.details_balance);
        Button paymentButton = findViewById(R.id.paymentButton);

        Intent getIntent = getIntent();
        customerDetailsList = (List<Customer>) getIntent.getSerializableExtra("list");
        senderCustomer = (Customer) getIntent.getSerializableExtra("selectedCustomer");

        if (senderCustomer != null) {
            senderAccountTextView.setText(senderCustomer.getAccountId());
            senderBalanceTextView.setText(senderCustomer.getBalance());
            populateReceiverList("");
        }

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                populateReceiverList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String transferAmountStr = amountEditText.getText().toString();
                if (TextUtils.isEmpty(transferAmountStr)) {
                    Toast.makeText(TransactionActivity.this, R.string.amountWarning, Toast.LENGTH_SHORT).show();
                    return;
                }

                int transferAmount = Integer.parseInt(transferAmountStr);
                if (transferAmount > Integer.parseInt(senderCustomer.getBalance())) {
                    Toast.makeText(TransactionActivity.this, R.string.amountLimitWarning, Toast.LENGTH_SHORT).show();
                    return;
                }

                String selectedCustomerId = receiverSpinner.getSelectedItem().toString();
                Customer receiverCustomer=null;

                for (Customer customer : customerDetailsList) {
                    if (customer.getAccountId().equals(selectedCustomerId)) {
                        receiverCustomer = customer;
                        break;
                    }
                }

                if (receiverCustomer == null) {
                    Toast.makeText(TransactionActivity.this, "Select a receiver", Toast.LENGTH_SHORT).show();
                    return;
                }

                performTransfer(receiverCustomer, transferAmount);
            }
        });
    }

    private void populateReceiverList(String query) {
        receiverList = new ArrayList<>();
        for (Customer customer : customerDetailsList) {
            if(!customer.getAccountId().equals(senderCustomer.getAccountId())) {
                if (customer.getAccountId().toLowerCase().contains(query.toLowerCase()) ||
                        customer.getName().toLowerCase().contains(query.toLowerCase()) ||
                        customer.getAddress().toLowerCase().contains(query.toLowerCase())) {
                    receiverList.add(customer.getAccountId());
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, receiverList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        receiverSpinner.setAdapter(adapter);
    }

    private void performTransfer(Customer receiverCustomer, int transferAmount) {
        int senderBalance = Integer.parseInt(senderCustomer.getBalance()) - transferAmount;
        int receiverBalance = Integer.parseInt(receiverCustomer.getBalance()) + transferAmount;

        senderCustomer.setBalance(String.valueOf(senderBalance));
        receiverCustomer.setBalance(String.valueOf(receiverBalance));

        handler.updateCustomer(senderCustomer);
        handler.updateCustomer(receiverCustomer);

        Toast.makeText(this, "Transaction Successful", Toast.LENGTH_SHORT).show();

        Transaction transaction = new Transaction();
        transaction.setSenderAccountId(senderCustomer.getAccountId());
        transaction.setReceiverAccountId(receiverCustomer.getAccountId());
        transaction.setAmount(transferAmount);
        transaction.setDate(new Date());

        handler.addTransaction(transaction);

        Intent intent = new Intent(TransactionActivity.this, CustomerListActivity.class);
        intent.putExtra("list", (Serializable) handler.getAllCustomers());
        startActivity(intent);
        finish();
    }
}
