package com.example.campusrider.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.campusrider.Adapter.FoodOrderAdapter;
import com.example.campusrider.Adapter.GroceryOrderAdapter;
import com.example.campusrider.Model.OrderModel;
import com.example.campusrider.R;
import com.example.campusrider.Session.SharedPrefManager;
import com.example.campusrider.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    String type;
    int status;
    TextView text;
    RecyclerView rec;
    ArrayList<OrderModel> orderModelList;
    FoodOrderAdapter foodOrderAdapter;
    GroceryOrderAdapter groceryOrderAdapter;

    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        rec=findViewById(R.id.order_rec);
        text=findViewById(R.id.messageText);
        type=getIntent().getStringExtra("type_name");
        sharedPrefManager=new SharedPrefManager(getApplicationContext());
        int id=sharedPrefManager.getUser().getRider_id();
        status=getIntent().getIntExtra("status",0);



        if(type.equals("Food Delivery")){
            getStatus(status);
            orderModelList=new ArrayList<>();
            foodOrderAdapter=new FoodOrderAdapter(getApplicationContext(),orderModelList);
            rec.setAdapter(foodOrderAdapter);
            getFoodOrderList(id,status);
            rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
            rec.setHasFixedSize(true);
            rec.setNestedScrollingEnabled(false);

        }
        if(type.equals("Grocery Delivery")){

            orderModelList=new ArrayList<>();
            groceryOrderAdapter=new GroceryOrderAdapter(getApplicationContext(),orderModelList);
            rec.setAdapter(groceryOrderAdapter);
            getGroceryOrderList(id,status);
            rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
            rec.setHasFixedSize(true);
            rec.setNestedScrollingEnabled(false);
            getSupportActionBar().setTitle("Placed Orders");

        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void getStatus(int status){
        RequestQueue queue= Volley.newRequestQueue(OrderActivity.this);
        StringRequest request=new StringRequest(Request.Method.GET, Constant.GET_ORDER_STATUS_URL+status, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject mainObj = new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray order_array=mainObj.getJSONArray("Status");
                        for(int i=0;i<order_array.length();i++){
                            JSONObject object=order_array.getJSONObject(i);
                            String name=object.getString("status_name");
                            getSupportActionBar().setTitle(name+ " Orders");

                        }

                    }

                    else {

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

    public void getFoodOrderList(int id, int status) {
        RequestQueue queue= Volley.newRequestQueue(OrderActivity.this);
        StringRequest request=new StringRequest(Request.Method.GET, "http://www.campusriderbd.com/Customer/rider/orders.php?type=food&rider_id=" +id +"&order_status=" +status, new Response.Listener<String>() {
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
                            orderModelList.add(orderModel);
                        }
                        foodOrderAdapter.notifyDataSetChanged();
                    }
                    else if(mainObj.getString("status").equals("failed")) {
                        text.setVisibility(View.VISIBLE);
                    }
                    else {

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

    public void getGroceryOrderList(int id, int status) {
        RequestQueue queue= Volley.newRequestQueue(OrderActivity.this);
        StringRequest request=new StringRequest(Request.Method.GET, "http://www.campusriderbd.com/Customer/rider/orders.php?type=grocery&rider_id=" +id +"&order_status=1", new Response.Listener<String>() {
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
                                    object.getString("address"),
                                    object.getInt("cost"),
                                    object.getInt("delivery_fee"),
                                    object.getInt("total_bill"),
                                    object.getString("payment_type"),
                                    object.getString("payment_status"),
                                    object.getString("order_status"),
                                    object.getString("order_date"),
                                    object.getInt("rider_id"),
                                    object.getString("customer_name"),
                                    object.getString("customer_phone")
                            );
                            orderModelList.add(orderModel);
                        }
                        groceryOrderAdapter.notifyDataSetChanged();
                    }
                    else if(mainObj.getString("status").equals("failed")) {
                        text.setVisibility(View.VISIBLE);
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


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}