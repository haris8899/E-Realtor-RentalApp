package com.example.erealtorapp.AdminPackage;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.example.erealtorapp.AgentPackage.AgentClass;
import com.example.erealtorapp.databinding.ActivityViewSingleAgentRequestBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ViewSingleAgentRequest extends AppCompatActivity {

    ActivityViewSingleAgentRequestBinding bind;
    FirebaseDatabase database;
    DatabaseReference myref;
    StorageReference storageRef;
    FirebaseStorage storage;
    Intent intent;
    String AgentID;
    String Name;
    String image;
    String email;
    String phone;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityViewSingleAgentRequestBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        intent = getIntent();
        AgentID = intent.getStringExtra("A1");
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        myref = database.getReference("Agent_Request").child(AgentID);
        ValueEventListener valueEventListener = myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Name =dataSnapshot.child("username").getValue().toString();
                    image =dataSnapshot.child("profileURI").getValue().toString();
                    email = dataSnapshot.child("email").getValue().toString();
                    phone = dataSnapshot.child("phone").getValue().toString();
                    address = dataSnapshot.child("phone").getValue().toString();
                    bind.agentnameesttext.setText(Name);
                    bind.Agentemailtext.setText(email);
                    bind.Agentaddresstext.setText(address);
                    bind.Agentphonetext.setText(phone);
                    bind.agentreqProfileimageview.setImageBitmap(stringtobitmap(image));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bind.DownloadCVbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a reference with an initial file path and name
                String name = bind.agentnameesttext.getText().toString();
               storageRef.child("Agent").child(AgentID).child(name+"CV").getDownloadUrl()
                       .addOnSuccessListener(new OnSuccessListener<Uri>() {
                           @Override
                           public void onSuccess(Uri uri) {
                               String url = uri.toString();
                               DownloadFiles(ViewSingleAgentRequest.this,bind.agentnameesttext.getText().toString() +"CV.pdf",url,DIRECTORY_DOWNLOADS);
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Log.d("Tag","Download Failed");
                           }
                       });
            }
        });
        bind.AgentRejectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myref.removeValue();
                storageRef.child("Agent").child(AgentID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Tag","File Successfully Deleted");
                    }
                });
                String to = bind.Agentemailtext.getText().toString();
                String subject = "Application to become Agent";
                String message = "Hi"+ bind.agentnameesttext.getText().toString()+",\n" +
                        " \n" +
                        "\n" +
                        "Thank you so much for your interest in the Agent position with E-realtor." +
                        " We appreciate you applying for this position.\n" +
                        " \n" +
                        "\n" +
                        "At this time, we are unable to accept your application" +
                        "\n" +
                        " \n" +
                        "\n" +
                        "We wish you the best of luck in your career endeavors.\n" +
                        " \n" +
                        "\n" +
                        "Thank you!";
                SendEmail(to,subject,message);
            }
        });
        bind.AgentSendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
        bind.AgentAcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference  ref= database.getReference().child("Agent");
                DatabaseReference ref2 = database.getReference().child("Users");
                ref2.child(AgentID).child("type").setValue("agent");
                String image1;
                ValueEventListener valueEventListener = myref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Name = snapshot.child("username").getValue().toString();
                        email = snapshot.child("email").getValue().toString();
                        image = snapshot.child("profileURI").getValue().toString();
                        phone = snapshot.child("phone").getValue().toString();
                        snapshot.child("Address").getValue().toString();

                        AgentClass agent = new AgentClass(Name,phone, image, email,address);
                        ref.child(AgentID).setValue(agent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                String to = bind.Agentemailtext.getText().toString();
                String subject = "Application to become Agent";
                String message ="Hi "+ bind.agentnameesttext.getText().toString()+",\n" +
                        "Congratulations your Application to become Agent for E-realtor has been Accepted\n"+
                        "Please Report to our office for initiation\n\n\n"+"Thank you!";
                myref.removeValue();
                SendEmail(to,subject,message);
            }
        });

    }

    private void SendEmail(String to, String subject, String message)
    {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);

        //need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));
        finish();
    }

    private void DownloadFiles(Context context, String Filename, String Url, String dest) {
        DownloadManager downloadManager = (DownloadManager) context
                .getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(Url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, dest, Filename);
        downloadManager.enqueue(request);
    }

    class RetrivePdfStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {

                // adding url
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                // if url connection response code is 200 means ok the execute
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }
            // if error return null
            catch (IOException e) {
                return null;
            }
            return inputStream;
        }

        @Override
        // Here load the pdf and dismiss the dialog box
        protected void onPostExecute(InputStream inputStream) {

        }
    }
    public Bitmap stringtobitmap(String string) {
        byte[] byteArray1;
        byteArray1 = Base64.decode(string, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray1, 0,
                byteArray1.length);
        return bmp;
    }
}