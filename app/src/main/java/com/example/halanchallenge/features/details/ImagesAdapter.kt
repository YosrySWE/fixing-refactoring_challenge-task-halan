package com.example.halanchallenge.features.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.halanchallenge.R

class ImagesAdapter internal constructor(context: Context, data: List<String?>?) :
    RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {
    private val mData: List<String?>? = data
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

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
        var productImageView: ImageView = itemView.findViewById(R.id.product_image_IV)
    }

}