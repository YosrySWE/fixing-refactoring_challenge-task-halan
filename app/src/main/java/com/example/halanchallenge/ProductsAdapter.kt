package com.example.halanchallenge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductsAdapter internal constructor(private val context: Context, data: MutableList<Product>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    private var mData: MutableList<Product> = data
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mData[position]
        holder.myTextView.text = item.name_ar
        Glide.with(holder.productImageView.context).load(item.image)
            .into(holder.productImageView)
        holder.moreButton.setOnClickListener {
            val myBundle = Bundle()
            myBundle.putParcelable("ITEM", item)
            val myIntent = Intent(context, ProductDetailsActivity::class.java).putExtra(
                "PARCELABLE",
                myBundle
            )
            myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(myIntent)
        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var myTextView: TextView
        var moreButton: Button
        var productImageView: ImageView
        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

        init {
            myTextView = itemView.findViewById(R.id.product_item_title_tv)
            moreButton = itemView.findViewById(R.id.more_btn)
            productImageView = itemView.findViewById(R.id.product_iv)
            itemView.setOnClickListener(this)
        }
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): Product {
        return mData[id]
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    fun add(list: ProductsList) {
        this.mData.clear()
        this.mData.addAll(list.products)
        notifyDataSetChanged()

    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

}