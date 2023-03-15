package com.example.erealtorapp.AddManagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.erealtorapp.databinding.ActivityUpdateAdBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateAdActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    ActivityUpdateAdBinding bind;
    PropertyClass dataClass;
    ArrayList<String> images;
    ArrayList<String> imagesBack;
    ArrayList<Uri> AddImageList;
    Uri currentImage;
    ArrayList<Uri> ImageUriList;

    int CurrentImageIndex = 0;
    int totalnumberofimages = 0;
    GenericTypeIndicator<ArrayList<String>> t;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityUpdateAdBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        getSupportActionBar().hide();
        Intent intent = getIntent();
        images = new ArrayList<String>();
        imagesBack = new ArrayList<String>();
        storageReference = FirebaseStorage.getInstance().getReference();
        ImageUriList = new ArrayList<Uri>();
        AddImageList = new ArrayList<>();
        String adid = intent.getStringExtra("A1");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Properties");
        ValueEventListener valueEventListener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bind.PropertyTitleText.setText(snapshot.child(adid).child("title").getValue().toString());
                bind.PropertyAddresText.setText(snapshot.child(adid).child("address").getValue().toString());
                bind.NoOfBedroomsText.setText(snapshot.child(adid).child("noofRooms").getValue().toString());
                bind.SizeOfPlotText.setText(snapshot.child(adid).child("plotsize").getValue().toString());
                bind.RentAmountText.setText(snapshot.child(adid).child("rent").getValue().toString());
                DatabaseReference ref1 = snapshot.child(adid).child("images").getRef();
                ValueEventListener valuei = ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postsnap : snapshot.getChildren()) {
                            String image = postsnap.getValue().toString();
                            images.add(image);
                            imagesBack.add(image);
                        }
                        ImageUriList.clear();
                        File newfile = null;
                        for (int i = 0; i < images.size(); i++) {
                            try {
                                newfile = File.createTempFile("images", ".jpg");
                                StorageReference mystorageref1 = storageReference.child("Properties")
                                        .child(adid).child("images")
                                        .child(String.valueOf(i));
                                mystorageref1.getFile(newfile);
                                ImageUriList.add(Uri.fromFile(newfile));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        currentImage = Uri.parse(images.get(0));
                        Picasso.get().load(currentImage).into(bind.PropertyImageView);
                        totalnumberofimages = images.size();
                        bind.floatingActionButton2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (CurrentImageIndex < totalnumberofimages - 1) {
                                    CurrentImageIndex = CurrentImageIndex + 1;
                                }
                                currentImage = Uri.parse(images.get(CurrentImageIndex));
                                Picasso.get().load(currentImage).into(bind.PropertyImageView);
                            }
                        });
                        bind.floatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (CurrentImageIndex > 0) {
                                    CurrentImageIndex = CurrentImageIndex - 1;
                                }
                                currentImage = Uri.parse(images.get(CurrentImageIndex));
                                Picasso.get().load(currentImage).into(bind.PropertyImageView);
                            }
                        });
                        bind.deletephotosbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (images.size() > 0) {
                                    images.remove(CurrentImageIndex);
                                    ImageUriList.remove(CurrentImageIndex);
                                    totalnumberofimages = totalnumberofimages - 1;
                                    if (CurrentImageIndex > 0) {
                                        CurrentImageIndex = CurrentImageIndex - 1;
                                        currentImage = Uri.parse(images.get(CurrentImageIndex));
                                        Picasso.get().load(currentImage).into(bind.PropertyImageView);
                                    } else if (CurrentImageIndex == 0) {
                                        CurrentImageIndex = totalnumberofimages - 1;
                                        currentImage = Uri.parse(images.get(CurrentImageIndex));
                                        Picasso.get().load(currentImage).into(bind.PropertyImageView);
                                    }
                                } else Toast.makeText(UpdateAdActivity.this,
                                        "No Images Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        });
                        bind.addphotosbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ImagePicker.with(UpdateAdActivity.this)
                                        .crop()	    			//Crop image(Optional), Check Customization for more option
                                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                                        .start();
                            }
                        });

                        bind.UpdateAddButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ImageUriList.addAll(AddImageList);
                                StorageReference stref = storageReference.child("Properties")
                                        .child(adid).child("images");
                                for (int i = 0; i < imagesBack.size(); i++) {
                                    stref.child(String.valueOf(i)).delete();
                                }
                                myRef.child(adid).child("images").removeValue();
                                for (int i = 0; i < ImageUriList.size(); i++) {
                                    try {
                                        uploadImages(ImageUriList.get(i), adid, i);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                myRef.child(adid).child("title").setValue(bind.PropertyTitleText.getText().toString());
                                myRef.child(adid).child("address").setValue(bind.PropertyAddresText.getText().toString());
                                myRef.child(adid).child("noofRooms").setValue(bind.NoOfBedroomsText.getText().toString());
                                myRef.child(adid).child("plotsize").setValue(bind.SizeOfPlotText.getText().toString());
                                myRef.child(adid).child("rent").setValue(bind.RentAmountText.getText().toString());
                                finish();
                            }
                        });
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        if(uri != null)
        {
            bind.PropertyImageView.setImageURI(uri);
            images.add(uri.toString());
            AddImageList.add(uri);
            totalnumberofimages = totalnumberofimages + 1;
            CurrentImageIndex = totalnumberofimages -1;
        }
    }

    public void uploadImages(Uri data, String PropertyID, int i) throws IOException {
        StorageReference mystorageref = storageReference.child("Properties")
                .child(PropertyID).child("images").child(String.valueOf(i));
        mystorageref.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getMetadata().getReference().getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                myRef.child(PropertyID).child("images").
                                        child(String.valueOf(i)).setValue(uri.toString());
                            }
                        });
            }
        });
    }
}