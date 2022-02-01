package com.example.halanchallenge.features.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.halanchallenge.R

class ProductsListFragment : Fragment() {

    companion object {
        fun newInstance() = ProductsListFragment()
    }

    private lateinit var viewModel: ProductsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductsListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}