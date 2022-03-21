package uz.coder.mvpexample.retrofit.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://api.nytimes.com/svc/books/"
    const val apiKey = "jY85c8s8Q0KV7iAEJzYA80AlQ9GJt8mG"
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build()

    }

    var apiService: ApiService = getRetrofit().create(ApiService::class.java)

}