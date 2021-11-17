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

import com.bumptech.glide.Glide;
import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Models.Ngo;
import com.example.foodhero.R;

import java.util.ArrayList;

public class VerificationListAdapter extends RecyclerView.Adapter<VerificationListAdapter.ViewHolder>{
    private ArrayList<Ngo> list;
    private Context context;
    private OnVerificationListListner onVerificationListListner;
    private final String parentdir= ApiClient.BASE_URL+"profile_pic/";
    public VerificationListAdapter(ArrayList<Ngo> list,Context context,OnVerificationListListner onVerificationListListner){
        this.context=context;
        this.list=list;
        this.onVerificationListListner=onVerificationListListner;
    }

    @NonNull
    @Override
    public VerificationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_verification_list_layout,parent,false);
        return new VerificationListAdapter.ViewHolder(view,onVerificationListListner);
    }

    @Override
    public void onBindViewHolder(@NonNull VerificationListAdapter.ViewHolder holder, int position) {
        Ngo ngo=list.get(position);
       // holder.image.setImageResource(ngo.getImgurl());

        Glide.with(context).load(parentdir+ngo.getImgurl()).into(holder.image);
        holder.name.setText(ngo.getName());
        holder.date.setText(ngo.getState());
        holder.city.setText(ngo.getAddress());
        holder.status.setText(ngo.getVerification_status());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView image;
        private final TextView name,date,city,status;
        private OnVerificationListListner onVerificationListListner;


        public ViewHolder(View view,OnVerificationListListner onVerificationListListner) {
            super(view);
            // Define click listener for the ViewHolder's View

            image = itemView.findViewById(R.id.profile_img);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            city = itemView.findViewById(R.id.city);
            status=itemView.findViewById(R.id.status);
            this.onVerificationListListner=onVerificationListListner;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onVerificationListListner.onVerificationListClick(getAdapterPosition());
        }
    }
    public interface OnVerificationListListner{
        void onVerificationListClick(int position);
    }
}
