package com.example.erealtorapp.ContractManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.erealtorapp.AddManagement.MyAddAdapter;
import com.example.erealtorapp.R;
import com.example.erealtorapp.databinding.ActivityViewContractBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewContractSingle extends AppCompatActivity {

    ActivityViewContractBinding bind;
    FirebaseAuth auth;
    ChatAdapter adapter;
    FirebaseDatabase database;
    DatabaseReference myref;
    ArrayList<MessagesClass> messageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityViewContractBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        Intent intent = getIntent();
        String CID = intent.getStringExtra("A1");
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myref = database.getReference();
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Data is Loading");
        dialog.setTitle("Loading");
        ArrayAdapter<CharSequence> spinadapter = ArrayAdapter.createFromResource(this,
                R.array.duration_array, android.R.layout.simple_spinner_item);
        spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bind.durationedit.setAdapter(spinadapter);
        messageList = new ArrayList<MessagesClass>();
        adapter = new ChatAdapter(messageList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        bind.messageRecyclerView.setLayoutManager(layoutManager);
        bind.messageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        bind.messageRecyclerView.setAdapter(adapter);
        dialog.show();
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Landlordid = snapshot.child("Contracts").child(CID)
                        .child("landlordID").getValue().toString();
                String Landlordname = snapshot.child("Users").child(Landlordid)
                        .child("username").getValue().toString();
                String Tenantid = snapshot.child("Contracts").child(CID)
                        .child("tenantID").getValue().toString();
                String Tenantname = snapshot.child("Users").child(Tenantid)
                        .child("username").getValue().toString();
                String duration = snapshot.child("Contracts").child(CID)
                        .child("duration").getValue().toString();
                String rent = snapshot.child("Contracts").child(CID)
                        .child("rentAmount").getValue().toString();
                String Status = snapshot.child("Contracts").child(CID)
                        .child("status").getValue().toString();
                String durationString = "";
                int durationint = 0;
                bind.LandlordNameText.setText(Landlordname);
                bind.TenantNameText.setText(Tenantname);
                bind.StatusText.setText(Status);
                bind.RentText.setText(rent);
                bind.Rentedit.setText(rent);
                switch (duration)
                {
                    case "0.5":
                        durationString = "6 months";
                        durationint = 0;
                        break;
                    case "1":
                        durationString = "1 year";
                        durationint = 1;
                        break;
                    case "2":
                        durationString = "2 Years";
                        durationint = 2;
                        break;
                }
                bind.durationText.setText(durationString);
                bind.durationedit.setSelection(durationint);
                bind.LandlordLayout.setVisibility(View.GONE);
                bind.l2.setVisibility(View.GONE);
                if(Landlordid.equals(auth.getCurrentUser().getUid()))
                {
                    bind.TenantLayout.setVisibility(View.GONE);
                    bind.LandlordLayout.setVisibility(View.VISIBLE);
                    bind.l2.setVisibility(View.VISIBLE);
                }
                else if (Tenantid.equals(auth.getCurrentUser().getUid()) && Status.equals("Accepted"))
                {
                    bind.l2.setVisibility(View.VISIBLE);
                }
                dialog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference myref2 = database.getReference("Contracts").child(CID).child("messages").getRef();
        myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                Log.d("Tag","listener entered");
                for(DataSnapshot postsnapshot: snapshot.getChildren())
                {
                    String Sender = postsnapshot.child("sender").getValue().toString();
                    String message = postsnapshot.child("message").getValue().toString();
                    messageList.add(new MessagesClass(Sender,message));
                    Log.d("Tag","Length "+messageList.size());
                    adapter.notifyDataSetChanged();
                }
                bind.SendMessageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String message = bind.EnterMessageText.getText().toString();
                        MessagesClass newmessage =
                                new MessagesClass(auth.getCurrentUser().getUid(),message);
                        int index = messageList.size();
                        myref2.child(Integer.toString(index)).setValue(newmessage);
                        bind.EnterMessageText.setText("");
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}