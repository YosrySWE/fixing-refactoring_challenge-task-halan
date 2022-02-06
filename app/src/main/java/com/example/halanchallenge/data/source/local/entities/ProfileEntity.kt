package com.example.halanchallenge.data.source.local.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity (
    @PrimaryKey
    @NonNull
    var username: String,
    var image: String? = null,
    var name: String? = null,
    var phone: String? = null,
    var email: String? = null)