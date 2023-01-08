package com.example.erealtorapp.AddManagement;

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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    static RecyclerView recyclerView;
    static ViewAddRecyclerViewAdapter adapter;
    static FragmentViewAddBinding bind;
    static ArrayList<AddDataClass> datalist = new ArrayList<AddDataClass>();
    HashSet<AddDataClass> hashSet =new HashSet<AddDataClass>();
    AddDataClass dataClass;

    public ViewAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentViewAddBinding.inflate(getLayoutInflater());
        View view = bind.getRoot();
        adapter = new ViewAddRecyclerViewAdapter(datalist,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        bind.viewaddrecyclerview.setLayoutManager(layoutManager);
        bind.viewaddrecyclerview.setItemAnimator(new DefaultItemAnimator());
        bind.viewaddrecyclerview.setAdapter(adapter);
        downloadadds();
        return view;
    }
    public void downloadadds()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("Ads");
        ValueEventListener valueEventListener = myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for(DataSnapshot postsnapshot: snapshot.getChildren())
                {
                    String title = postsnapshot.child("title").getValue().toString();
                    Log.d("Tag",title);
                    int rent = Integer.parseInt(postsnapshot.child("rent").getValue().toString());
                    Log.d("Tag",Integer.toString(rent));
                    GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                    List<String> image = postsnapshot.child("images").getValue(t);
                    if(!datalist.contains(new AddDataClass(title, rent, image)))
                    {
                        Log.d("Tag","Dublicate value");
                        datalist.add(new AddDataClass(title, rent, image));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getActivity(), ViewSingleAd.class);
        startActivity(intent);

    }
}