package com.example.erealtorapp.AdminPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.erealtorapp.R;
import com.example.erealtorapp.databinding.ActivityViewSingleAdBinding;

public class ViewSingleAgentRequest extends AppCompatActivity {

    ActivityViewSingleAdBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityViewSingleAdBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
    }
}