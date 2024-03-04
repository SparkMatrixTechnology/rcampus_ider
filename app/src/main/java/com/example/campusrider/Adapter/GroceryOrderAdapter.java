package com.example.campusrider.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusrider.Activity.OrderDetailsActivity;
import com.example.campusrider.Model.OrderModel;
import com.example.campusrider.R;
import com.example.campusrider.databinding.GroceryOrderBinding;

import java.util.ArrayList;

public class GroceryOrderAdapter extends RecyclerView.Adapter<GroceryOrderAdapter.ViewHolder>{
    Context context;
    ArrayList<OrderModel> list;

    public GroceryOrderAdapter(Context context, ArrayList<OrderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_order,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OrderModel groceryOrderModel=list.get(position);
        holder.binding.orderId.setText(""+groceryOrderModel.getId());
        holder.binding.cusName.setText(groceryOrderModel.getCustomer_name());
        holder.binding.cusPhone.setText("+880"+groceryOrderModel.getCustomer_phone());
        holder.binding.fromAddress.setText(groceryOrderModel.getAddress());
        holder.binding.totalBill.setText("TK "+groceryOrderModel.getTotal_price());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OrderDetailsActivity.class);
                String type="grocery";
                intent.putExtra("type",type);
                intent.putExtra("id",groceryOrderModel.getId());
                intent.putExtra("address",groceryOrderModel.getAddress());
                intent.putExtra("payment",groceryOrderModel.getPayment_type());
                intent.putExtra("delivery",groceryOrderModel.getDelivery_fee());
                intent.putExtra("cost",groceryOrderModel.getCost());
                intent.putExtra("total_price",groceryOrderModel.getTotal_price());
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
        GroceryOrderBinding binding;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding= GroceryOrderBinding.bind(itemView);
        }
    }
}
