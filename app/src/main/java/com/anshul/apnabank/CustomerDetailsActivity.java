package com.anshul.apnabank;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anshul.apnabank.Data.DatabaseHandler;
import com.anshul.apnabank.Model.Customer;
import com.anshul.apnabank.Model.Transaction;
import com.anshul.apnabank.Model.TransactionList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerDetailsActivity extends AppCompatActivity {
    List<Customer> customerDetailsList;
    Customer senderCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        AtomicInteger userAmountINT = new AtomicInteger();
        DatabaseHandler handler = new DatabaseHandler(getBaseContext());

        TextView account=findViewById(R.id.details_account);
        TextView name = findViewById(R.id.details_name);
        TextView mobile = findViewById(R.id.details_mobile);
        TextView email = findViewById(R.id.details_email);
        TextView address = findViewById(R.id.details_address);
        TextView balance = findViewById(R.id.details_balance);
        Button transferButton = findViewById(R.id.transferButton);

        Intent getIntent = getIntent();
        customerDetailsList = (List<Customer>) getIntent.getSerializableExtra("list");
        senderCustomer = (Customer) getIntent.getSerializableExtra("selectedCustomer");

        if (senderCustomer != null) {
            account.setText(senderCustomer.getAccountId());
            name.setText(senderCustomer.getName());
            mobile.setText(senderCustomer.getMobileNo());
            email.setText(senderCustomer.getEmail());
            address.setText(senderCustomer.getAddress());
            balance.setText(senderCustomer.getBalance());
            userAmountINT.set(Integer.parseInt(senderCustomer.getBalance()));

        }

        transferButton.setOnClickListener(view -> {
            Intent intent = new Intent(CustomerDetailsActivity.this, TransactionActivity.class);
            intent.putExtra("list", (Serializable) customerDetailsList);
            intent.putExtra("selectedCustomer", (Serializable) senderCustomer);
            startActivity(intent);
            finish();
        });
    }
}
