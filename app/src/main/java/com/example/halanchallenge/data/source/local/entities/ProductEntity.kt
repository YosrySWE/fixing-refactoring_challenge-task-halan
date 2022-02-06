package com.example.halanchallenge.data.source.local.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey
    @NonNull
    var id: Int,
    var name_ar: String?,
    var deal_description: String?,
    var brand: String?,
    var image: String?,
    var name_en: String?,
    var price: Int,
    var images: List<String>?
)

