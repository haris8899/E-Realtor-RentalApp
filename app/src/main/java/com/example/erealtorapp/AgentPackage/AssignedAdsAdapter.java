package com.example.erealtorapp.AgentPackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erealtorapp.AddManagement.PropertyClass;
import com.example.erealtorapp.AddManagement.ViewSingleAd;
import com.example.erealtorapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AssignedAdsAdapter extends RecyclerView.Adapter<AssignedAdsAdapter.ViewHolder>{
    private RecyclerView.ViewHolder holder;
    private int position;
    FirebaseDatabase database;
    DatabaseReference myref;
    String itemid;
    Context context;
    Activity a;
    private static final int SMS_PERMISSION_CODE = 100;
    ArrayList<PropertyClass> addList = new ArrayList<PropertyClass>();
    public AssignedAdsAdapter(Activity a,Context context, ArrayList<PropertyClass> addList) {
        this.addList = addList;
        this.context = context;
        this.a = a;
    }
    @NonNull
    @Override
    public AssignedAdsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignedadscardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignedAdsAdapter.ViewHolder holder, int position) {
        PropertyClass data = addList.get(position);
       // holder.dataimage.setImageBitmap(stringtobitmap(data.getImages().get(0)));
        holder.Title.setText(data.getTitle());
        holder.Rent.setText(String.valueOf(data.getRent()));
        holder.clicklayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewSingleAd.class);
                intent.putExtra("A1",data.getId().toString());
                context.startActivity(intent);
            }
        });
        holder.acceptadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = data.getId().toString();
                myref.child(id).child("status").setValue("true");
            }
        });
        holder.rejectadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = data.getId().toString();
                myref.child(id).removeValue();
            }
        });
        holder.contactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AgentContactOwner.class);
                String id = data.getId().toString();
                intent.putExtra("A1",id);
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
        Button acceptadbtn;
        Button rejectadbtn;
        Button contactbtn;
        LinearLayout clicklayout;
        String itemid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dataimage = itemView.findViewById(R.id.addmainimagetext);
            Title = itemView.findViewById(R.id.addtitletext);
            Rent = itemView.findViewById(R.id.renttext);
            acceptadbtn = itemView.findViewById(R.id.Accepbtn);
            rejectadbtn = itemView.findViewById(R.id.Rejectbtn);
            contactbtn = itemView.findViewById(R.id.Contactbtn);
            clicklayout = itemView.findViewById(R.id.Clicklinearlayout);
            database = FirebaseDatabase.getInstance();
            myref = database.getReference("Ads");
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
