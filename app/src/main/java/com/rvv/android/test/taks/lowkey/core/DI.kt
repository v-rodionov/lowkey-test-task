package com.rvv.android.test.taks.lowkey.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rvv.android.test.taks.lowkey.data.ContentRepository
import com.rvv.android.test.taks.lowkey.data.PexelsApi
import com.rvv.android.test.taks.lowkey.ui.list.ListOfPhotosViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DI {
    private const val CONNECTION_TIMEOUT_MS = 30_000L
    private const val READ_WRITE_TIMEOUT_MS = 30_000L

    private const val API_KEY = "8LQ65eo2QhMFrHyPZYWbyeukHlnUjjPnSB04RODQKnl6DWMRicaLOScp"
    private const val HEADER_KEY_AUTHORIZATION = "Authorization"

    val api: PexelsApi = createPexelsApi()
    val contentRepository = ContentRepository(api)

    private fun createPexelsApi(): PexelsApi {
        return Retrofit.Builder()
            .baseUrl("https://api.pexels.com/")
            .client(createHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PexelsApi::class.java)
    }

    private fun createHttpClient() = OkHttpClient.Builder()
        .addNetworkInterceptor(Interceptor { chain -> chain.proceed(chain.request().signedWithAccessTokenRequest()) })
        .addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .connectTimeout(CONNECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS)
        .readTimeout(READ_WRITE_TIMEOUT_MS, TimeUnit.MILLISECONDS)
        .writeTimeout(READ_WRITE_TIMEOUT_MS, TimeUnit.MILLISECONDS)
        .build()

    private fun Request.signedWithAccessTokenRequest(): Request {
        return newBuilder()
            .header(HEADER_KEY_AUTHORIZATION, API_KEY)
            .build()
    }

    object UiScope {

        val listOfPhotosViewModelFactory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ListOfPhotosViewModel(
                    contentRepository = contentRepository
                ) as T
            }
        }
    }
}