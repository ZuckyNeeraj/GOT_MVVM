package com.example.got_app_mvvm.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class DataItem(
    val family: String?,
    val firstName: String?,
    val fullName: String?,
    val id: Int,
    val image: String?,
    val imageUrl: String?,
    val lastName: String?,
    val title: String?
): Serializable