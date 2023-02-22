package com.example.erealtorapp.AddManagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erealtorapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAddAdapter extends RecyclerView.Adapter<MyAddAdapter.ViewHolder>{
    private RecyclerView.ViewHolder holder;
    private int position;
    FirebaseDatabase database;
    DatabaseReference myref;
    String itemid;
    Context context;
    ArrayList<PropertyClass> addList = new ArrayList<PropertyClass>();
    public MyAddAdapter(Context context, ArrayList<PropertyClass> addList) {
        this.addList = addList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAddAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userprofilepage,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAddAdapter.ViewHolder holder, int position) {
        PropertyClass data = addList.get(position);
        try
        {
            Uri uri = Uri.parse(data.getImages().get(0));
            Picasso.get().load(uri).into(holder.dataimage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.Title.setText(data.getTitle());
        holder.Rent.setText(String.valueOf(data.getRent()));
        holder.deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Tag","id: "+data.getId());
                database = FirebaseDatabase.getInstance();
                myref = database.getReference("Properties").child(data.getId());
                myref.removeValue();
                StorageReference stref = FirebaseStorage.getInstance()
                        .getReference().child("Properties")
                        .child(data.getId()).child("images");
                for (int i = 0; i < data.getImages().size(); i++) {
                    stref.child(String.valueOf(i)).delete();
                }
            }
        });
        holder.updatebuttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,UpdateAdActivity.class);
                intent.putExtra("A1",data.getId().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return addList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView dataimage;
        TextView Title;
        TextView Rent;
        CardView cardView;
        Button deleteitem;
        Button updatebuttn;
        String itemid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dataimage = itemView.findViewById(R.id.addmainimagetext);
            Title = itemView.findViewById(R.id.addtitletext);
            Rent = itemView.findViewById(R.id.renttext);
            deleteitem = itemView.findViewById(R.id.deletead);
            updatebuttn = itemView.findViewById(R.id.editad);
        }
    }
    public Bitmap stringtobitmap(String string)
    {
        byte[] byteArray1;
        byteArray1 = Base64.decode(string, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray1, 0,
                byteArray1.length);
        return bmp;
    }
}
