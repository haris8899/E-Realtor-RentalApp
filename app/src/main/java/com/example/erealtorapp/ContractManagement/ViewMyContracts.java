package com.example.erealtorapp.ContractManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.erealtorapp.AddManagement.MyAddAdapter;
import com.example.erealtorapp.AddManagement.PropertyClass;
import com.example.erealtorapp.R;
import com.example.erealtorapp.databinding.ActivityViewMyContractsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewMyContracts extends AppCompatActivity {

    ActivityViewMyContractsBinding bind;
    MyContractAdapter adapter;
    ProgressDialog dilog;
    FirebaseAuth auth;
    static ArrayList<ContractClass> datalist = new ArrayList<ContractClass>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityViewMyContractsBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        getSupportActionBar().hide();
        adapter = new MyContractAdapter(this,datalist);
        auth = FirebaseAuth.getInstance();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        bind.Contractrecyclerview.setLayoutManager(layoutManager);
        bind.Contractrecyclerview.setItemAnimator(new DefaultItemAnimator());
        bind.Contractrecyclerview.setAdapter(adapter);
        dilog = new ProgressDialog(this);
        dilog.setTitle("Loading");
        dilog.setMessage("Data is loading");
        downloadContracts();

    }

    private void downloadContracts() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("Contracts");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        dilog.show();
        ValueEventListener value = myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for(DataSnapshot csnapshot: snapshot.getChildren())
                {
                    String landlordname = csnapshot.child("landlordID").getValue().toString();
                    SharedPreferences sharedPreferences = getSharedPreferences("userfiles",0);
                    String user = sharedPreferences.getString("A1","");
                    String CID = csnapshot.getKey();
                    //Log.d("Tag",CID);
                    String tenantname = csnapshot.child("tenantID").getValue().toString();
                    String propertyid = csnapshot.child("propertyID").getValue().toString();
                    String rentamount = csnapshot.child("rentAmount").getValue().toString();
                    String status = csnapshot.child("status").getValue().toString();
                    String duration = csnapshot.child("duration").getValue().toString();
                    if(!datalist.contains(
                            new ContractClass(CID, landlordname,tenantname,propertyid
                                    ,status,rentamount,duration)))
                    {
                        if(landlordname.equals(user) ||tenantname.equals(user))
                        {
                            datalist.add(new ContractClass(CID, landlordname,tenantname,propertyid
                                    ,status,rentamount,duration));
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
                dilog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}