package com.example.erealtorapplication.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.example.erealtorapplication.MainActivity;
import com.example.erealtorapplication.R;
import com.example.erealtorapplication.databinding.ActivitySplashBinding;
import com.example.erealtorapplication.onboardingscreen.OnboardingActivtiy;

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
                Intent intent=new Intent(SplashActivity.this, OnboardingActivtiy.class);
                startActivity(intent);
                finish();
            }
        },5000);

    }
}