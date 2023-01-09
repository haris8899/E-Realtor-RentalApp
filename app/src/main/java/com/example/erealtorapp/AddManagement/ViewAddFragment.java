package com.example.erealtorapp.AddManagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.erealtorapp.R;
import com.example.erealtorapp.UserAccountsManagement.UserLoginActivity;
import com.example.erealtorapp.UserAccountsManagement.UserSignupActivity;
import com.example.erealtorapp.databinding.FragmentViewAddBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ViewAddFragment extends Fragment implements RecyclerItemSelectListener {
    static RecyclerView recyclerView;
    static ViewAddRecyclerViewAdapter adapter;
    static FragmentViewAddBinding bind;
    String key;
    static ArrayList<AddDataClass> datalist = new ArrayList<AddDataClass>();
    HashSet<AddDataClass> hashSet =new HashSet<AddDataClass>();
    AddDataClass dataClass;
    ProgressDialog dilog;

    public ViewAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentViewAddBinding.inflate(getLayoutInflater());
        View view = bind.getRoot();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        bind.viewaddrecyclerview.setLayoutManager(layoutManager);
        bind.viewaddrecyclerview.setItemAnimator(new DefaultItemAnimator());
        adapter = new ViewAddRecyclerViewAdapter(datalist,this);
        bind.viewaddrecyclerview.setAdapter(adapter);
        dilog=new ProgressDialog(getActivity());
        dilog.setTitle("Loading");
        dilog.setMessage("Data is loading");
        downloadadds();
        return view;
    }
    public void downloadadds()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("Ads");
        dilog.show();
        ValueEventListener valueEventListener = myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for(DataSnapshot postsnapshot: snapshot.getChildren())
                {
                    String id = postsnapshot.getKey().toString();
                    Log.d("Tag",id);
                    String title = postsnapshot.child("title").getValue().toString();
                    Log.d("Tag",title);
                    int rent = Integer.parseInt(postsnapshot.child("rent").getValue().toString());
                    Log.d("Tag",Integer.toString(rent));
                    String oid = postsnapshot.child("status").getValue().toString();
                    GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                    List<String> image = postsnapshot.child("images").getValue(t);
                    if(!datalist.contains(new AddDataClass(id, title, rent, image)))
                    {
                        if(oid.equals("true"))
                        datalist.add(new AddDataClass(id, title, rent, image));
                    }

                }
                dilog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getActivity(), ViewSingleAd.class);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("Ads");
        intent.putExtra("A1",datalist.get(position).getId().toString());
        startActivity(intent);

    }
}