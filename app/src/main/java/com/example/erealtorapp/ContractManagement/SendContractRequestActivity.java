package com.example.erealtorapp.ContractManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.erealtorapp.R;
import com.example.erealtorapp.databinding.ActivitySendContractRequestBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SendContractRequestActivity extends AppCompatActivity {

    ActivitySendContractRequestBinding bind;
    FirebaseDatabase database;
    DatabaseReference myref;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivitySendContractRequestBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        Intent intent = getIntent();
        database = FirebaseDatabase.getInstance();
        myref = database.getReference();
        auth = FirebaseAuth.getInstance();
        //String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        String TenantID = auth.getUid();
        String Owner = intent.getStringExtra("A1");
        String PropertyID = intent.getStringExtra("A2");
        String ContractID = Owner + TenantID + PropertyID;
        bind.SendRequestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("Contracts").child(ContractID).exists())
                        {
                            Toast.makeText(getApplicationContext(),
                                    "You Already have a Request Pending on this Property",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                        {
                            MessagesClass messages = new MessagesClass(TenantID,
                                    bind.ContractInitiateMessage.getText().toString());
                            ContractClass Contract = new ContractClass(Owner,TenantID,PropertyID,"Request",
                                    "Not Set");
                            myref.child("Contracts").child(ContractID).setValue(Contract);
                            myref.child("Contracts").child(ContractID).child("messages").child("0").setValue(messages);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}