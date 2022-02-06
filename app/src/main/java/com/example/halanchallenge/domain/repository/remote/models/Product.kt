package com.example.halanchallenge.domain.repository.remote.models

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep

@Keep
data class Product(
    var id: Int,
    var name_ar: String?,
    var deal_description: String?,
    var brand: String?,
    var image: String?,
    var name_en: String?,
    var price: Int,
    var images: List<String>?
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.createStringArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name_ar)
        parcel.writeString(deal_description)
        parcel.writeString(brand)
        parcel.writeString(image)
        parcel.writeString(name_en)
        parcel.writeInt(price)
        parcel.writeStringList(images)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }

}
