package com.example.erealtorapp.WalletManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.erealtorapp.R;
import com.example.erealtorapp.databinding.ActivityCreateNewWalletBinding;

public class CreateNewWalletActivity extends AppCompatActivity {

    ActivityCreateNewWalletBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityCreateNewWalletBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        getSupportActionBar().hide();
        bind.CreateWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean bool = new Wallet().WalletExists(CreateNewWalletActivity.this);
                if (bool) {
                    Log.d("Tag", "True");
                } else {
                    Log.d("Tag", "False");
                }
                if (bind.EnterWalletPasswordText.getText().toString().length() >= 6) {
                    new Wallet().CreateNewWallet(CreateNewWalletActivity.this,
                            bind.EnterWalletPasswordText.getText().toString());
                    Toast.makeText(CreateNewWalletActivity.this,
                            "Wallet Created", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(CreateNewWalletActivity.this,
                            "Password cannot have less than 6 characters", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}