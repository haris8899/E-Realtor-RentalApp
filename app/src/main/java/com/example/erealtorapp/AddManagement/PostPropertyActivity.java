package com.example.erealtorapp.AddManagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.erealtorapp.databinding.ActivityPostPropertyBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PostPropertyActivity extends AppCompatActivity {

    ActivityPostPropertyBinding bind;
    List<Uri> ImageList;
    FirebaseDatabase database;
    FirebaseAuth auth;
    Bitmap bmp;
    String PropertyID;
    DatabaseReference myRef;
    StorageReference storageReference;
    int CurrentImageIndex = 0;
    int totalnumberofimages = 0;
    private ProgressDialog dialog;
    private String timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityPostPropertyBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        ImageList = new ArrayList<Uri>();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        myRef = database.getReference("Properties");
        storageReference = FirebaseStorage.getInstance().getReference();
        bind.Addphotosbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(PostPropertyActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        bind.UploadAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog =new ProgressDialog(PostPropertyActivity.this);
                dialog.setTitle("Uploading");
                dialog.show();
                timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
                PropertyID = auth.getUid().toString() + timestamp;
                for(int i=0;i<ImageList.size();i++)
                {
                    uploadImages(ImageList.get(i),PropertyID,i);
                }
                PropertyClass data = new PropertyClass(bind.PropertyTitleText.getEditText().getText().toString().trim(),bind.PropertyAddresText.getEditText().getText().toString().trim()
                        ,Integer.parseInt(bind.NoOfBedroomsText.getEditText().getText().toString().trim()),Integer.parseInt(bind.RentAmountText.getEditText().getText().toString().trim())
                        ,Integer.parseInt(bind.SizeOfPlotText.getEditText().getText().toString().trim()),auth.getUid().toString(),Boolean.FALSE);
                myRef.child(PropertyID).setValue(data);
                Toast.makeText(PostPropertyActivity.this,"Ad Created Successfully!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        bind.PropertyImageView.setImageURI(uri);
        ImageList.add(uri);
        totalnumberofimages = totalnumberofimages + 1;
        CurrentImageIndex = totalnumberofimages -1;
      //  images.add(uri);
//        try {
//            bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver() , uri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        bmpImages.add(bmp);
    }
    public void uploadImages(Uri data,String PropertyID, int i)
    {
        Log.d("Tag","Index: "+String.valueOf(i));
        StorageReference mystorageref = storageReference.child("Properties")
                .child(PropertyID).child("images").child(String.valueOf(i));
        mystorageref.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("Tag",uri.toString());
                        myRef.child(PropertyID).child("images").child(String.valueOf(i)).setValue(uri.toString());
                    }
                });
            }
        });
    }
    public void PreviousImageFunction(View view)
    {
        if(CurrentImageIndex > 0)
        {
            Log.d("Tag","back");
            CurrentImageIndex = CurrentImageIndex -1;
        }
        Log.d("Tag","Back "+String.valueOf(CurrentImageIndex));
        bind.PropertyImageView.setImageURI(ImageList.get(CurrentImageIndex));
    }
    public void NextImageFunction(View view)
    {
        if(CurrentImageIndex < totalnumberofimages-1)
        {
            Log.d("Tag","forward");
            CurrentImageIndex = CurrentImageIndex +1;
        }
        Log.d("Tag","Forward "+String.valueOf(CurrentImageIndex));
        Log.d("Tag","Total"+ String.valueOf(ImageList.size()));
        bind.PropertyImageView.setImageURI(ImageList.get(CurrentImageIndex));
    }
}