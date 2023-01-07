package com.example.erealtorapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erealtorapp.AddManagement.PostAddActivity;
import com.example.erealtorapp.databinding.ActivityUserDashBoardMainBinding;
import com.example.erealtorapp.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase data;
    DatabaseReference myref;
    FragmentProfileBinding bind;
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
        ValueEventListener valueEventListener = myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot postsnapshot: snapshot.getChildren())
//                {
//                    String mess = postsnapshot.child(uid).child("username").getValue().toString();
//                    Log.d("Tag", mess);
//                    bind.textView1.setText(mess);
//
//                }
                String mess = snapshot.child(uid).child("username").getValue().toString();
                bind.Profilenameesttext.setText(mess);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bind.PostAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostAddActivity.class);
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
}