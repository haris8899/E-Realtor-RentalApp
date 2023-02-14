package com.example.erealtorapp.AdminPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erealtorapp.AddManagement.AddDataClass;
import com.example.erealtorapp.AddManagement.MyAddAdapter;
import com.example.erealtorapp.AddManagement.RecyclerItemSelectListener;
import com.example.erealtorapp.AgentPackage.AgentClass;
import com.example.erealtorapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewAgentRequestsAdapter extends RecyclerView.Adapter<ViewAgentRequestsAdapter.ViewHolder> {
    private RecyclerView.ViewHolder holder;
    private int position;
    FirebaseDatabase database;
    DatabaseReference myref;
    String itemid;
    Context context;
    private RecyclerItemSelectListener itemSelectListener;
    ArrayList<AgentClass> agentList = new ArrayList<AgentClass>();

    public ViewAgentRequestsAdapter(ArrayList<AgentClass> agentList, RecyclerItemSelectListener itemSelectListener) {
        this.agentList = agentList;
        this.itemSelectListener = itemSelectListener;
    }

    @NonNull
    @Override
    public ViewAgentRequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.agentrequestcard,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAgentRequestsAdapter.ViewHolder holder, int position) {
        AgentClass data = agentList.get(position);
        holder.agentimage.setImageBitmap(stringtobitmap(data.getProfilePicture()));
        holder.AgentName.setText(data.getName());
        holder.AgentEmail.setText(data.getEmail());
    }

    @Override
    public int getItemCount() {
        return agentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView agentimage;
        TextView AgentName;
        TextView AgentEmail;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            agentimage = itemView.findViewById(R.id.agentimage);
            AgentName = itemView.findViewById(R.id.agentusername);
            AgentEmail = itemView.findViewById(R.id.agentemail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemSelectListener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION);
                        {
                            itemSelectListener.onItemClicked(position);
                        }
                    }
                }
            });
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
