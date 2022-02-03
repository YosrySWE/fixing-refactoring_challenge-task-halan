package com.example.halanchallenge.domain.models


data class Product(
    var id: Int,
    var name_ar: String?,
    var deal_description: String?,
    var brand: String?,
    var image: String?,
    var name_en: String?,
    var price: Int,
    var images: List<String>?
)
