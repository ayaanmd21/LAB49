package com.faizan.lab49.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tiles(
    val id: Int,
    val name: String
) : Parcelable