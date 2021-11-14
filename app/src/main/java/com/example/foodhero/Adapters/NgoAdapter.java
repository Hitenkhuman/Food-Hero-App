package com.example.foodhero.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhero.Models.Ngo;
import com.example.foodhero.Models.Restuarant;
import com.example.foodhero.R;

import java.util.ArrayList;

public class NgoAdapter extends RecyclerView.Adapter<NgoAdapter.ViewHolder>{
    private ArrayList<Ngo> list;
    private Context context;
    private OnNgoListner onNgoListner;
    public NgoAdapter(ArrayList<Ngo> list,Context context,OnNgoListner onNgoListner){
        this.context=context;
        this.list=list;
        this.onNgoListner=onNgoListner;
    }

    @NonNull
    @Override
    public NgoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_layout,parent,false);
        return new NgoAdapter.ViewHolder(view,onNgoListner);
    }

    @Override
    public void onBindViewHolder(@NonNull NgoAdapter.ViewHolder holder, int position) {
        Ngo ngo=list.get(position);
        holder.image.setImageResource(ngo.getImgurl());
        holder.name.setText(ngo.getName());
        holder.date.setText(ngo.getState());
        holder.city.setText(ngo.getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView image;
        private final TextView name,date,city;
        private  final Button btn;
        private OnNgoListner onNgoListner;

        public ViewHolder(View view,OnNgoListner onNgoListner) {
            super(view);
            // Define click listener for the ViewHolder's View

            image = itemView.findViewById(R.id.profile_img);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            city = itemView.findViewById(R.id.city);
            btn=itemView.findViewById(R.id.delbtn);
            this.onNgoListner=onNgoListner;
            view.setOnClickListener(this);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onNgoListner.onDelClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View view) {
            onNgoListner.onNgoClick(getAdapterPosition());
        }
    }
    public interface OnNgoListner{
         void onNgoClick(int position);
         void onDelClick(int position);
    }
}
