package com.example.erealtorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.erealtorapp.AddManagement.PostAddActivity;
import com.example.erealtorapp.AddManagement.ViewAddFragment;
import com.example.erealtorapp.AddManagement.ViewMyAds;
import com.example.erealtorapp.AgentPackage.ReqruitAgentRequestActivity;
import com.example.erealtorapp.UserDashboardPackage.UserDashBoardMainActivity;
import com.example.erealtorapp.databinding.ActivityUserDashBoardMainBinding;
import com.example.erealtorapp.databinding.FragmentProfileBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    //variables
    FirebaseAuth auth;
    FirebaseDatabase data;
    DatabaseReference myref;
    FragmentProfileBinding bind;
    ProgressDialog dilog;
    String image;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;


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
        dilog = new ProgressDialog(getActivity());
        dilog.setTitle("Loading");
        dilog.setMessage("Data is loading");
        dilog.show();
        //Menu Hooks
        drawerLayout = bind.drawerLayout;
        navigationView = bind.navigationView;

        //Navigation Drawer
        navigationDrawer();

        ValueEventListener valueEventListener = myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child(uid).child("username").getValue().toString();
                image = snapshot.child(uid).child("profileURI").getValue().toString();
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
       /* bind.LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                getActivity().finish();
            }
        });*/
        bind.AddPropertyDashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPostButton = new Intent(getActivity(), PostAddActivity.class);
                startActivity(intentPostButton);
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
                intent.putExtra("A1", bind.Profilenameesttext.getText().toString());
                intent.putExtra("A2", bind.profileemailtext.getText().toString());
                intent.putExtra("A3", bind.profilephonetext.getText().toString());
                intent.putExtra("A4",image);
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
    // For Navigation Drawer
    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        bind.menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == R.id.nav_home)
        {
            ((UserDashBoardMainActivity)getActivity()).loadFragment(new ViewAddFragment());
        }
        return true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bind = FragmentProfileBinding.inflate(getLayoutInflater());
        return bind.getRoot();

    }

    public Bitmap stringtobitmap(String string) {
        byte[] byteArray1;
        byteArray1 = Base64.decode(string, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray1, 0,
                byteArray1.length);
        return bmp;
    }

}