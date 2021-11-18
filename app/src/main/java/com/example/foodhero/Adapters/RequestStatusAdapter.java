package com.example.foodhero.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Models.Food;
import com.example.foodhero.Models.Request;
import com.example.foodhero.R;

import java.util.ArrayList;

public class RequestStatusAdapter extends RecyclerView.Adapter<RequestStatusAdapter.ViewHolder> {
    private ArrayList<Request> list;
    private Context context;
    private OnRequestListner onRequestListner;
    private final String parentdir= ApiClient.BASE_URL+"profile_pic/";
    public RequestStatusAdapter(ArrayList<Request> list,Context context,OnRequestListner onRequestListner) {
        this.list = list;
        this.context = context;
        this.onRequestListner=onRequestListner;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView name,status;
        private  final Button btn;
        OnRequestListner onRequestListner;


        public ViewHolder(View view,OnRequestListner onRequestListner) {
            super(view);
            // Define click listener for the ViewHolder's View

            image = itemView.findViewById(R.id.profile_img);
            name = itemView.findViewById(R.id.name);
            status = itemView.findViewById(R.id.status);
            btn=itemView.findViewById(R.id.pickup);
            this.onRequestListner=onRequestListner;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRequestListner.onRequestClick(getAdapterPosition());
                }
            });
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRequestListner.onPickupClick(getAdapterPosition());
                }
            });
        }


    }




    // Create new views (invoked by the layout manager)
    @Override
    public RequestStatusAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_request_status_layout, viewGroup, false);

        return new RequestStatusAdapter.ViewHolder(view,onRequestListner);
    }


    @Override
    public void onBindViewHolder(RequestStatusAdapter.ViewHolder viewHolder, final int position) {


        Request food = list.get(position);
        //viewHolder.image.setImageResource(food.getResImgUrl());
        Glide.with(context).load(parentdir+food.getRes_id().getImgurl()).into(viewHolder.image);
        viewHolder.name.setText(food.getRes_id().getName());
        viewHolder.status.setText(food.getRequest_status());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface OnRequestListner{
        void onRequestClick(int position);
        void onPickupClick(int position);
    }
}
