package com.dicoding.chillicare.data.entitity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Forum(
    val date: String,
    val description: String,
    val name: String,
    val title: String,
    val userid: String
): Parcelable
