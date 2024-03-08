package com.rvv.android.test.taks.lowkey.data

class ContentRepository(
    private val api: PexelsApi,
) {

    suspend fun getCurated(page: Int, perPage: Int): CuratedPage {
        return api.getCurated(page, perPage)
    }
}