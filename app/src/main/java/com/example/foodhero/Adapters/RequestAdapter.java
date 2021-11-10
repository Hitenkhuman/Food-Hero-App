package com.example.foodhero.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhero.Models.Food;
import com.example.foodhero.Models.Request;
import com.example.foodhero.R;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {


    private ArrayList<Request> list;
    private Context context;

    public RequestAdapter(ArrayList<Request> list,  Context context) {
        this.list = list;
        this.context = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView NGOImage;
        private final TextView NGOName,time;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            NGOImage = itemView.findViewById(R.id.profile_img);
            NGOName = itemView.findViewById(R.id.NGO_name);
            time = itemView.findViewById(R.id.request_time);
        }


    }




    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_request_layout, viewGroup, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        Request req = list.get(position);
        viewHolder.NGOImage.setImageResource(req.getPic());
        viewHolder.NGOName.setText(req.getNGO_name());
        viewHolder.time.setText(req.getRequest_time());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
