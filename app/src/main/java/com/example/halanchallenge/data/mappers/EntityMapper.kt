package com.example.halanchallenge.data.mappers

import com.example.halanchallenge.data.source.local.entities.ProductEntity
import com.example.halanchallenge.data.source.local.entities.ProfileEntity
import com.example.halanchallenge.domain.repository.remote.models.Product
import com.example.halanchallenge.domain.repository.remote.models.Profile

fun Product.toEntity() = ProductEntity(
    this.id, this.name_ar, this.deal_description,
    this.brand, this.image, this.name_en, this.price, this.images
)

fun ProductEntity.toDataTransferObject() = Product(
    this.id, this.name_ar, this.deal_description,
    this.brand, this.image, this.name_en, this.price, this.images
)

fun Profile.toEntity() = ProfileEntity(
    this.username!!, this.image, this.name, this.phone, this.email
)

fun ProfileEntity.toDataTransferObject() = Profile(
    this.username, this.image, this.name, this.phone, this.email
)