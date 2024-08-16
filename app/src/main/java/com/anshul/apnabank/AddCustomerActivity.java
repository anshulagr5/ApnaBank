package com.anshul.apnabank;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anshul.apnabank.Data.DatabaseHandler;
import com.anshul.apnabank.Model.Customer;

public class AddCustomerActivity extends AppCompatActivity {

    private EditText nameEditText, mobileEditText,emailEditText, addressEditText,balanceEditText;
    private Button addCustomerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        nameEditText = findViewById(R.id.nameEditText);
        mobileEditText=findViewById(R.id.mobileEditText);
        emailEditText = findViewById(R.id.emailEditText);
        addressEditText=findViewById(R.id.addressEditText);
        balanceEditText = findViewById(R.id.balanceEditText);
        addCustomerButton = findViewById(R.id.addCustomerButton);

        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String mobile= mobileEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();
                String balance = balanceEditText.getText().toString().trim();

                if (name.isEmpty() || mobile.isEmpty() || email.isEmpty()|| address.isEmpty() || balance.isEmpty()) {
                    Toast.makeText(AddCustomerActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                String id="15"+mobile;
                DatabaseHandler handler = new DatabaseHandler(AddCustomerActivity.this);
                handler.addCustomer(new Customer(id,name, mobile, email,address, balance));

                Toast.makeText(AddCustomerActivity.this, "Customer added successfully", Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
