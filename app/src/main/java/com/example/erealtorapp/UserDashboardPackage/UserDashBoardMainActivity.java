package com.example.erealtorapp.UserDashboardPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.erealtorapp.AdminPackage.adminProfileFragment;
import com.example.erealtorapp.ProfileFragment;
import com.example.erealtorapp.R;
import com.example.erealtorapp.AddManagement.ViewAddFragment;
import com.example.erealtorapp.databinding.ActivityUserDashBoardMainBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class UserDashBoardMainActivity extends AppCompatActivity{

    ActivityUserDashBoardMainBinding bind;
    FirebaseAuth auth;
    FirebaseDatabase data;
    DatabaseReference myref;
    public static String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board_main);
        bind = ActivityUserDashBoardMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        getSupportActionBar().hide();
        Intent intent = getIntent();
        type = intent.getStringExtra("A1");
        Log.d("Tag",type);
        data = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        myref = data.getReference("Users");
        bind.btmNavbar.setSelectedItemId(R.id.Userprofileicon);
        if(Objects.equals(type, "admin"))
            loadFragment(new adminProfileFragment());
        else if(Objects.equals(type, "Simple User"))
            loadFragment(new ProfileFragment());
        bind.btmNavbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemid = item.getItemId();
                if(itemid == R.id.Userprofileicon)
                {
                    bind.btmNavbar.getMenu().findItem(R.id.Userprofileicon).setChecked(true);
                    if(Objects.equals(type, "admin"))
                        loadFragment(new adminProfileFragment());
                    else if(Objects.equals(type, "Simple User"))
                        loadFragment(new ProfileFragment());
                } else if (itemid == R.id.viewaddicon)
                {
                    bind.btmNavbar.getMenu().findItem(R.id.viewaddicon).setChecked(true);
                    loadFragment(new ViewAddFragment());
                }
                return false;
            }
        });
        //bind.textView1.setText(uid);

    }
    public void loadFragment(Fragment fr)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.DashboardContainer, fr);
        ft.commit();
    }
}