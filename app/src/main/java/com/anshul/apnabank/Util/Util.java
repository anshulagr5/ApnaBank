package com.anshul.apnabank.Util;

public class Util {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "customer_db";
    public static final String TABLE_NAME = "customers";

    public static final String KEY_ID = "id";

    public  static final String KEY_ACCOUNT_ID="account";
    public static final String KEY_NAME = "name";

    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_EMAIL = "email";

    public static final String KEY_ADDRESS = "address";
    public static final String KEY_BALANCE = "balance";

    // Transaction Table
    public static final String TABLE_NAME_TRANSACTION = "transactions";
    public static final String KEY_TRANSACTION_ID = "transaction_id";
    public static final String KEY_SENDER_ACCOUNT_ID = "sender_account_id";
    public static final String KEY_RECEIVER_ACCOUNT_ID = "receiver_account_id";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_DATE = "date";
}
