package com.example.myapplication9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.item_details, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        newProduct product = mProducts.get(position);

        // Set item views based on your views and data model
        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(product.getProductName());
        TextView idTextView = holder.idTextView;
        idTextView.setText(product.getProductID());
        TextView quantityTextView = holder.quantityTextView;
        quantityTextView.setText(Integer.toString(Integer.parseInt(product.getProductQuantity())));
        TextView typeTextView = holder.typeTextView;
        typeTextView.setText(product.getProductType());
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView, idTextView, quantityTextView, typeTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.item_name);
            idTextView = (TextView) itemView.findViewById(R.id.item_id);
            quantityTextView = (TextView) itemView.findViewById(R.id.item_quantity);
            typeTextView = (TextView) itemView.findViewById(R.id.item_type);
        }
    }

    private List<newProduct> mProducts;

    public DetailsAdapter(List<newProduct> products) {
        mProducts = products;
    }
}
