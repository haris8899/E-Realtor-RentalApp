package com.example.erealtorapp.AddManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.erealtorapp.ContractManagement.SendContractRequestActivity;
import com.example.erealtorapp.databinding.ActivityViewSingleAdBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewSingleAd extends AppCompatActivity {

    ActivityViewSingleAdBinding bind;
    Intent intent;
    String adid;
    FirebaseDatabase data;
    DatabaseReference myref;
    FirebaseAuth auth;
    ArrayList<String> images;
    Uri currentImage;
    int CurrentImageIndex = 0;
    int totalnumberofimages = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityViewSingleAdBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        intent = getIntent();
        getSupportActionBar().hide();
        adid = intent.getStringExtra("A1");
        data =FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        myref = data.getReference("Properties");
        images = new ArrayList<String>();
        ValueEventListener valueEventListener= myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bind.PropertyTitleText.setText(snapshot.child(adid)
                        .child("title").getValue().toString());
                bind.PropertyAddresText.setText(snapshot.child(adid).child("address").getValue().toString());
                bind.NoOfBedroomsText.setText(snapshot.child(adid).child("noofRooms").getValue().toString());
                bind.SizeOfPlotText.setText(snapshot.child(adid).child("plotsize").getValue().toString());
                bind.RentAmountText.setText(snapshot.child(adid).child("rent").getValue().toString());
                DatabaseReference ref1 = snapshot.child(adid).child("images").getRef();
                String owner = snapshot.child(adid).child("ownerID").getValue().toString();
                ValueEventListener valuei = ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postsnap: snapshot.getChildren())
                        {
                            String image =postsnap.getValue().toString();
                            images.add(image);
                        }
                        currentImage = Uri.parse(images.get(0));
                        Picasso.get().load(currentImage).into(bind.PropertyImageView);
                        totalnumberofimages = images.size();
                        bind.floatingActionButton2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(CurrentImageIndex < totalnumberofimages-1)
                                {
                                    CurrentImageIndex = CurrentImageIndex +1;
                                }
                                currentImage = Uri.parse(images.get(CurrentImageIndex));
                                Picasso.get().load(currentImage).into(bind.PropertyImageView);
                            }
                        });
                        bind.floatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(CurrentImageIndex > 0)
                                {
                                    CurrentImageIndex = CurrentImageIndex -1;
                                }
                                currentImage = Uri.parse(images.get(CurrentImageIndex));
                                Picasso.get().load(currentImage).into(bind.PropertyImageView);
                            }
                        });
                        if(owner.equals(auth.getUid()))
                        {
                            bind.SendContractRequestBtn.setVisibility(View.INVISIBLE);
                            bind.CallOwnerBtn.setVisibility(View.INVISIBLE);
                        }

                        bind.SendContractRequestBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ViewSingleAd.this
                                        , SendContractRequestActivity.class);
                                intent.putExtra("A1",owner);
                                intent.putExtra("A2",adid);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                DatabaseReference ref = data.getReference("Users");
                ValueEventListener value = ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                        String name= snapshot2.child(snapshot.child(adid).child("ownerID").getValue().toString())
                                .child("username").getValue().toString();
                        bind.OwnerNameText.setText(name);
                        String phone= snapshot2.child(snapshot.child(adid).child("ownerID").getValue().toString())
                                .child("phone").getValue().toString();
                        bind.OwnerPhoneText.setText(phone);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}