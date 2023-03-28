package com.example.erealtorapp.SplashScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.erealtorapp.R;
import com.example.erealtorapp.UserAccountsManagement.UserLoginActivity;
import com.example.erealtorapp.UserDashboardPackage.UserDashBoardMainActivity;
import com.example.erealtorapp.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SplashActivity extends AppCompatActivity {
    //Variables
    ActivitySplashBinding binding;
    Animation topAnim,bottomAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Hide Actionbar
        getSupportActionBar().hide();
        //line to hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Adding Resources
        binding.logoImg.setImageResource(R.drawable.logo);
        binding.nameL.setImageResource(R.drawable.name);
        //Assigning values to animation variables
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        //Assigning Animations to image and textview
        binding.logoImg.setAnimation(topAnim);
        binding.nameL.setAnimation(bottomAnim);
        //Setting Animation

        binding.logoImg.animate().translationY(2000).setDuration(1000).setStartDelay(4000);
        binding.nameL.animate().translationY(2000).setDuration(1000).setStartDelay(4000);
        binding.anim2.animate().translationY(1600).setDuration(1000).setStartDelay(5000);
        //To set delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                if(auth.getCurrentUser()==null)
                {
                    Intent intent=new Intent(SplashActivity.this, UserLoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this,
                            UserDashBoardMainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },5000);

    }
}