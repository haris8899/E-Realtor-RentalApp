package com.example.erealtorapp.AddManagement;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erealtorapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ViewAddRecyclerViewAdapter
        extends RecyclerView.Adapter<ViewAddRecyclerViewAdapter.ViewHolder> implements Filterable{
    private RecyclerView.ViewHolder holder;
    private int position;
    private RecyclerItemSelectListener itemSelectListener;
    ArrayList<PropertyClass> addList = new ArrayList<PropertyClass>();
    ArrayList<PropertyClass> addListfil;

    public ViewAddRecyclerViewAdapter(ArrayList<PropertyClass> addList, RecyclerItemSelectListener itemSelectListener) {
        this.addList = addList;
        this.addListfil = new ArrayList<>(addList);
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

    }

    @Override
    public int getItemCount() {
        return addList.size();
    }

    @Override
    public Filter getFilter() {
        return MyFilter;
    }
    public Filter MyFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<PropertyClass> FilteredList = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0)
            {
                FilteredList.addAll(addListfil);
            }
            else
            {
                String FilterPattern = charSequence.toString().toLowerCase().trim();
                for(PropertyClass add: addListfil)
                {
                    if(add.getAddress().toLowerCase().contains(charSequence))
                    {
                        FilteredList.add(add);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = FilteredList;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                addList.clear();
                addList.addAll((List)filterResults.values);
                notifyDataSetChanged();
        }
    };


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
