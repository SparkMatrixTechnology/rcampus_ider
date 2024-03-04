package com.example.campusrider.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.campusrider.Adapter.OrderDetailsAdapter;
import com.example.campusrider.Model.OrderDetailsModel;
import com.example.campusrider.Model.OrderModel;
import com.example.campusrider.R;
import com.example.campusrider.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderDetailsActivity extends AppCompatActivity {

    String type;
    int order_id,total_price,delivery_fee,subtotal;
    String address,payment,shop_name,shop_address;
    RecyclerView rec;
    TextView orderText,shopNameText,shopAddressText,addressText,subtotalText,deliveryText,billText,paymentText;
    ArrayList<OrderDetailsModel> orderDetailsModels;
    OrderDetailsAdapter orderDetailsAdapter;

    TextView pickedbtn,deliveredbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type=getIntent().getStringExtra("type");

        if(type.equals("food")){
        setContentView(R.layout.activity_order_details);

        orderText=findViewById(R.id.order_text);
        shopNameText=findViewById(R.id.store);
        shopAddressText=findViewById(R.id.storeaddress);
        addressText=findViewById(R.id.toaddress);
        rec=findViewById(R.id.order_rec);
        subtotalText=findViewById(R.id.subtotal);
        deliveryText=findViewById(R.id.delivery);
        billText=findViewById(R.id.totalbill_text);
        paymentText=findViewById(R.id.payment_type);
        pickedbtn=findViewById(R.id.picked_btn);
        deliveredbtn=findViewById(R.id.delivered_btn);

        order_id=getIntent().getIntExtra("id",0);
        total_price=getIntent().getIntExtra("total_price",0);
        delivery_fee=getIntent().getIntExtra("delivery",0);
        subtotal=getIntent().getIntExtra("cost",0);;
        address=getIntent().getStringExtra("address");
        payment=getIntent().getStringExtra("payment");
        shop_name=getIntent().getStringExtra("shop_name");
        shop_address=getIntent().getStringExtra("shop_address");
        orderText.setText("#"+order_id);
        shopNameText.setText(shop_name);
        shopAddressText.setText(shop_address);
        addressText.setText(address);
        subtotalText.setText("TK "+subtotal);
        deliveryText.setText("TK "+delivery_fee);
        billText.setText("TK "+total_price);
        paymentText.setText(payment);

        pickedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status="Picked";
                changefoodStatus(order_id,status);
            }
        });
        deliveredbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status="Delivered";
                changefoodStatus(order_id,status);
            }
        });



        orderDetailsModels=new ArrayList<>();
        orderDetailsAdapter=new OrderDetailsAdapter(getApplicationContext(),orderDetailsModels);
        rec.setAdapter(orderDetailsAdapter);
        getFoodDetails(order_id);
        rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
        rec.setHasFixedSize(true);
        rec.setNestedScrollingEnabled(false);

        }
        else if(type.equals("grocery")){

            setContentView(R.layout.grocery_order_details);

            orderText=findViewById(R.id.order_text);
            addressText=findViewById(R.id.address);
            rec=findViewById(R.id.order_rec);
            subtotalText=findViewById(R.id.subtotal);
            deliveryText=findViewById(R.id.delivery);
            billText=findViewById(R.id.totalbill_text);
            paymentText=findViewById(R.id.payment_type);

            order_id=getIntent().getIntExtra("id",0);
            total_price=getIntent().getIntExtra("total_price",0);
            delivery_fee=getIntent().getIntExtra("delivery",0);
            subtotal=getIntent().getIntExtra("cost",0);;
            address=getIntent().getStringExtra("address");
            payment=getIntent().getStringExtra("payment");
            pickedbtn=findViewById(R.id.picked_btn);
            deliveredbtn=findViewById(R.id.delivered_btn);

            orderText.setText("#"+order_id);
            addressText.setText(address);
            subtotalText.setText("TK "+subtotal);
            deliveryText.setText("TK "+delivery_fee);
            billText.setText("TK "+total_price);
            paymentText.setText(payment);

            pickedbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String status="Picked";
                    changegroceryStatus(order_id,status);
                }
            });
            deliveredbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String status="Delivered";
                    changegroceryStatus(order_id,status);
                }
            });

            orderDetailsModels=new ArrayList<>();
            orderDetailsAdapter=new OrderDetailsAdapter(getApplicationContext(),orderDetailsModels);
            rec.setAdapter(orderDetailsAdapter);
            getGroceryDetails(order_id);
            rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
            rec.setHasFixedSize(true);
            rec.setNestedScrollingEnabled(false);
            getSupportActionBar().setTitle("Placed Orders");

        }


        getSupportActionBar().setTitle("Order Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void changefoodStatus(int order_id,String status){
        RequestQueue queue= Volley.newRequestQueue(OrderDetailsActivity.this);
        StringRequest request=new StringRequest(Request.Method.POST, Constant.UPDATE_FOOD_ORDER_URL+order_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject mainObj = new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        Toast.makeText(OrderDetailsActivity.this,"Updated",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OrderDetailsActivity.this,Home2Activity.class));
                    }
                    else {
                        Toast.makeText(OrderDetailsActivity.this,"failed",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OrderDetailsActivity.this,Home2Activity.class));
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
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("order_status",status);

                return params;
            }
        };
        queue.add(request);
    }
    public void changegroceryStatus(int order_id,String status){
        RequestQueue queue= Volley.newRequestQueue(OrderDetailsActivity.this);
        StringRequest request=new StringRequest(Request.Method.POST, Constant.UPDATE_GROCERY_ORDER_URL+order_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject mainObj = new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        Toast.makeText(OrderDetailsActivity.this,"Updated",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OrderDetailsActivity.this,Home2Activity.class));
                    }
                    else {
                        Toast.makeText(OrderDetailsActivity.this,"failed",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OrderDetailsActivity.this,Home2Activity.class));
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
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("order_status",status);

                return params;
            }
        };
        queue.add(request);
    }

    public void getGroceryDetails(int orderId) {
        RequestQueue queue= Volley.newRequestQueue(OrderDetailsActivity.this);
        StringRequest request=new StringRequest(Request.Method.GET, Constant.GET_GROCERY_ORDER_DETAILS_URL+orderId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject mainObj = new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray order_array=mainObj.getJSONArray("Order_details");
                        for(int i=0;i<order_array.length();i++){
                            JSONObject object=order_array.getJSONObject(i);
                            String order_status=object.getString("order_status");
                            OrderDetailsModel orderDetails=new OrderDetailsModel(
                                    object.getInt("id"),
                                    object.getInt("order_id"),
                                    object.getInt("product_id"),
                                    object.getInt("quantity"),
                                    object.getInt("price"),
                                    object.getString("order_date"),
                                    object.getString("name")
                            );
                            orderDetailsModels.add(orderDetails);
                            if(order_status.equals("Accepted")){
                                pickedbtn.setVisibility(View.VISIBLE);
                            }
                            else if(order_status.equals("Picked")){
                                deliveredbtn.setVisibility(View.VISIBLE);
                            }
                        }
                        orderDetailsAdapter.notifyDataSetChanged();
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

    public void getFoodDetails(int orderId) {
        RequestQueue queue= Volley.newRequestQueue(OrderDetailsActivity.this);
        StringRequest request=new StringRequest(Request.Method.GET, Constant.GET_FOOD_ORDER_DETAILS_URL+orderId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject mainObj = new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray order_array=mainObj.getJSONArray("Order_details");
                        for(int i=0;i<order_array.length();i++){
                            JSONObject object=order_array.getJSONObject(i);
                            String order_status=object.getString("order_status");
                            OrderDetailsModel orderDetails=new OrderDetailsModel(
                                    object.getInt("id"),
                                    object.getInt("order_id"),
                                    object.getInt("product_id"),
                                    object.getInt("quantity"),
                                    object.getInt("vendor_id"),
                                    object.getInt("price"),
                                    object.getString("order_date"),
                                    object.getString("product_name")
                            );
                            orderDetailsModels.add(orderDetails);
                            if(order_status.equals("Accepted")){
                                pickedbtn.setVisibility(View.VISIBLE);
                            }
                            else if(order_status.equals("Picked")){
                                deliveredbtn.setVisibility(View.VISIBLE);
                            }

                        }
                        orderDetailsAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}