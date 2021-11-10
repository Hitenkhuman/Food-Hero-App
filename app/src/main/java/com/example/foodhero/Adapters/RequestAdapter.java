package com.example.foodhero.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhero.Models.Request;
import com.example.foodhero.R;

import java.util.ArrayList;

public class RequestAdapter<context> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Request> list;
    private Context context;

    public RequestAdapter(ArrayList<Request> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private final ImageView NGOImage;
        private final TextView NGOName,time;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            NGOImage = itemView.findViewById(R.id.profile_img);
            NGOName = itemView.findViewById(R.id.NGO_name);
            time = itemView.findViewById(R.id.request_time);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_request_layout , parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Request req = list.get(position);
        holder.NGOImage.setImageResource(req.getPic());
        holder.NGOName.setText(req.getNGO_name());
        holder.time.setText(req.getRequest_time());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
