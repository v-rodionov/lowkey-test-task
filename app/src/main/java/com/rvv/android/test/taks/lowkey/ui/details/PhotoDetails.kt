package com.rvv.android.test.taks.lowkey.ui.details

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoDetails(
    val id: String,
    val imageUrl: String,
    val authorName: String,
) : Parcelable