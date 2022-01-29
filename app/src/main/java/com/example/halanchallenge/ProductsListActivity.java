package com.example.halanchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONObject;

public class ProductsListActivity extends AppCompatActivity {

    String response;

    TextView userName, phoneNumber, email;
    RecyclerView productsListRV;
    ImageView userIV,logoutIV;

    LoginResponse loginResponse;
    ProductsList productsList;

    ProductsAdapter productsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            response = bundle.getString("RESPONSE");
        }

        Gson gson = new Gson();
        loginResponse = gson.fromJson(response, LoginResponse.class);

        userName = findViewById(R.id.username_tv);
        phoneNumber = findViewById(R.id.phone_number_tv);
        email = findViewById(R.id.email_tv);
        userIV= findViewById(R.id.user_iv);
        logoutIV = findViewById(R.id.logoutIV);

        Glide.with(this).load(loginResponse.profile.image).into(userIV);

        productsListRV = findViewById(R.id.products_list_rv);

        logoutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        productsListRV.setLayoutManager(mLayoutManager);

        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get("https://assessment-sn12.halan.io/products")
                .addHeaders("Authorization", "Bearer " + loginResponse.token)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {
                        productsList = gson.fromJson(String.valueOf(response), ProductsList.class);
                        productsListAdapter = new ProductsAdapter(getBaseContext(), productsList.products);
                        productsListAdapter.notifyDataSetChanged();
                        productsListRV.setAdapter(productsListAdapter);
                        productsListAdapter.setClickListener(new ProductsAdapter.ItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }
                        });

                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("FastError", error.getMessage());
                    }
                });

        userName.setText(loginResponse.profile.name);
        phoneNumber.setText(loginResponse.profile.phone);
        email.setText(loginResponse.profile.email);


    }


}