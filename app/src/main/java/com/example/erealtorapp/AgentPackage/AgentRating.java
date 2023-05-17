package com.example.erealtorapp.AgentPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.erealtorapp.R;
import com.example.erealtorapp.databinding.ActivityAgentRatingBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AgentRating extends AppCompatActivity {

    ActivityAgentRatingBinding bind;
    FirebaseDatabase database;
    DatabaseReference ref;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityAgentRatingBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        intent = getIntent();
        getSupportActionBar().hide();
        String agid = intent.getStringExtra("A1");
        String adid = intent.getStringExtra("A2");
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        bind.AgentRatinBar.setNumStars(5);
        bind.SubmitRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("Properties").child(adid).child("verified").getValue() == null) {
                            ref.child("Properties").child(adid).child("verified").setValue("true");
                            if (snapshot.child("Users").child(agid).child("ratings").getValue() == null) {
                                ref.child("Users").child(agid).child("ratings").child("num").setValue("1");
                                String Rating = String.valueOf(bind.AgentRatinBar.getRating());
                                ref.child("Users").child(agid).child("ratings").child("rating").setValue(Rating);
                                finish();
                            } else {
                                float num = Float.parseFloat(snapshot.child("Users").child(agid).child("ratings").child("num").getValue().toString());
                                float newnum = num + 1;
                                ref.child("Users").child(agid).child("ratings").child("num").setValue(String.valueOf(newnum));
                                float rating = Float.parseFloat(snapshot.child("Users").child(agid).child("ratings").child("rating").getValue().toString());
                                float newrating = ((rating * num) + bind.AgentRatinBar.getRating()) / newnum;
                                ref.child("Users").child(agid).child("ratings").child("rating").setValue(newrating);
                                finish();
                            }
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