package com.example.campusrider.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.campusrider.R;
import com.example.campusrider.Session.SharedPrefManager;
import com.example.campusrider.databinding.ActivityHome2Binding;

public class Home2Activity extends AppCompatActivity {

    String type;
    TextView accepted,complete,picked,cancelled;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        sharedPrefManager=new SharedPrefManager(getApplicationContext());

        int id=sharedPrefManager.getUser().getRider_id();
        type=getIntent().getStringExtra("type_name");

        accepted=findViewById(R.id.accepted);
        complete=findViewById(R.id.complete);
        picked=findViewById(R.id.picked);
        cancelled=findViewById(R.id.cancelled);


        accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status="Accepted";
                Intent intent=new Intent(Home2Activity.this, OrderActivity.class);
                intent.putExtra("rider_id",id);
                intent.putExtra("type_name",type);
                intent.putExtra("status",4);
                startActivity(intent);
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status="Delivered";

                Intent intent=new Intent(Home2Activity.this, OrderActivity.class);
                intent.putExtra("rider_id",id);
                intent.putExtra("type_name",type);
                intent.putExtra("status",3);
                startActivity(intent);
            }
        });
        picked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status="Picked Up";
                Intent intent=new Intent(Home2Activity.this, OrderActivity.class);
                intent.putExtra("rider_id",id);
                intent.putExtra("type_name",type);
                intent.putExtra("status",2);
                startActivity(intent);
            }
        });
        cancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status="Cancelled";
                Intent intent=new Intent(Home2Activity.this, OrderActivity.class);
                intent.putExtra("rider_id",id);
                intent.putExtra("type_name",type);
                intent.putExtra("status",5);
                startActivity(intent);
            }
        });

        getSupportActionBar().setTitle(type);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}