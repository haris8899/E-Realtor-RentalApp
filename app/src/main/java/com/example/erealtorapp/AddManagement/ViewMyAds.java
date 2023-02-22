package com.example.erealtorapp.AddManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.erealtorapp.R;
import com.example.erealtorapp.databinding.ActivityViewMyAdsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewMyAds extends AppCompatActivity {

    ActivityViewMyAdsBinding bind;
    MyAddAdapter adapter;
    ProgressDialog dilog;
    static ArrayList<PropertyClass> datalist = new ArrayList<PropertyClass>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityViewMyAdsBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        getSupportActionBar().hide();
        adapter = new MyAddAdapter(this, datalist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        bind.myadrecyclerview.setLayoutManager(layoutManager);
        bind.myadrecyclerview.setItemAnimator(new DefaultItemAnimator());
        bind.myadrecyclerview.setAdapter(adapter);
        dilog = new ProgressDialog(this);
        dilog.setTitle("Loading");
        dilog.setMessage("Data is loading");
        downloadadds();
    }

    private void downloadadds() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("Properties");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        dilog.show();
        ValueEventListener valueEventListener = myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for(DataSnapshot postsnapshot: snapshot.getChildren())
                {
                    String title = postsnapshot.child("title").getValue().toString();
                    String oid = postsnapshot.child("ownerID").getValue().toString();
                    String id = postsnapshot.getKey().toString();
                    int rent = Integer.parseInt(postsnapshot.child("rent").getValue().toString());
                    GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                    List<String> image = postsnapshot.child("images").getValue(t);
                    if(!datalist.contains(new PropertyClass(id,title, rent, image)))
                    {
                         if(oid.equals(auth.getUid().toString()))
                         {
                             datalist.add(new PropertyClass(id,title, rent, image));
                             adapter.notifyDataSetChanged();
                         }
                    }
                }
                dilog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}