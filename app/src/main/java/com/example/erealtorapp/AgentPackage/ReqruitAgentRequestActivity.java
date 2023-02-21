package com.example.erealtorapp.AgentPackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.erealtorapp.PDFclass;
import com.example.erealtorapp.ProfileFragment;
import com.example.erealtorapp.databinding.ActivityRecruitAgentBinding;
import com.example.erealtorapp.databinding.ActivityReqruitAgentRequestBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.erealtorapp.R;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ReqruitAgentRequestActivity extends AppCompatActivity {

    ActivityReqruitAgentRequestBinding bind;
    private GoogleMap mMap;
    ProgressDialog dialog;
    FirebaseAuth auth;
    DatabaseReference ref;
    StorageReference storageReference;
    Intent intent;
    String username;
    String email;
    String phone;
    String uri;
    //String address;
    Uri PDFuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityReqruitAgentRequestBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        storageReference = FirebaseStorage.getInstance().getReference();
        ref = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        intent = getIntent();
        username = intent.getStringExtra("A1");
        email = intent.getStringExtra("A2");
        phone = intent.getStringExtra("A3");
        uri = intent.getStringExtra("A4");
        bind.emailtext.setText(email);
        bind.usernametext.setText(username);
        //address = bind.addresstext.getText().toString();
        bind.uploadpdfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bind.Fileuploadstatus.getText().toString().equals("No File Uploaded"))
                {
                    selectfiles();
                }
                else if(bind.Fileuploadstatus.getText().toString().equals("File Uploaded"))
                {
                    Toast.makeText(ReqruitAgentRequestActivity.this,
                            "File already Uploaded",Toast.LENGTH_SHORT).show();
                }
            }
        });
        bind.SendRequestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bind.Fileuploadstatus.getText().toString().equals("File Uploaded"))
                {
                    ref.child("Agent_Request").child(auth.getUid()).child("profileURI").setValue(uri);
                    ref.child("Agent_Request").child(auth.getUid()).child("username").setValue(username);
                    ref.child("Agent_Request").child(auth.getUid()).child("email").setValue(email);
                    ref.child("Agent_Request").child(auth.getUid()).child("phone").setValue(phone);
                    ref.child("Agent_Request").child(auth.getUid()).child("Address")
                            .setValue(bind.addresstext.getText().toString());
                    Toast.makeText(ReqruitAgentRequestActivity.this,
                            "Request Sent Successfully",Toast.LENGTH_SHORT);
                    finish();
                }
                else if(bind.Fileuploadstatus.getText().toString().equals("No File Uploaded"))
                {
                    Toast.makeText(ReqruitAgentRequestActivity.this,
                            "CV is Required",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectfiles() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pdf files to upload"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            UploadFiles(data.getData());

        }
    }

    private void UploadFiles(Uri data) {
        dialog =new ProgressDialog(this);
        dialog.setTitle("Uploading");
        dialog.show();
        StorageReference mystorageref = storageReference.child("Agent").child(auth.getUid()).child(username+"CV");
        mystorageref.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                dialog.dismiss();
                bind.Fileuploadstatus.setText("File Uploaded");
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                dialog.setMessage("File is Uploading please wait");
            }
        });
    }
}