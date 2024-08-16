package com.anshul.apnabank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.anshul.apnabank.Data.DatabaseHandler;
import com.anshul.apnabank.Model.Customer;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_CUSTOMER = 1;
    AlertDialog.Builder builder;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    LinearLayout mMainLayout;
    List<Customer> customerDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        builder = new AlertDialog.Builder(this);

        mMainLayout= findViewById(R.id.mainActivity);

        BiometricManager biometricManager=BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "Device doesn't have fingerprint", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "Hardware Not Working", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "No fingerprint Assigned", Toast.LENGTH_SHORT).show();
                break;

        }

        Executor executor= ContextCompat.getMainExecutor(this);
        biometricPrompt=new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                mMainLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        promptInfo=new BiometricPrompt.PromptInfo.Builder().setTitle("Apna Bank")
                .setDescription("Use Fingerprint to authenticate")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK | BiometricManager.Authenticators.DEVICE_CREDENTIAL).build();
        biometricPrompt.authenticate(promptInfo);

        Button allCustomersButton = findViewById(R.id.allCustomersButton);
        Button addCustomersButton = findViewById(R.id.addCustomersButton);
        Button transactionButton = findViewById(R.id.transactionHistoryButton);

        DatabaseHandler handler = new DatabaseHandler(MainActivity.this);

        customerDetailsList = handler.getAllCustomers();
//        Log.d("TAG", "onCreate: " + customerDetailsList.size());

        allCustomersButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CustomerListActivity.class);
            intent.putExtra("list", (Serializable) customerDetailsList);
            startActivity(intent);
        });


        addCustomersButton.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this,AddCustomerActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_CUSTOMER);
        });

        transactionButton.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, TransactionDetailsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_CUSTOMER && resultCode == RESULT_OK) {
            DatabaseHandler handler = new DatabaseHandler(MainActivity.this);
            customerDetailsList = handler.getAllCustomers();
            Log.d("TAG", "onActivityResult: " + customerDetailsList.size());
        }
    }
}