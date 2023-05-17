package com.example.erealtorapp.ContractManagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erealtorapp.R;

import java.util.ArrayList;

public class TransactionAdaptor extends RecyclerView.Adapter<TransactionAdaptor.ViewHolder> {

    Context context;
    ArrayList<Transactions> TransactionsList;

    public TransactionAdaptor(Context context, ArrayList<Transactions> list) {
        TransactionsList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transactioncard,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdaptor.ViewHolder holder, int position) {
        Transactions transaction = TransactionsList.get(position);
        holder.TMessage.setText(transaction.getMessage());
        holder.THash.setText(transaction.getHash());
        holder.THash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClipboard(context,holder.THash.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return TransactionsList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView TMessage;
        TextView THash;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TMessage = itemView.findViewById(R.id.TransactionMessageText);
            THash = itemView.findViewById(R.id.TransactionHashText);
        }
    }
    private void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard =
                    (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard =
                    (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(context,
                "Text copied to clipboard",Toast.LENGTH_SHORT).show();
    }
}
