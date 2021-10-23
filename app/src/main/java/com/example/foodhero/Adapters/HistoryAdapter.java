package com.example.foodhero.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhero.Models.Ngo;
import com.example.foodhero.Models.Restuarant;
import com.example.foodhero.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {


    private ArrayList<Restuarant> restuarants;
    private Context context;

    public HistoryAdapter(ArrayList<Restuarant> restuarants, ArrayList<Ngo> ngos, Context context) {
        this.restuarants = restuarants;
        this.ngos = ngos;
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

        public TextView getTextView() {
            return textView;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomAdapter(String[] dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_history_layout, parent, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Restuarant host=restuarants.get(position);
        Ngo reciver=ngos.get(position);

        viewHolder.img1.setImageResource(host.getImgurl());
        viewHolder.img2.setImageResource(reciver.getImgurl());
        viewHolder.name1.setText(host.getName());
        viewHolder.name2.setText(reciver.getName());


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.length;
    }
}
