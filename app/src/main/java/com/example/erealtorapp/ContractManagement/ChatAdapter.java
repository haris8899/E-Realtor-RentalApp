package com.example.erealtorapp.ContractManagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erealtorapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    ArrayList<MessagesClass> messagesList;
    private RecyclerView.ViewHolder holder;
    private int position;
    FirebaseDatabase database;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference myref;
    String itemid;
    private static final int VIEW_TYPE_ITEM_1 = 1;
    private static final int VIEW_TYPE_ITEM_2 = 2;
    public ChatAdapter(ArrayList<MessagesClass> messagesList)
    {
        this.messagesList = messagesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == VIEW_TYPE_ITEM_1)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sent_message,parent,false);
        }
        else
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.receive_message,parent,false);
        }
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if(messagesList.get(position).getSender().equals(auth.getCurrentUser().getUid()))
        {
            return VIEW_TYPE_ITEM_1;
        }
        else return VIEW_TYPE_ITEM_2;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessagesClass message = messagesList.get(position);
        if(message.getSender().equals(auth.getCurrentUser().getUid()))
        {
            holder.sentmessage.setText(message.getMessage());
        }
        else {
            holder.receivedmessage.setText(message.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sentmessage;
        TextView receivedmessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sentmessage = itemView.findViewById(R.id.sent_messagetext);
            receivedmessage = itemView.findViewById(R.id.receive_messagetext);
        }
    }
}
