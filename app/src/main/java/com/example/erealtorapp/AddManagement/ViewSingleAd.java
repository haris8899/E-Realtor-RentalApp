package com.example.erealtorapp.AddManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.example.erealtorapp.R;
import com.example.erealtorapp.databinding.ActivityViewSingleAdBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewSingleAd extends AppCompatActivity {

    ActivityViewSingleAdBinding bind;
    Intent intent;
    String adid;
    FirebaseDatabase data;
    DatabaseReference myref;
    ArrayList<String> images;
    int CurrentImageIndex = 0;
    int totalnumberofimages = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityViewSingleAdBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        intent = getIntent();
        adid = intent.getStringExtra("A1");
        data =FirebaseDatabase.getInstance();
        myref = data.getReference("Ads");
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
                ValueEventListener valuei = ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postsnap: snapshot.getChildren())
                        {
                            String image =postsnap.getValue().toString();
                            Log.d("Tag",image);
                            images.add(image);
                        }
                        bind.PropertyImageView.setImageBitmap(stringtobitmap(images.get(0)));
                        totalnumberofimages = images.size();
                        bind.floatingActionButton2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(CurrentImageIndex < totalnumberofimages-1)
                                {
                                    Log.d("Tag","forward");
                                    CurrentImageIndex = CurrentImageIndex +1;
                                }
                                bind.PropertyImageView.setImageBitmap(stringtobitmap(images.get(CurrentImageIndex)));
                            }
                        });
                        bind.floatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(CurrentImageIndex > 0)
                                {
                                    Log.d("Tag","back");
                                    CurrentImageIndex = CurrentImageIndex -1;
                                }
                                bind.PropertyImageView.setImageBitmap(stringtobitmap(images.get(CurrentImageIndex)));
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
    public Bitmap stringtobitmap(String string)
    {
        byte[] byteArray1;
        byteArray1 = Base64.decode(string, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray1, 0,
                byteArray1.length);
        return bmp;
    }
}