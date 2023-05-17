package com.example.erealtorapp.AgentPackage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erealtorapp.databinding.FragmentAgentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AgentFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase data;
    DatabaseReference myref;
    FragmentAgentBinding bind;
    ProgressDialog dilog;
    public AgentFragment() {
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
                String rating = snapshot.child(uid).child("ratings").child("rating").getValue().toString();
                String numrating = snapshot.child(uid).child("ratings").child("num").getValue().toString();
                int num = (int) Float.parseFloat(numrating);
                numrating = String.valueOf(num);
                bind.Profilenameesttext.setText(name);
                bind.Profileimageview.setImageBitmap(stringtobitmap(image));
                bind.profileemailtext.setText(email);
                String ratingstr = "Ratings: "+rating+"/5"+" ("+numrating+")";
                bind.AgentRatingtext.setText(ratingstr);
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
//        bind.RecruitAgent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), RecruitAgent.class);
//                startActivity(intent);
//            }
//        });
        bind.ViewAssignedAdsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewAssignedAdsActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bind = FragmentAgentBinding.inflate(getLayoutInflater());
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