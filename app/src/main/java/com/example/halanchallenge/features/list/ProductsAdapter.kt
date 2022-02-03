package com.example.halanchallenge.features.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.halanchallenge.R
import com.example.halanchallenge.databinding.ProductItemBinding
import com.example.halanchallenge.domain.models.Product

class ProductsAdapter internal constructor(
    val callback: ItemClickListener? = null
) : ListAdapter<Product, ProductsAdapter.ViewHolder>(ProductsCallback()) {

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(var binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            binding.productItemTitleTv.text = item.name_ar
            Glide.with(binding.productIv.context).load(item.image).placeholder(R.drawable.ic_logo)
                .into(binding.productIv)
            binding.content.setOnClickListener {
                callback?.onItemClick(item)
            }
        }
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(item: Product)
    }

    internal class ProductsCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

}