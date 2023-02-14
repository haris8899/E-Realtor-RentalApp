package com.example.erealtorapp.AdminPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.erealtorapp.R;
import com.example.erealtorapp.databinding.ActivityViewAllAgentsBinding;

public class ViewAllAgents extends AppCompatActivity {

    ActivityViewAllAgentsBinding bind;
    static RecyclerView agentRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityViewAllAgentsBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        agentRecycler = bind.AgentsRecyclerView;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        agentRecycler.setLayoutManager(layoutManager);
        agentRecycler.setItemAnimator(new DefaultItemAnimator());
    }
}