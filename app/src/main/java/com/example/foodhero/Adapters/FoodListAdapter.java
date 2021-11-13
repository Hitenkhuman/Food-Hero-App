package com.example.foodhero.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhero.Models.Food;

import com.example.foodhero.R;

import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {
    private ArrayList<Food> list;
    private Context context;

    public FoodListAdapter(ArrayList<Food> list,Context context) {
        this.list = list;
        this.context = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView name,date;
        private  final Button btn;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            image = itemView.findViewById(R.id.profile_img);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            btn=itemView.findViewById(R.id.reqbtn);
        }


    }




    // Create new views (invoked by the layout manager)
    @Override
    public FoodListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_restaurant_layout, viewGroup, false);

        return new FoodListAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(FoodListAdapter.ViewHolder viewHolder, final int position) {


        Food food = list.get(position);
        viewHolder.image.setImageResource(food.getResImgUrl());
        viewHolder.name.setText(food.getResName());
        viewHolder.date.setText(food.getDate());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
