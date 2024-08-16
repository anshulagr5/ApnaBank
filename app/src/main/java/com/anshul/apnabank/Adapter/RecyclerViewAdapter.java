package com.anshul.apnabank.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anshul.apnabank.R;
import com.anshul.apnabank.Model.Customer;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Customer> customerList;
    private OnCustomerClickListener customerClickListener;
    public RecyclerViewAdapter(List<Customer> customerList, OnCustomerClickListener onCustomerClickListener) {
        this.customerList = customerList;
        this.customerClickListener = onCustomerClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_customers, parent, false);
        return new ViewHolder(view, customerClickListener);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Customer customer = customerList.get(position);

        holder.account.setText(customer.getAccountId());
        holder.name.setText(customer.getName());
        holder.address.setText(customer.getAddress());
    }
    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView account;
        public TextView address;
        OnCustomerClickListener onCustomerClickListener;
        public ViewHolder(@NonNull View itemView, OnCustomerClickListener onCustomerClickListener) {
            super(itemView);

            account = itemView.findViewById(R.id.idTextView);
            name = itemView.findViewById(R.id.nameTextView);
            address=itemView.findViewById(R.id.addressTextView);
            this.onCustomerClickListener = onCustomerClickListener;

            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            onCustomerClickListener.onCustomerClick(customerList.get(getAdapterPosition()));
        }
    }
    public void filterList(List<Customer> filteredList) {
        customerList = filteredList;
        notifyDataSetChanged();
    }
}
