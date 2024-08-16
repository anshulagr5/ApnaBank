package com.anshul.apnabank.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.anshul.apnabank.Model.Transaction;
import com.anshul.apnabank.R;
import com.anshul.apnabank.Model.Customer;
import com.anshul.apnabank.Util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"
                + Util.KEY_ACCOUNT_ID + " TEXT,"
                + Util.KEY_NAME + " TEXT,"
                + Util.KEY_MOBILE + " TEXT,"
                + Util.KEY_EMAIL + " TEXT,"
                + Util.KEY_ADDRESS + " TEXT,"
                + Util.KEY_BALANCE + " TEXT" + ")";

        String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + Util.TABLE_NAME_TRANSACTION + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"
                + Util.KEY_SENDER_ACCOUNT_ID + " TEXT,"
                + Util.KEY_RECEIVER_ACCOUNT_ID + " TEXT,"
                + Util.KEY_AMOUNT + " INTEGER,"
                + Util.KEY_DATE + " INTEGER" + ")";

        db.execSQL(CREATE_CONTACT_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.dpDrop);
        String DROP_TRANSACTION_TABLE = "DROP TABLE IF EXISTS " + Util.TABLE_NAME_TRANSACTION;

        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});
        db.execSQL(DROP_TRANSACTION_TABLE);

        onCreate(db);
    }

    public void addCustomer(Customer customerDetails){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_ACCOUNT_ID, customerDetails.getAccountId());
        values.put(Util.KEY_NAME, customerDetails.getName());
        values.put(Util.KEY_MOBILE, customerDetails.getMobileNo());
        values.put(Util.KEY_EMAIL, customerDetails.getEmail());
        values.put(Util.KEY_ADDRESS, customerDetails.getAddress());
        values.put(Util.KEY_BALANCE, customerDetails.getBalance());

        Log.d("database", "addCustomer: "+ values.getAsString(Util.KEY_ACCOUNT_ID));

        //insert to row
        db.insert(Util.TABLE_NAME, null, values);
        db.close(); // closing db connection
    }

    //get all Customers
    public List<Customer> getAllCustomers(){
        List<Customer> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if(cursor.moveToFirst()){
            do{
                Customer customer  = new Customer();
                customer.setId(Integer.parseInt(cursor.getString(0)));
                customer.setAccountId(cursor.getString(1));
                customer.setName(cursor.getString(2));
                customer.setMobileNo(cursor.getString(3));
                customer.setEmail(cursor.getString(4));
                customer.setAddress(cursor.getString(5));
                customer.setBalance(cursor.getString(6));

                contactList.add(customer);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return contactList;
    }

    //Update Contact
    public void updateCustomer(Customer customerDetails){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_ACCOUNT_ID,customerDetails.getAccountId());
        values.put(Util.KEY_NAME, customerDetails.getName());
        values.put(Util.KEY_MOBILE, customerDetails.getMobileNo());
        values.put(Util.KEY_EMAIL, customerDetails.getEmail());
        values.put(Util.KEY_ADDRESS, customerDetails.getAddress());
        values.put(Util.KEY_BALANCE, customerDetails.getBalance());

        //update row
        //update(tablename, values, where id = 3)
        db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?",
                new String[]{String.valueOf(customerDetails.getId())});
        db.close();
    }

    // Transaction methods
    public void addTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_SENDER_ACCOUNT_ID, transaction.getSenderAccountId());
        values.put(Util.KEY_RECEIVER_ACCOUNT_ID, transaction.getReceiverAccountId());
        values.put(Util.KEY_AMOUNT, transaction.getAmount());
        values.put(Util.KEY_DATE, transaction.getDate().getTime()); // Store date as long

        db.insert(Util.TABLE_NAME_TRANSACTION, null, values);
        db.close();
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + Util.TABLE_NAME_TRANSACTION;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction();
                transaction.setId(Integer.parseInt(cursor.getString(0)));
                transaction.setSenderAccountId(cursor.getString(1));
                transaction.setReceiverAccountId(cursor.getString(2));
                transaction.setAmount(cursor.getInt(3));
                transaction.setDate(new Date(cursor.getLong(4)));

                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return transactionList;
    }
    public void deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Util.TABLE_NAME);
    }
}
