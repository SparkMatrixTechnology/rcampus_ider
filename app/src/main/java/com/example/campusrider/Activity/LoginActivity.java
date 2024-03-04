package com.example.campusrider.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.campusrider.MainActivity;
import com.example.campusrider.Model.LoginResponse;
import com.example.campusrider.Model.User;
import com.example.campusrider.R;
import com.example.campusrider.Session.SharedPrefManager;
import com.example.campusrider.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText ePhone,ePassword;
    Button bLogin;
    TextView tRegister;

    SharedPrefManager sharedPrefManager;
    String sPhone,sPassword;

    int rider_id;
    String rider_name;
    int rider_phone;
    String rider_email;
    String area,rider_password,rider_image;
    int rider_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefManager=new SharedPrefManager(getApplicationContext());

        ePhone=findViewById(R.id.phone_edit);
        ePassword=findViewById(R.id.password_edit);
        bLogin=findViewById(R.id.login_button);
        tRegister=findViewById(R.id.Register);

        tRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginResponse loginResponse=null;

                if(ePhone.getText().toString().isEmpty()){
                    ePhone.setError("Field can't be empty");
                    return;
                }
                if(ePassword.getText().toString().isEmpty()){
                    ePassword.setError("Field can't be empty");
                    return;
                }
                else {
                    Login();
                }
            }
        });
    }
    public void Login(){
        sPhone=ePhone.getText().toString().trim();
        sPassword=ePassword.getText().toString().trim();

        RequestQueue requestQueue= Volley.newRequestQueue(LoginActivity.this);
        StringRequest request=new StringRequest(Request.Method.POST, Constant.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("err", response);
                    JSONObject jsonObject = new JSONObject(response);
                    String result=jsonObject.getString("status");
                    if (result.equals("Login Success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("user");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject user=jsonArray.getJSONObject(i);
                            rider_id=user.getInt("rider_id");
                            rider_name=user.getString("rider_name");
                            rider_phone=user.getInt("rider_phone");
                            rider_email=user.getString("rider_email");
                            area=user.getString("area");
                            rider_password=user.getString("rider_password");
                            rider_image=Constant.IMAGE_URL+user.getString("rider_image");
                            rider_status=user.getInt("rider_status");
                            sharedPrefManager.saveUser(new User(rider_id,rider_name,rider_phone,rider_email,area,rider_password,rider_image,rider_status));

                            Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                    }
                    if(result.equals("Login unsuccessful.Enter correct credentials")) {
                        Toast.makeText(LoginActivity.this,"Login Unsuccessful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,LoginActivity.class));
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
                params.put("rider_phone",sPhone);
                params.put("rider_password",sPassword);
                return params;
            }
        };
        requestQueue.add(request);

    }
}