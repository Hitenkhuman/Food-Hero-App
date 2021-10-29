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
import com.example.foodhero.Models.Ngo;
import com.example.foodhero.Models.Restuarant;
import com.example.foodhero.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {


    private ArrayList<Food> list;
    private Context context;

    public HistoryAdapter(ArrayList<Food> list,  Context context) {
        this.list = list;
        this.context = context;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name1,name2,status;
        private final ImageView img1,img2;
        private  final Button viewbtn;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            name1= view.findViewById(R.id.name1);
            name2= view.findViewById(R.id.name2);
            img1= view.findViewById(R.id.image1);
            img2= view.findViewById(R.id.image2);
            status= view.findViewById(R.id.status);
            viewbtn= view.findViewById(R.id.histrorybtn);
        }


    }



    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_history_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Food data=list.get(position);


//        viewHolder.img1.setImageResource(data.getResImgUrl());
//        viewHolder.img2.setImageResource(data.getNgoIImgUrl());
        viewHolder.name1.setText(data.getResName());
        viewHolder.name2.setText(data.getNgoName());
        viewHolder.status.setText(data.getStatus());
        viewHolder.img1.setImageResource(data.getResImgUrl());
        viewHolder.img2.setImageResource(data.getNgoIImgUrl());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
