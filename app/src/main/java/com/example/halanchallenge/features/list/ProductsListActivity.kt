package com.example.halanchallenge.features.list

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Glide
import com.example.halanchallenge.features.list.ProductsAdapter.ItemClickListener
import com.example.halanchallenge.R
import com.example.halanchallenge.features.login.LoginResponse
import com.google.gson.Gson
import org.json.JSONObject

class ProductsListActivity : AppCompatActivity() {
    private var response: String = ""
    lateinit var userName: TextView
    lateinit var phoneNumber: TextView
    lateinit var email: TextView
    lateinit var productsListRV: RecyclerView
    lateinit var userIV: ImageView
    lateinit var logoutIV: ImageView
    private var loginResponse: LoginResponse = LoginResponse()
    var productsList: ProductsList = ProductsList()
    lateinit var productsListAdapter: ProductsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_list)
        val bundle = intent.extras
        if (bundle != null) {
            response = bundle.getString("RESPONSE").toString()
        }
        val gson = Gson()
        loginResponse = gson.fromJson(response, LoginResponse::class.java)?: LoginResponse()
        userName = findViewById(R.id.username_tv)
        phoneNumber = findViewById(R.id.phone_number_tv)
        email = findViewById(R.id.email_tv)
        userIV = findViewById(R.id.user_iv)
        logoutIV = findViewById(R.id.logoutIV)
        if (!loginResponse.profile.image.isNullOrEmpty()) Glide.with(this)
            .load(loginResponse.profile.image).into(userIV)
        productsListRV = findViewById(R.id.products_list_rv)
        productsListAdapter = ProductsAdapter(baseContext, productsList.products)

        productsListRV.adapter = productsListAdapter


        logoutIV.setOnClickListener { finish() }
        val mLayoutManager = LinearLayoutManager(applicationContext)
        productsListRV.layoutManager = mLayoutManager
        AndroidNetworking.initialize(applicationContext)
        AndroidNetworking.get("https://assessment-sn12.halan.io/products")
            .addHeaders("Authorization", "Bearer " + loginResponse.token)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    productsList = gson.fromJson(response.toString(), ProductsList::class.java)
                    productsListAdapter.add(productsList)
                    productsListAdapter.setClickListener(itemClickListener = object :
                        ItemClickListener {
                        override fun onItemClick(view: View?, position: Int) {
                            /*val myBundle = Bundle()
                            myBundle.putParcelable("ITEM", productsListAdapter.getItem(position))
                            val myIntent = Intent(
                                this@ProductsListActivity,
                                ProductDetailsActivity::class.java
                            ).putExtra(
                                "PARCELABLE",
                                myBundle
                            )
                            myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(myIntent)*/
                        }
                    })
                }

                override fun onError(error: ANError) {
                    Log.e("FastError", error.message!!)
                }
            })
        userName.text = loginResponse.profile.name
        phoneNumber.text = loginResponse.profile.phone
        email.text = loginResponse.profile.email
    }
}