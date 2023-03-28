package com.example.erealtorapp.ContractManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.erealtorapp.databinding.ActivityViewContractBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewContractSingle extends AppCompatActivity {

    ActivityViewContractBinding bind;
    FirebaseDatabase database;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityViewContractBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        Intent intent = getIntent();
        String CID = intent.getStringExtra("A1");
        Log.d("Tag",CID);
        database = FirebaseDatabase.getInstance();
        myref = database.getReference();
        bind.l2.setVisibility(View.INVISIBLE);
    }
}