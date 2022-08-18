package com.example.lawbeats_retrofit_repo

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object APIClient {
    private lateinit var retrofit: Retrofit
    private lateinit var apiInterface: APIInterface
    private fun getClient(): Retrofit {
        if (this::retrofit.isInitialized.not()) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
            retrofit = Retrofit.Builder()
                .baseUrl("https://lawgical.php-dev.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        return retrofit
    }
    fun getApiInterface():APIInterface{
        if (this::apiInterface.isInitialized.not()){
            apiInterface = getClient().create(APIInterface::class.java)
        }
        return apiInterface
    }
}