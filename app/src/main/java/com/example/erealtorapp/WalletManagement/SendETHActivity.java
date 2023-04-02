package com.example.erealtorapp.WalletManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.erealtorapp.databinding.ActivitySendEthactivityBinding;

public class SendETHActivity extends AppCompatActivity {

    ActivitySendEthactivityBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivitySendEthactivityBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        getSupportActionBar().hide();
        Intent intent = getIntent();
        String Password = intent.getStringExtra("A1");
        bind.SendEthbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Wallet w = new Wallet();
                try {
                    w.sendEth(w.LoadWalletCrediantials(SendETHActivity.this,Password),
                            bind.SendEtherAddressTXT.getText().toString(),
                            Double.valueOf(bind.EnterEthtosendTXT.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }
}