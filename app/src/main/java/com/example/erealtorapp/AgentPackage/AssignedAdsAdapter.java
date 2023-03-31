package com.example.erealtorapp.AgentPackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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
        try
        {
            Uri uri = Uri.parse(data.getImages().get(0));
            Picasso.get().load(uri).into(holder.dataimage);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                StorageReference stref = FirebaseStorage.getInstance()
                        .getReference().child("Properties")
                        .child(data.getId()).child("images");
                for (int i = 0; i < data.getImages().size(); i++) {
                    stref.child(String.valueOf(i)).delete();
                }

            }
        });
        holder.contactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myref2 = FirebaseDatabase.getInstance().getReference("Users");
                myref2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String to = snapshot.child(data.getOwnerID()).child("email").getValue().toString();
                        String subject = "About Posted Property";
                        String message = "";
                        SendEmail(to,subject,message);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
    private void SendEmail(String to, String subject, String message)
    {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);

        //need this to prompts email client only
        email.setType("message/rfc822");

        context.startActivity(Intent.createChooser(email, "Choose an Email client :"));
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
            myref = database.getReference("Properties");
        }
    }
}
