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

public class RequestStatusAdapter extends RecyclerView.Adapter<RequestStatusAdapter.ViewHolder> {
    private ArrayList<Food> list;
    private Context context;

    public RequestStatusAdapter(ArrayList<Food> list,Context context) {
        this.list = list;
        this.context = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView name,status;
        private  final Button btn;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            image = itemView.findViewById(R.id.profile_img);
            name = itemView.findViewById(R.id.name);
            status = itemView.findViewById(R.id.status);
            btn=itemView.findViewById(R.id.pickup);
        }


    }




    // Create new views (invoked by the layout manager)
    @Override
    public RequestStatusAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_request_status_layout, viewGroup, false);

        return new RequestStatusAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RequestStatusAdapter.ViewHolder viewHolder, final int position) {


        Food food = list.get(position);
        viewHolder.image.setImageResource(food.getResImgUrl());
        viewHolder.name.setText(food.getResName());
        viewHolder.status.setText(food.getStatus());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
