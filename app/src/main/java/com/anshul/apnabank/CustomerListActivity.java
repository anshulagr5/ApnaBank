package com.anshul.apnabank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.anshul.apnabank.Adapter.OnCustomerClickListener;
import com.anshul.apnabank.Adapter.RecyclerViewAdapter;
import com.anshul.apnabank.Model.Customer;

import androidx.appcompat.widget.SearchView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerListActivity extends AppCompatActivity implements OnCustomerClickListener {
    private RecyclerView recyclerView;
    List<Customer> customerDetailsList;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent getIntent = getIntent();
        customerDetailsList = (List<Customer>) getIntent.getSerializableExtra("list");

        recyclerViewAdapter = new RecyclerViewAdapter(customerDetailsList, this);
        recyclerView.setAdapter(recyclerViewAdapter);

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
        List<Customer> filteredList = new ArrayList<>();

        for (Customer customer : customerDetailsList) {
            if (customer.getAccountId().toLowerCase().contains(query.toLowerCase()) ||
                customer.getName().toLowerCase().contains(query.toLowerCase()) ||
                customer.getAddress().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(customer);
            }
        }

        recyclerViewAdapter.filterList(filteredList);
    }

    @Override
    public void onCustomerClick(Customer customer) {
        Intent intent = new Intent(CustomerListActivity.this, CustomerDetailsActivity.class);
        intent.putExtra("list", (Serializable) customerDetailsList);
        intent.putExtra("selectedCustomer", (Serializable) customer);
        startActivity(intent);
    }
}