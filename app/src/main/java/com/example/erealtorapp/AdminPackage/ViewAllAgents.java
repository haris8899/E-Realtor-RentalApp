package com.example.erealtorapp.AdminPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.erealtorapp.AddManagement.RecyclerItemSelectListener;
import com.example.erealtorapp.AgentPackage.AgentClass;
import com.example.erealtorapp.R;
import com.example.erealtorapp.databinding.ActivityViewAgentRequestsBinding;
import com.example.erealtorapp.databinding.ActivityViewAllAgentsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class ViewAllAgents extends AppCompatActivity implements RecyclerItemSelectListener {

    //variables
    ActivityViewAllAgentsBinding bind;
    static RecyclerView agentRecycler;
    private ProgressDialog dialog;
    String key;
    static ArrayList<AgentClass> datalist = new ArrayList<AgentClass>();
    HashSet<AgentClass> hashSet = new HashSet<AgentClass>();
    AgentClass dataClass;
    ViewAgentRequestsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityViewAllAgentsBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        agentRecycler = bind.AgentsRecyclerView;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        agentRecycler.setLayoutManager(layoutManager);
        agentRecycler.setItemAnimator(new DefaultItemAnimator());

        adapter = new ViewAgentRequestsAdapter(datalist, this);
        agentRecycler.setAdapter(adapter);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Data is loading");
        DownloadAgentRequestList();
    }

    private void DownloadAgentRequestList() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("Agent");
        dialog.show();
        ValueEventListener valueEventListener = myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for (DataSnapshot postsnapshot : snapshot.getChildren()) {
                    String id = postsnapshot.getKey();
                    String image =postsnapshot.child("profilePicture").getValue().toString();
                    String name = postsnapshot.child("name").getValue().toString();
                    String email = postsnapshot.child("email").getValue().toString();
                    Log.d("Tag", id);
                    Log.d("Tag", name);
                    if (!datalist.contains(new AgentClass(id, name, image, email))) {
                        datalist.add(new AgentClass(id, name, image, email));
                        adapter.notifyDataSetChanged();
                    }

                }
                dialog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClicked(int position) {
      /*  Intent intent = new Intent(this, ViewSingleAgentRequest.class);
        intent.putExtra("A1", datalist.get(position).getAgentID().toString());
        startActivity(intent);
        Log.d("Tag", "Item Clicked");*/
    }
}