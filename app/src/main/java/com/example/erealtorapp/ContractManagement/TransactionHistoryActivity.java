package com.example.erealtorapp.ContractManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.erealtorapp.ErealtorSmartContract.RentalSmartContract;
import com.example.erealtorapp.R;
import com.example.erealtorapp.databinding.ActivityTransactionHistoryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.web3j.protocol.Web3j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class TransactionHistoryActivity extends AppCompatActivity {

    ActivityTransactionHistoryBinding bind;
    ArrayList<Transactions> transactionslist;
    TransactionAdaptor adaptor;
    FirebaseDatabase database;
    DatabaseReference ref;
    ProgressDialog dilog;
    String CID;
    String message = "";
    String Hash = "";
    String block = "";
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityTransactionHistoryBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        getSupportActionBar().hide();
        transactionslist = new ArrayList<Transactions>();
        intent = getIntent();
        database = FirebaseDatabase.getInstance();
        CID = intent.getStringExtra("A1");
        Toast.makeText(TransactionHistoryActivity.this,
                "Tap on values to copy to clipboard",Toast.LENGTH_SHORT).show();
        ref = database.getReference("Contracts").child(CID).getRef();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ContractAddress = snapshot.child("ContractAddress").getValue().toString();
                bind.ContractAddressText.setText(ContractAddress);
                bind.ContractAddressText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClipboard(TransactionHistoryActivity.this,ContractAddress);

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adaptor = new TransactionAdaptor(TransactionHistoryActivity.this,transactionslist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        bind.TransactionHistoryRecyclerView.setLayoutManager(layoutManager);
        bind.TransactionHistoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        bind.TransactionHistoryRecyclerView.setAdapter(adaptor);
        dilog = new ProgressDialog(this);
        dilog.setTitle("Loading");
        dilog.setMessage("Data is loading");
        dilog.show();
        downloadTransactions();

    }

    private void downloadTransactions() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("Contracts")
                .child(CID).child("Transactions").getRef();
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transactionslist.clear();
                for(DataSnapshot csnapshot: snapshot.getChildren())
                {
                    if(csnapshot.child("Message").getValue()!=null && csnapshot.child("Hash").getValue()!=null)
                    {
                        message = csnapshot.child("Message").getValue().toString();
                        Hash = csnapshot.child("Hash").getValue().toString();
                        block = csnapshot.child("block").getValue().toString();
//                        try {
//                           Date date = new RentalSmartContract().Timestamp(block);
//                            Log.d("Tag",String.valueOf(date));
//                        } catch (ExecutionException e) {
//                            throw new RuntimeException(e);
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
                    }
                    if(!transactionslist.contains(new Transactions(message,Hash,block)))
                    {
                        transactionslist.add(new Transactions(message,Hash,block));
                        adaptor.notifyDataSetChanged();
                    }
                }
                dilog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard =
                    (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard =
                    (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(TransactionHistoryActivity.this,
                "Text copied to clipboard",Toast.LENGTH_SHORT).show();
    }
}