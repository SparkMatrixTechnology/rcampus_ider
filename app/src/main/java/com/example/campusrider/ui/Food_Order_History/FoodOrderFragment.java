package com.example.campusrider.ui.Food_Order_History;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.campusrider.Adapter.FoodOrderAdapter;
import com.example.campusrider.Adapter.GrocerySummaryAdapter;
import com.example.campusrider.Model.OrderModel;
import com.example.campusrider.Session.SharedPrefManager;
import com.example.campusrider.databinding.FragmentFoodOrderBinding;
import com.example.campusrider.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class FoodOrderFragment extends Fragment {

    private FragmentFoodOrderBinding binding;
    String from,to;
    SharedPrefManager sharedPrefManager;

    ArrayList<OrderModel> orderModels;
    FoodOrderAdapter foodOrderAdapter;
    int tOrder=0,tIncome=0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFoodOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedPrefManager=new SharedPrefManager(getContext());
        int id=sharedPrefManager.getUser().getRider_id();
        binding.fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogue1();

            }
        });
        binding.toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogue2();
            }
        });
        orderModels=new ArrayList<>();
        foodOrderAdapter=new FoodOrderAdapter(getActivity(),orderModels);
        binding.orderRec.setAdapter(foodOrderAdapter);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getInfo(getContext(),id);

            }
        });
        binding.orderRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        binding.orderRec.setHasFixedSize(true);
        binding.orderRec.setNestedScrollingEnabled(false);
        return root;
    }

    private void getInfo(Context context, int id) {


        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(Request.Method.GET, "http://www.campusriderbd.com/Customer/rider/report.php?type=food&rider_id=" +id +"&from=" +binding.fromDate.getText().toString() +"&to=" +binding.toDate.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject mainObj = new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray order_array=mainObj.getJSONArray("Order_list");
                        for(int i=0;i<order_array.length();i++){
                            JSONObject object=order_array.getJSONObject(i);
                            OrderModel orderModel=new OrderModel(
                                    object.getInt("id"),
                                    object.getInt("customer_id"),
                                    object.getInt("vendor_id"),
                                    object.getString("address"),
                                    object.getInt("cost"),
                                    object.getInt("delivery_fee"),
                                    object.getInt("total_price"),
                                    object.getString("comment"),
                                    object.getString("payment_type"),
                                    object.getString("payment_status"),
                                    object.getString("order_status"),
                                    object.getString("order_date"),
                                    object.getInt("rider_id"),
                                    object.getString("customer_name"),
                                    object.getString("customer_phone"),
                                    object.getString("vendor_name"),
                                    Constant.IMAGE_URL+object.getString("shop_image"),
                                    object.getString("shop_address")
                            );
                            int total_cost=object.getInt("delivery_fee");
                            tOrder++;
                            tIncome+=total_cost;
                            orderModels.add(orderModel);
                        }
                        foodOrderAdapter.notifyDataSetChanged();
                        binding.textViewOrder.setText(""+tOrder);
                        binding.textViewSell.setText("TK "+tIncome);
                    }
                    if(mainObj.getString("status").equals("failed")) {
                        binding.textViewOrder.setText(""+0);
                        binding.textViewSell.setText("TK "+0);
                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        queue.add(request);
    }


    public void openDialogue1(){

        int y= Calendar.getInstance().get(Calendar.YEAR);
        int m=Calendar.getInstance().get(Calendar.MONTH);
        int d=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                from=String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(dayOfMonth);
                binding.fromDate.setText(from);
            }
        }, y, m, d);
        dialog.show();
    }
    public void openDialogue2(){

        int y=Calendar.getInstance().get(Calendar.YEAR);
        int m=Calendar.getInstance().get(Calendar.MONTH);
        int d=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                to=String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(dayOfMonth);
                binding.toDate.setText(to);
            }
        }, y, m, d);
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}