package com.anshul.apnabank.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.anshul.apnabank.R;
import com.anshul.apnabank.Model.Transaction;

import java.text.SimpleDateFormat;
import java.util.List;
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<Transaction> transactionList;

    public TransactionAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);

        holder.senderText.setText(transaction.getSenderAccountId());
        holder.receiverText.setText(transaction.getReceiverAccountId());
        holder.amountText.setText(String.valueOf(transaction.getAmount()));

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        holder.dateText.setText(formatter.format(transaction.getDate()));
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView senderText;
        public TextView receiverText;
        public TextView amountText;
        public TextView dateText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            senderText = itemView.findViewById(R.id.senderTextView);
            receiverText = itemView.findViewById(R.id.receiverTextView);
            amountText = itemView.findViewById(R.id.amountTextView);
            dateText = itemView.findViewById(R.id.dateTextView);
        }
    }

    public void filterList(List<Transaction> filteredList) {
        transactionList = filteredList;
        notifyDataSetChanged();
    }
}
