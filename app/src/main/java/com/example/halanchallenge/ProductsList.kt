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
import com.example.halanchallenge.LoginResponse
import com.example.halanchallenge.ProductsList
import com.example.halanchallenge.ProductsAdapter
import com.google.gson.Gson
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.error.ANError

class ProductsList {
    var status: String = ""
    var products: MutableList<Product> = mutableListOf()
}

class Product protected constructor(`in`: Parcel) : Parcelable {
    var id: Int
    var name_ar: String?
    var deal_description: String?
    var brand: String?
    var image: String?
    var name_en: String?
    var price: Int
    var images: List<String>?
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name_ar)
        dest.writeString(deal_description)
        dest.writeString(brand)
        dest.writeString(image)
        dest.writeString(name_en)
        dest.writeInt(price)
        dest.writeStringList(images)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Product?> = object : Parcelable.Creator<Product?> {
            override fun createFromParcel(`in`: Parcel): Product? {
                return Product(`in`)
            }

            override fun newArray(size: Int): Array<Product?> {
                return arrayOfNulls(size)
            }
        }
    }

    init {
        id = `in`.readInt()
        name_ar = `in`.readString()
        deal_description = `in`.readString()
        brand = `in`.readString()
        image = `in`.readString()
        name_en = `in`.readString()
        price = `in`.readInt()
        images = `in`.createStringArrayList()
    }
}