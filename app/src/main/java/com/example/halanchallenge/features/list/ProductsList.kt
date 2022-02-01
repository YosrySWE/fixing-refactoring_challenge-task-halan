package com.example.halanchallenge.features.list

import android.os.Parcelable
import android.os.Parcel

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