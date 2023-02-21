package com.example.erealtorapp.AddManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.erealtorapp.databinding.ActivityUpdateAdBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateAdActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    ActivityUpdateAdBinding binding;
    PropertyClass dataClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateAdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        String adid= intent.getStringExtra("A1");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Ads");
        ValueEventListener valueEventListener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.PropertyTitleText.setText(snapshot.child(adid).child("title").getValue().toString());
                binding.PropertyAddresText.setText(snapshot.child(adid).child("address").getValue().toString());
                binding.NoOfBedroomsText.setText(snapshot.child(adid).child("noofRooms").getValue().toString());
                binding.SizeOfPlotText.setText(snapshot.child(adid).child("plotsize").getValue().toString());
                binding.RentAmountText.setText(snapshot.child(adid).child("rent").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.UpdateAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child(adid).child("title").setValue(binding.PropertyTitleText.getText().toString());
                myRef.child(adid).child("address").setValue(binding.PropertyAddresText.getText().toString());
                myRef.child(adid).child("noofRooms").setValue(binding.NoOfBedroomsText.getText().toString());
                myRef.child(adid).child("plotsize").setValue(binding.SizeOfPlotText.getText().toString());
                myRef.child(adid).child("rent").setValue(binding.RentAmountText.getText().toString());
                finish();
            }
        });

    }
}