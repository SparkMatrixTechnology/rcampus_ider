package com.example.campusrider.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.campusrider.Activity.OrderDetailsActivity;
import com.example.campusrider.Model.OrderModel;
import com.example.campusrider.R;
import com.example.campusrider.databinding.FoodOrderBinding;

import java.util.ArrayList;

public class FoodOrderAdapter extends RecyclerView.Adapter<FoodOrderAdapter.ViewHolder>{
    Context context;
    ArrayList<OrderModel> list;

    public FoodOrderAdapter(Context context, ArrayList<OrderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.food_order,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel foodOrderModel=list.get(position);
        Glide.with(context).load(foodOrderModel.getShop_image()).into(holder.binding.shopImg);
        holder.binding.shopName.setText(foodOrderModel.getShop_name());
        holder.binding.date.setText(foodOrderModel.getOrder_date());
        holder.binding.address.setText(foodOrderModel.getAddress());
        holder.binding.orderId.setText(""+foodOrderModel.getId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OrderDetailsActivity.class);
                String type="food";
                intent.putExtra("type",type);
                intent.putExtra("id",foodOrderModel.getId());
                intent.putExtra("address",foodOrderModel.getAddress());
                intent.putExtra("shop_name",foodOrderModel.getShop_name());
                intent.putExtra("shop_address",foodOrderModel.getShop_address());
                intent.putExtra("delivery",foodOrderModel.getDelivery_fee());
                intent.putExtra("payment",foodOrderModel.getPayment_type());
                intent.putExtra("cost",foodOrderModel.getCost());
                intent.putExtra("total_price",foodOrderModel.getTotal_price());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        FoodOrderBinding binding;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding=FoodOrderBinding.bind(itemView);
        }
    }
}
