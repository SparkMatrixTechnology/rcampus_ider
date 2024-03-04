package com.example.campusrider.ui.Grocery_Summary;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.campusrider.Adapter.GrocerySummaryAdapter;
import com.example.campusrider.Model.GrocerySummaryModel;
import com.example.campusrider.R;
import com.example.campusrider.Session.SharedPrefManager;
import com.example.campusrider.databinding.FragmentGroceryOrderBinding;
import com.example.campusrider.databinding.FragmentGrocerySummaryBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class GrocerySummaryFragment extends Fragment {
    FragmentGrocerySummaryBinding binding;
    String from,to;
    int total=0;

    ArrayList<GrocerySummaryModel> grocerySummaryModels;
    GrocerySummaryAdapter grocerySummaryAdapter;
    SharedPrefManager sharedPrefManager;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGrocerySummaryBinding.inflate(inflater, container, false);
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
        grocerySummaryModels=new ArrayList<>();
        grocerySummaryAdapter=new GrocerySummaryAdapter(getActivity(),grocerySummaryModels);
        binding.groceryRec.setAdapter(grocerySummaryAdapter);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getInfo(getContext(),id);

            }
        });
        binding.groceryRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        binding.groceryRec.setHasFixedSize(true);
        binding.groceryRec.setNestedScrollingEnabled(false);
        return root;
    }
    private void getInfo(Context context, int id) {
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(Request.Method.GET,"http://campusriderbd.com/Customer/rider/grocer_summary.php?rider_id=" +id +"&from=" +binding.fromDate.getText().toString() +"&to=" +binding.toDate.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject mainObj = new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray order_array=mainObj.getJSONArray("Report");
                        for(int i=0;i<order_array.length();i++){
                            JSONObject object=order_array.getJSONObject(i);
                            GrocerySummaryModel summary= new GrocerySummaryModel(
                                    object.getInt("id"),
                                    object.getInt("Total_quantity"),
                                    object.getInt("total_cost"),
                                    object.getString("name"),
                                    object.getString("unit")
                            );
                            grocerySummaryModels.add(summary);
                            int  total_cost=object.getInt("total_cost");
                            total+=total_cost;
                        }
                        grocerySummaryAdapter.notifyDataSetChanged();
                        binding.textViewSell.setText("TK "+total);
                    }
                    if(mainObj.getString("status").equals("failed")) {
                        binding.message.setVisibility(View.VISIBLE);
                        binding.textViewSell.setText("TK "+0);
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
}