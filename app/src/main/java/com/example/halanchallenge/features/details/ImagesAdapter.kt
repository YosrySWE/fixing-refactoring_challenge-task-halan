package com.example.halanchallenge.features.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.halanchallenge.databinding.ImageViewItemBinding

class ImagesAdapter :
    ListAdapter<String, ImagesAdapter.ViewHolder>(ImagesCallback()) {

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ImageViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder internal constructor(private val binding: ImageViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            val circularProgressDrawable = CircularProgressDrawable(binding.productImageIV.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Glide.with(binding.productImageIV.context).load(item).placeholder(circularProgressDrawable).into(binding.productImageIV)

        }
    }

    internal class ImagesCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }


    }

}