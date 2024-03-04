package com.example.campusrider.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusrider.Model.OrderDetailsModel;
import com.example.campusrider.R;
import com.example.campusrider.databinding.FoodOrderDetailsBinding;

import java.util.ArrayList;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>{
    Context context;
    ArrayList<OrderDetailsModel> list;

    public OrderDetailsAdapter(Context context, ArrayList<OrderDetailsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.food_order_details,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetailsModel orderDetailsModel =list.get(position);
        holder.binding.quantity.setText(""+orderDetailsModel.getQuantity());
        holder.binding.itemName.setText(orderDetailsModel.getProduct_name());
        holder.binding.cost.setText("TK "+ orderDetailsModel.getPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
       FoodOrderDetailsBinding binding;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding= FoodOrderDetailsBinding.bind(itemView);
        }
    }
}
