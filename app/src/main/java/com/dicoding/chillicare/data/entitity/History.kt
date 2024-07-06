package com.dicoding.chillicare.data.entitity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class History (
    var id: Int = 0,
    var disease: String = "",
    var descDisease: String = " ",
    var photoLeaf: String= "",
    var date: String = ""
): Parcelable