package com.dicoding.chillicare.data.entitity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val name: String,
    val rating: String,
    val comment: String
) : Parcelable