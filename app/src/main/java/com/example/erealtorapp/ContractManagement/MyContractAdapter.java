package com.example.erealtorapp.ContractManagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erealtorapp.AddManagement.MyAddAdapter;
import com.example.erealtorapp.AddManagement.PropertyClass;
import com.example.erealtorapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MyContractAdapter extends RecyclerView.Adapter<MyContractAdapter.ViewHolder> {
    private RecyclerView.ViewHolder holder;
    private int position;
    ArrayList<ContractClass> contractList = new ArrayList<ContractClass>();
    String relatedid="";
    FirebaseDatabase database;
    DatabaseReference myref;
    Context context;
    public MyContractAdapter(Context context, ArrayList<ContractClass> contractList) {
        this.contractList = contractList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_contract_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContractClass data = contractList.get(position);
        SharedPreferences sharedPreferences = context.getSharedPreferences("userfiles",0);
        String user = sharedPreferences.getString("A1","");
        if(data.getTenantID().equals(user))
        {
            relatedid = data.getLandlordID();
            holder.userstatus.setText("To Landlord");
        } else if(data.getLandlordID().equals(user)) {
            relatedid = data.getTenantID();
            holder.userstatus.setText("From Tenant");
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String relatedname = snapshot.child(relatedid).child("username").getValue().toString();
                Log.d("Tag",relatedname+" id: "+relatedid);
                holder.RelatedName.setText(relatedname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.Status.setText(data.getStatus());
        holder.Rentamount.setText(data.getRentAmount());
        holder.contractcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ViewContractSingle.class);
                intent.putExtra("A1",data.getContractID());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return contractList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView RelatedName;
        TextView userstatus;
        TextView Status;
        TextView Rentamount;
        CardView contractcard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RelatedName = itemView.findViewById(R.id.TenantNameText);
            userstatus = itemView.findViewById(R.id.UserStatus);
            Status = itemView.findViewById(R.id.StatusText);
            contractcard = itemView.findViewById(R.id.contractcard);
            Rentamount = itemView.findViewById(R.id.rentamounttext);
        }
    }
}
