package com.example.erealtorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.erealtorapp.AddManagement.PostAddActivity;
import com.example.erealtorapp.AddManagement.ViewMyAds;
import com.example.erealtorapp.AgentPackage.ReqruitAgentRequestActivity;
import com.example.erealtorapp.databinding.ActivityUserDashBoardMainBinding;
import com.example.erealtorapp.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase data;
    DatabaseReference myref;
    FragmentProfileBinding bind;
    ProgressDialog dilog;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();
        data = FirebaseDatabase.getInstance();
        String uid = auth.getCurrentUser().getUid().toString();
        myref = data.getReference("Users");
        dilog=new ProgressDialog(getActivity());
        dilog.setTitle("Loading");
        dilog.setMessage("Data is loading");
        dilog.show();
        ValueEventListener valueEventListener = myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child(uid).child("username").getValue().toString();
                String image = snapshot.child(uid).child("profileURI").getValue().toString();
                String email = snapshot.child(uid).child("email").getValue().toString();
                String phone = snapshot.child(uid).child("phone").getValue().toString();
                bind.Profilenameesttext.setText(name);
                bind.Profileimageview.setImageBitmap(stringtobitmap(image));
                bind.profileemailtext.setText(email);
                bind.profilephonetext.setText(phone);
                dilog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bind.LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                getActivity().finish();
            }
        });
        bind.PostAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostAddActivity.class);
                startActivity(intent);
            }
        });
        bind.requestagentstatusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReqruitAgentRequestActivity.class);
                startActivity(intent);
            }
        });
        bind.ViewMyAdsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   Intent intent = new Intent(getActivity(), ViewMyAds.class);
                   startActivity(intent);

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bind = FragmentProfileBinding.inflate(getLayoutInflater());
        return bind.getRoot();

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