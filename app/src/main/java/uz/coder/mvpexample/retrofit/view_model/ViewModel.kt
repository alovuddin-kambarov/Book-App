package uz.coder.mvpexample.retrofit.view_model

import android.content.Context
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import uz.coder.mvpexample.models.books.books.Book
import uz.coder.mvpexample.models.books.categories.CategoryName
import uz.coder.mvpexample.repository.MyRepository
import uz.coder.mvpexample.retrofit.resource.Resource
import uz.coder.mvpexample.retrofit.api.ApiClient
import uz.coder.mvpexample.utils.NetworkHelper

class ViewModel : ViewModel() {

    private var myRepository = MyRepository(ApiClient.apiService)
    private var liveData = MutableLiveData<Resource<Book>>()
    private var categoryLiveData = MutableLiveData<Resource<CategoryName>>()


    fun getData(context: Context, s:String): LiveData<Resource<Book>> {


        viewModelScope.launch {
            if (NetworkHelper(context).isNetworkConnected()) {
                coroutineScope {
                    supervisorScope {
                        try {
                            liveData.postValue(Resource.loading(null))
                            val video = myRepository.getBooks(s)

                            if (video.isSuccessful) {
                                liveData.postValue(Resource.success(video.body()))
                            } else {
                                liveData.postValue(
                                    Resource.error(
                                        video.raw().toString(),
                                        null
                                    )
                                )


                            }


                        } catch (e: Exception) {
                            liveData.postValue(Resource.error(e.message ?: "Error", null))
                        }
                    }
                }
            } else {
                liveData.postValue(
                    Resource.error(
                        "Internet no connection! Please, connect internet and try again!",
                        null
                    )
                )

            }

        }

        return liveData

    }

    fun getCategoryName(context: Context): LiveData<Resource<CategoryName>> {


        viewModelScope.launch {
            if (NetworkHelper(context).isNetworkConnected()) {
                coroutineScope {
                    supervisorScope {
                        try {
                            categoryLiveData.postValue(Resource.loading(null))
                            val video = myRepository.getCategoryName()

                            if (video.isSuccessful) {
                                categoryLiveData.postValue(Resource.success(video.body()))
                            } else {
                                categoryLiveData.postValue(
                                    Resource.error(
                                        video.raw().toString(),
                                        null
                                    )
                                )


                            }


                        } catch (e: Exception) {
                            categoryLiveData.postValue(Resource.error(e.message ?: "Error", null))
                        }
                    }
                }
            } else {
                categoryLiveData.postValue(
                    Resource.error(
                        "Internet no connection! Please, connect internet and try again!",
                        null
                    )
                )

            }

        }

        return categoryLiveData

    }


}

