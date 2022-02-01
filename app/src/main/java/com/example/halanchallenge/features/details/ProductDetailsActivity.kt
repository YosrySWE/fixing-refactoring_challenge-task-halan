package com.example.halanchallenge.features.details

import android.os.Bundle
import android.os.Parcelable
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.arindicatorview.ARIndicatorView
import com.example.halanchallenge.ImagesAdapter
import com.example.halanchallenge.R
import com.example.halanchallenge.features.list.Product

class ProductDetailsActivity : AppCompatActivity() {
    lateinit var product: Product
    lateinit var description: TextView
    lateinit var title: TextView
    lateinit var price: TextView
    lateinit var back: Button
    lateinit var indicatorView: ARIndicatorView
    lateinit var imagesListRV: RecyclerView
    lateinit var imagesAdapter: ImagesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        description = findViewById(R.id.product_description_tv)
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
        indicatorView.attachTo(imagesListRV, true)
    }
}