package com.example.campusrider.Activity;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;

import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.campusrider.R;
import com.example.campusrider.utils.Constant;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    EditText eName,ePhone,eEmail,ePassword;
    Spinner spinnerArea;
    ImageView pImage;
    Button bRegister,aImage;
    TextView tLogin;
    Bitmap bitmap;
    String base64Image;
    ArrayList<String> areaArray;
    ArrayAdapter<String> areaAdapter;
    String sName,sEmail,sPassword,sImage,sArea,sPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        eName=findViewById(R.id.name);
        ePhone=findViewById(R.id.phone);
        eEmail=findViewById(R.id.email);
        ePassword=findViewById(R.id.password);
        spinnerArea=(Spinner) findViewById(R.id.spinner_area);
        pImage=findViewById(R.id.profile_image);
        bRegister=findViewById(R.id.register);
        aImage=findViewById(R.id.add_image);
        tLogin=findViewById(R.id.login);

        tLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

        aImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                               startActivityForResult(intent,111);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();


            }
        });

        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item=parent.getItemAtPosition(position).toString();
                sArea=item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        areaArray=new ArrayList<>();
        getArea();

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eName.getText().toString().isEmpty()){
                    eName.setError("Field can't be empty");
                    return;
                }
                if(eEmail.getText().toString().isEmpty()){
                    eEmail.setError("Field can't be empty");
                    return;
                }
                if(ePhone.getText().toString().isEmpty()){
                    ePhone.setError("Field can't be empty");
                    return;
                }
                if(ePassword.getText().toString().isEmpty()){
                    ePassword.setError("Field can't be empty");
                    return;
                }
                else {
                    Register();
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==111 && resultCode==RESULT_OK){
            bitmap= (Bitmap)data.getExtras().get("data") ;
            pImage.setImageBitmap(bitmap);
        }
    }
    private String encodebitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] byteofimages=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteofimages, Base64.DEFAULT);
    }
    public void getArea(){

        RequestQueue queue= Volley.newRequestQueue(this);
        StringRequest request =new StringRequest(Request.Method.POST, Constant.GET_LOCATION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err", response);
                    JSONObject mainObj = new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray area_Array=mainObj.getJSONArray("Location");
                        for(int i=0;i<area_Array.length();i++){
                            JSONObject object=area_Array.getJSONObject(i);
                            String location=object.getString("name");
                            areaArray.add(location);
                            areaAdapter=new ArrayAdapter<>(RegistrationActivity.this, android.R.layout.simple_spinner_item,areaArray);
                            areaAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            spinnerArea.setAdapter(areaAdapter);
                        }
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
    public void Register(){

        sName=eName.getText().toString().trim();
        sEmail=eEmail.getText().toString().trim();
        sPhone=ePhone.getText().toString().trim();
        sPassword=ePassword.getText().toString().trim();
        StringRequest request=new StringRequest(Request.Method.POST, Constant.Registration_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Vendor Already Exists!")){
                    Toast.makeText(RegistrationActivity.this,response,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this,RegistrationActivity.class));
                }else{
                    Toast.makeText(RegistrationActivity.this,response,Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrationActivity.this,error.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("rider_name",sName);
                params.put("rider_phone",sPhone);
                params.put("rider_email",sEmail);
                params.put("area",sArea);
                params.put("rider_password",sPassword);
                params.put("image",encodebitmap(bitmap));
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(RegistrationActivity.this);
        requestQueue.add(request);
    }

}