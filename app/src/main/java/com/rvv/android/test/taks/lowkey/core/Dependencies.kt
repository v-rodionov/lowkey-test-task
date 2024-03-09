package com.rvv.android.test.taks.lowkey.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rvv.android.test.taks.lowkey.BuildConfig
import com.rvv.android.test.taks.lowkey.data.ContentRepository
import com.rvv.android.test.taks.lowkey.data.PexelsApi
import com.rvv.android.test.taks.lowkey.ui.details.PhotoDetailsArgs
import com.rvv.android.test.taks.lowkey.ui.details.PhotoDetailsViewModel
import com.rvv.android.test.taks.lowkey.ui.list.ListOfPhotosViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Dependencies {
    private const val API_KEY = "8LQ65eo2QhMFrHyPZYWbyeukHlnUjjPnSB04RODQKnl6DWMRicaLOScp"
    private const val HEADER_KEY_AUTHORIZATION = "Authorization"

    object AppScope {
        private val api: PexelsApi = createPexelsApi()
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
            .addNetworkInterceptor(Interceptor { chain -> chain.proceed(chain.request().signedWithApiKeyRequest()) })
            .apply {
                if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                }
            }
            .build()

        private fun Request.signedWithApiKeyRequest(): Request {
            return newBuilder()
                .header(HEADER_KEY_AUTHORIZATION, API_KEY)
                .build()
        }
    }

    object UiScope {

        fun getListOfPhotosViewModelFactory() = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ListOfPhotosViewModel(
                    contentRepository = AppScope.contentRepository
                ) as T
            }
        }

        fun getPhotoDetailsViewModelFactory(args: PhotoDetailsArgs) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PhotoDetailsViewModel(args) as T
            }
        }
    }
}