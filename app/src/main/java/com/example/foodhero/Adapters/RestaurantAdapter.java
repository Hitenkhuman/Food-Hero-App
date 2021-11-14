package com.example.foodhero.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhero.Models.Restuarant;
import com.example.foodhero.R;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private ArrayList<Restuarant> list;
    private Context context;
    private OnRestaurantListner onRestaurantListner;
    public RestaurantAdapter(ArrayList<Restuarant> list,Context context,OnRestaurantListner onRestaurantListner){
        this.context=context;
        this.list=list;
        this.onRestaurantListner=onRestaurantListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_layout,parent,false);
        return new RestaurantAdapter.ViewHolder(view,onRestaurantListner);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.ViewHolder holder, int position) {
    Restuarant restuarant=list.get(position);
    holder.image.setImageResource(restuarant.getImgurl());
    holder.name.setText(restuarant.getName());
    holder.date.setText(restuarant.getJoinDate());
    holder.city.setText(restuarant.getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
        private final ImageView image;
        private final TextView name,date,city;
        private  final Button btn;
        private OnRestaurantListner onRestaurantListner;


        public ViewHolder(View view,OnRestaurantListner onRestaurantListner) {
            super(view);
            // Define click listener for the ViewHolder's View

            image = itemView.findViewById(R.id.profile_img);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            city = itemView.findViewById(R.id.city);
            btn=itemView.findViewById(R.id.delbtn);
            this.onRestaurantListner=onRestaurantListner;
           view.setOnClickListener(this);
           btn.setOnClickListener(new OnClickListener() {
               @Override
               public void onClick(View view) {
                   onRestaurantListner.onDeleteClick(getAdapterPosition());
               }
           });
        }

        @Override
        public void onClick(View view) {
            onRestaurantListner.onRestaurantClick(getAdapterPosition());
        }
    }
    public interface OnRestaurantListner{
         void onRestaurantClick(int position);
         void onDeleteClick(int position);
    }
}
