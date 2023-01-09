package com.example.erealtorapp.AgentPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;

import com.example.erealtorapp.AdminPackage.RecruitAgent;
import com.example.erealtorapp.R;
import com.example.erealtorapp.databinding.ActivityAgentContactOwnerBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AgentContactOwner extends AppCompatActivity {
    ActivityAgentContactOwnerBinding bind;
    private static final int SMS_PERMISSION_CODE = 100;
    FirebaseDatabase database;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityAgentContactOwnerBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        database = FirebaseDatabase.getInstance();
        myref = database.getReference("Ads");
        Intent intent = getIntent();
        String adid = intent.getStringExtra("A1");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id= snapshot.child(adid).child("ownerID").getValue().toString();
                DatabaseReference ref = database.getReference("Users");
                ValueEventListener value = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String phoneno = snapshot.child(id).child("phone").getValue().toString();
                        bind.Ownerphoneno.setText(phoneno);
                        bind.sendmsg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (ContextCompat.checkSelfPermission(AgentContactOwner.this, Manifest.permission.SEND_SMS)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(AgentContactOwner.this,
                                            new String[] { Manifest.permission.SEND_SMS },SMS_PERMISSION_CODE);
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phoneno
                                            , null,bind.Msgbodytxt.getText().toString(),
                                            null, null);
                                } else {
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phoneno
                                            , null,bind.Msgbodytxt.getText().toString(),
                                            null, null);
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

    }
}