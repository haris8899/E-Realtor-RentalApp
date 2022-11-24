package com.example.erealtorapplication.onboardingscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.erealtorapplication.MainActivity;
import com.example.erealtorapplication.R;
import com.example.erealtorapplication.databinding.ActivityOnboardingActivtiyBinding;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;

public class OnboardingActivtiy extends AppCompatActivity {
    //Variables
    ActivityOnboardingActivtiyBinding binding;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOnboardingActivtiyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Hide Actionbar
        getSupportActionBar().hide();
        //line to hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Assigning Fragment manager
        fragmentManager=getSupportFragmentManager();

        final PaperOnboardingFragment paperFragment=PaperOnboardingFragment.newInstance(getDataOnBoarding());
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, paperFragment);
        fragmentTransaction.commit();



    }

    private ArrayList<PaperOnboardingPage> getDataOnBoarding() {
        PaperOnboardingPage scr1 = new PaperOnboardingPage("Search Ads", "Search Ads according to your desire"
                , Color.parseColor("#00FFFF"), R.drawable.ads, R.drawable.ads);
        PaperOnboardingPage scr2 = new PaperOnboardingPage("Make Contract", "Make contract Digitally By using the app"
                , Color.parseColor("#3399FF"), R.drawable.sc, R.drawable.sc);
        PaperOnboardingPage scr3 = new PaperOnboardingPage("Make Payments", "Make Rental Payments using the app"
                , Color.parseColor("#FF9933"), R.drawable.paymentmethod, R.drawable.paymentmethod);
        PaperOnboardingPage scr4 = new PaperOnboardingPage("Log History", "Log property history based on your experience"
                , Color.parseColor("#3399FF"), R.drawable.logs, R.drawable.logs);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        elements.add(scr4);
        return elements;
    }
}