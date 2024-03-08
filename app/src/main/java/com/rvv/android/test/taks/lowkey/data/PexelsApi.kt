package com.rvv.android.test.taks.lowkey.data

import retrofit2.http.GET
import retrofit2.http.Query

interface PexelsApi {

    @GET("v1/curated")
    suspend fun getCurated(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): CuratedPage
}
