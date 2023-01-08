package com.example.erealtorapp.AddManagement;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erealtorapp.R;

import java.util.ArrayList;

public class ViewAddRecyclerViewAdapter
        extends RecyclerView.Adapter<ViewAddRecyclerViewAdapter.ViewHolder> {
    private RecyclerView.ViewHolder holder;
    private int position;
    private RecyclerItemSelectListener itemSelectListener;
    ArrayList<AddDataClass> addList = new ArrayList<AddDataClass>();

    public ViewAddRecyclerViewAdapter(ArrayList<AddDataClass> addList, RecyclerItemSelectListener itemSelectListener) {
        this.addList = addList;
        this.itemSelectListener = itemSelectListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.addviewslayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddDataClass data = addList.get(position);
        holder.dataimage.setImageBitmap(stringtobitmap(data.getImages().get(0)));
        holder.Title.setText(data.getTitle());
        holder.Rent.setText(String.valueOf(data.getRent()));

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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dataimage = itemView.findViewById(R.id.addmainimagetext);
            Title = itemView.findViewById(R.id.addtitletext);
            Rent = itemView.findViewById(R.id.renttext);
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
