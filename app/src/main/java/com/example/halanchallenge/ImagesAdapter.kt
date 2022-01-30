package com.example.halanchallenge

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.halanchallenge.R
import com.bumptech.glide.Glide
import com.example.halanchallenge.LoginTask
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import com.example.halanchallenge.Login
import android.os.Bundle
import android.os.AsyncTask
import android.annotation.SuppressLint
import org.json.JSONObject
import android.content.ContentValues
import android.content.Context
import org.json.JSONException
import android.content.Intent
import com.example.halanchallenge.ProductsListActivity
import com.example.halanchallenge.Product
import android.widget.TextView
import com.arindicatorview.ARIndicatorView
import com.example.halanchallenge.ImagesAdapter
import android.os.Parcelable
import android.text.method.ScrollingMovementMethod
import com.example.halanchallenge.ProductsAdapter.ItemClickListener
import com.example.halanchallenge.ProductDetailsActivity
import android.os.Parcel
import android.view.View
import android.widget.ImageView
import com.example.halanchallenge.LoginResponse
import com.example.halanchallenge.ProductsList
import com.example.halanchallenge.ProductsAdapter
import com.google.gson.Gson
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.error.ANError

class ImagesAdapter internal constructor(context: Context, data: List<String?>?) :
    RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {
    private val mData: List<String?>?
    private val mInflater: LayoutInflater
    private val context: Context

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.image_view_item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mData!![position]
        if (item != null) {
            Glide.with(holder.productImageView.context).load(item).into(holder.productImageView)
        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return mData!!.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var productImageView: ImageView

        init {
            productImageView = itemView.findViewById(R.id.product_image_IV)
        }
    }

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        mData = data
        this.context = context
    }
}