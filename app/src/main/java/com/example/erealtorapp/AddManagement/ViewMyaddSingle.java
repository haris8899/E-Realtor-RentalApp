package com.example.erealtorapp.AddManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.erealtorapp.AgentPackage.AgentRating;
import com.example.erealtorapp.ContractManagement.SendContractRequestActivity;
import com.example.erealtorapp.databinding.ActivityViewMyaddSingleBinding;
import com.example.erealtorapp.databinding.ActivityViewSingleAdBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewMyaddSingle extends AppCompatActivity {

    ActivityViewMyaddSingleBinding bind;
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
        bind = ActivityViewMyaddSingleBinding.inflate(getLayoutInflater());
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
                String status = snapshot.child(adid).child("status").getValue().toString();
//                String Agent = "";
//                if(snapshot.child(adid).child("agentid").getValue().toString()!=null)
//                {
//                    Agent = snapshot.child(adid).child("agentid").getValue().toString();
//                }
//                if(Agent!= null)
//                {
//                    Log.d("Tag","Agentid: "+Agent);
//                    bind.AgentnameText.setText(Agent);
//                }
                bind.AddStatusText.setText(status);
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
                        String Agentname = "Not Verified";
                        String Agent ="";
                        if(snapshot.child(adid).child("agentid").getValue()!=null)
                        {
                            Agent = snapshot.child(adid).child("agentid").getValue().toString();
                            Agentname = snapshot2.child(Agent).child("username").getValue().toString();
                            if(snapshot.child(adid).child("verified").getValue()!=null)
                            {
                                bind.AgentnameText.setTextColor(Color.WHITE);
                            } else
                            {
                                bind.AgentnameText.setTextColor(Color.CYAN);
                                String finalAgent = Agent;
                                bind.AgentnameText.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(ViewMyaddSingle.this, AgentRating.class);
                                        intent.putExtra("A1", finalAgent);
                                        intent.putExtra("A2", adid);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                        Log.d("Tag","Agentid: "+Agent);
                        bind.AgentnameText.setText(Agentname);
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