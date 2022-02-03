package com.example.halanchallenge.features.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.halanchallenge.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private val viewModel: ProductDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }
}


/*
*
*     lateinit var product: Product
    lateinit var description: TextView
    lateinit var title: TextView
    lateinit var price: TextView
    lateinit var back: Button
    lateinit var indicatorView: ARIndicatorView
    lateinit var imagesListRV: RecyclerView
    lateinit var imagesAdapter: ImagesAdapter
*
*
*         description = findViewById(R.id.product_description_tv)
        title = findViewById(R.id.product_title_tv)
        back = findViewById(R.id.materialButton)
        price = findViewById(R.id.product_price_tv)
        imagesListRV = findViewById(R.id.product_images_banner)
        indicatorView = findViewById(R.id.ar_indicator)
        val bundle = intent.getBundleExtra("PARCELABLE")
        product = (bundle!!.getParcelable<Parcelable>("ITEM") as Product?)!!
        back.setOnClickListener { finish() }
        description.text = product.deal_description
        title.text = product.name_ar
        description.movementMethod = ScrollingMovementMethod()
        price.text = "كاش           ${product.price}جنيه"
        imagesAdapter = ImagesAdapter(this, product.images)
        imagesListRV.adapter = imagesAdapter
        indicatorView.attachTo(imagesListRV, true)*/