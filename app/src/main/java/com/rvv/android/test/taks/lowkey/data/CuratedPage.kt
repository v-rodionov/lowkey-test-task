package com.rvv.android.test.taks.lowkey.data

import com.google.gson.annotations.SerializedName

data class CuratedPage(
    @SerializedName("photos") val photos: List<Photo>,
    @SerializedName("next_page") val nextPage: String?,
)

data class Photo(
    @SerializedName("id") val id: String,
    @SerializedName("height") val height: Int,
    @SerializedName("width") val width: Int,
    @SerializedName("alt") val alt: String,
    @SerializedName("avg_color") val avgColor: String,
    @SerializedName("liked") val liked: Boolean,
    @SerializedName("photographer") val photographer: String,
    @SerializedName("photographer_id") val photographerId: Int,
    @SerializedName("photographer_url") val photographerUrl: String,
    @SerializedName("src") val src: Src,
    @SerializedName("url") val url: String,
)

data class Src(
    @SerializedName("landscape") val landscape: String,
    @SerializedName("large") val large: String,
    @SerializedName("large2x") val large2x: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("original") val original: String,
    @SerializedName("portrait") val portrait: String,
    @SerializedName("small") val small: String,
    @SerializedName("tiny") val tiny: String,
)
