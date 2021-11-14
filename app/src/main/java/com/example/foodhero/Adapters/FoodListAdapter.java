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
    private OnFoodListListner onFoodListListner;

    public FoodListAdapter(ArrayList<Food> list,Context context,OnFoodListListner onFoodListListner) {
        this.list = list;
        this.context = context;
        this.onFoodListListner=onFoodListListner;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView name,date;
        private  final Button btn;
        private OnFoodListListner onFoodListListner;


        public ViewHolder(View view,OnFoodListListner onFoodListListner) {
            super(view);
            // Define click listener for the ViewHolder's View

            image = itemView.findViewById(R.id.profile_img);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            btn=itemView.findViewById(R.id.reqbtn);
            this.onFoodListListner=onFoodListListner;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onFoodListListner.OnFoodClick(getAdapterPosition());
                }
            });
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onFoodListListner.OnRequestClick(getAdapterPosition());
                }
            });

        }


    }




    // Create new views (invoked by the layout manager)
    @Override
    public FoodListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_restaurant_layout, viewGroup, false);

        return new FoodListAdapter.ViewHolder(view,onFoodListListner);
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

    public interface OnFoodListListner{
        void OnFoodClick(int position);
        void OnRequestClick(int position);
    }
}
