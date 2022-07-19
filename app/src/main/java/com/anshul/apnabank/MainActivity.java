package com.anshul.apnabank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.anshul.apnabank.Data.DatabaseHandler;
import com.anshul.apnabank.Model.CustomerDetails;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button allCustomersButton;
    List<CustomerDetails> customerDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        allCustomersButton = findViewById(R.id.allCustomersButton);

        DatabaseHandler handler = new DatabaseHandler(MainActivity.this);

        handler.addCustomer(new CustomerDetails("Adarsh", "adarsh@gmail.com", "10000"));
        handler.addCustomer(new CustomerDetails("Anshika", "anshika@gmail.com", "200000"));
        handler.addCustomer(new CustomerDetails("Pranav", "pranav@gmail.com", "100000"));
        handler.addCustomer(new CustomerDetails("Joy", "joy@gmail.com", "40000"));
        handler.addCustomer(new CustomerDetails("Sakshi", "sakshi@gmail.com", "250000"));
        handler.addCustomer(new CustomerDetails("Sneha", "sneha@gmail.com", "700000"));
        handler.addCustomer(new CustomerDetails("Ankit", "ankit@gmail.com", "450000"));
        handler.addCustomer(new CustomerDetails("Aditya", "aditya@gmail.com", "69000"));
        handler.addCustomer(new CustomerDetails("Garima", "garima@gmail.com", "500000"));
        handler.addCustomer(new CustomerDetails("Riya", "riya@gmail.com", "300000"));

        customerDetailsList = handler.getAllCustomers();
        Log.d("TAG", "onCreate: " + customerDetailsList.size());

        allCustomersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CustomerList.class);
                intent.putExtra("list", (Serializable) customerDetailsList);
                startActivity(intent);
            }
        });
    }
}