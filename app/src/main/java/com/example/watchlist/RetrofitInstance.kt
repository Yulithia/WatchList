package com.example.watchlist
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val api: MovieApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            //.client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(MovieApi::class.java)
    }

}