package com.example.campusrider.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusrider.Model.GrocerySummaryModel;
import com.example.campusrider.Model.OrderModel;
import com.example.campusrider.R;
import com.example.campusrider.databinding.GroceryOrderBinding;
import com.example.campusrider.databinding.GrocerySummeryBinding;

import java.util.ArrayList;

public class GrocerySummaryAdapter extends RecyclerView.Adapter<GrocerySummaryAdapter.ViewHolder>{
    Context context;
    ArrayList<GrocerySummaryModel> list;

    public GrocerySummaryAdapter(Context context, ArrayList<GrocerySummaryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_summery,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GrocerySummaryModel grocerySummaryModel=list.get(position);
        holder.binding.itemTextView.setText(grocerySummaryModel.getName());
        holder.binding.quantityTextView.setText(""+grocerySummaryModel.getQuantity());
        holder.binding.priceTextView.setText("TK "+grocerySummaryModel.getCost());
        holder.binding.unitTextView.setText(grocerySummaryModel.getUnit());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        GrocerySummeryBinding binding;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding= GrocerySummeryBinding.bind(itemView);
        }
    }
}
