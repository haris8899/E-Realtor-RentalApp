package com.example.erealtorapp.AddManagement;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.erealtorapp.R;
import com.example.erealtorapp.databinding.FragmentViewAddBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    static ArrayList<PropertyClass> datalist = new ArrayList<PropertyClass>();
    HashSet<PropertyClass> hashSet =new HashSet<PropertyClass>();
    PropertyClass dataClass;
    Bundle bundle;
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
        bundle = this.getArguments();
        ArrayAdapter<CharSequence> spinneradapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sorting_array, android.R.layout.simple_spinner_item);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        DatabaseReference myref = database.getReference("Properties");
        dilog.show();
        ValueEventListener valueEventListener = myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for(DataSnapshot postsnapshot: snapshot.getChildren())
                {
                    String id = postsnapshot.getKey().toString();
                    String title = postsnapshot.child("title").getValue().toString();
                    int rent = Integer.parseInt(postsnapshot.child("rent").getValue().toString());
                    int size = Integer.parseInt(postsnapshot.child("plotsize").getValue().toString());
                    String oid = postsnapshot.child("status").getValue().toString();
                    GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                    List<String> image = postsnapshot.child("images").getValue(t);
                    if(!datalist.contains(new PropertyClass(id, title, rent,size,image)))
                    {
                        if(oid.equals("true"))
                        {
                            if (bundle !=null)
                            {
                                bind.viewaddtitletext.setText("Search Results");
                                String pricelower = bundle.getString("pricelower");
                                String pricehigher = bundle.getString("pricehigher");
                                String sizehigher = bundle.getString("sizehigher");
                                String sizelower = bundle.getString("sizelower");
                                if(sizelower == null || sizelower.length() == 0)
                                {
                                    sizelower = "0";
                                }
                                if(sizehigher==null || sizehigher.length()==0)
                                {
                                    sizehigher = String.valueOf(Integer.MAX_VALUE);
                                }
                                if(pricelower == null || pricelower.length() == 0)
                                {
                                    pricelower = "0";
                                }
                                if(pricehigher==null || pricehigher.length()==0)
                                {
                                    pricehigher = String.valueOf(Integer.MAX_VALUE);
                                }
                                if(rent >= Integer.parseInt(pricelower)
                                        && rent <= Integer.parseInt(pricehigher)
                                        && size >=Integer.parseInt(sizelower)
                                        && size <= Integer.parseInt(sizehigher))
                                {
                                    datalist.add(new PropertyClass(id, title, rent,size,image));
                                }
                            }
                            else
                            {
                                datalist.add(new PropertyClass(id, title, rent,size,image));
                            }
                            Collections.sort(datalist, new Comparator<PropertyClass>() {
                                @Override
                                public int compare(PropertyClass propertyClass, PropertyClass t1) {
                                    return Integer.valueOf(propertyClass.getRent())
                                            .compareTo(Integer.valueOf(t1.getRent()));
                                }
                            });
                            adapter.notifyDataSetChanged();
                        }
                    }

                }
                //spinnersort();
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