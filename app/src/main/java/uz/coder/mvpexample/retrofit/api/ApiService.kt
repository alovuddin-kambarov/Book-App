package uz.coder.mvpexample.retrofit.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.coder.mvpexample.models.books.books.Book
import uz.coder.mvpexample.models.books.categories.CategoryName

interface ApiService {


    @GET("v3/lists/current/{name}.json")
    suspend fun getBooks(
        @Path("name") s: String,
        @Query("api-key") key: String = ApiClient.apiKey
    ): Response<Book>


    @GET("lists/names.json")
    suspend fun getCategoryName(
        @Query("api-key") key: String = ApiClient.apiKey
    ): Response<CategoryName>



}


