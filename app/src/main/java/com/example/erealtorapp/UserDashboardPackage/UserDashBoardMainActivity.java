package com.example.erealtorapp.UserDashboardPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.erealtorapp.ProfileFragment;
import com.example.erealtorapp.R;
import com.example.erealtorapp.AddManagement.ViewAddFragment;
import com.example.erealtorapp.databinding.ActivityUserDashBoardMainBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDashBoardMainActivity extends AppCompatActivity {

    ActivityUserDashBoardMainBinding bind;
    FirebaseAuth auth;
    FirebaseDatabase data;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board_main);
        bind = ActivityUserDashBoardMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        bind.btmNavbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemid = item.getItemId();
                if(itemid == R.id.Userprofileicon)
                {
                    loadFragment(new ProfileFragment());
                } else if (itemid == R.id.viewaddicon)
                {
                    loadFragment(new ViewAddFragment());
                }
                return false;
            }
        });
        bind.btmNavbar.setSelectedItemId(R.id.viewaddicon);
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