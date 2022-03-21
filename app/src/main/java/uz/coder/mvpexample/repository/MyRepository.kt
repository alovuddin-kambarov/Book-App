package uz.coder.mvpexample.repository

import uz.coder.mvpexample.retrofit.api.ApiClient
import uz.coder.mvpexample.retrofit.api.ApiService

class MyRepository(var apiService: ApiService) {

    suspend fun getBooks(s:String) = apiService.getBooks(s, ApiClient.apiKey)
    suspend fun getCategoryName() = apiService.getCategoryName(ApiClient.apiKey)

}