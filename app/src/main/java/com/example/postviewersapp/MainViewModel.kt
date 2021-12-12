package com.example.postviewersapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postviewersapp.api.RetrofitInstance
import com.example.postviewersapp.models.PostData
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"
class MainViewModel : ViewModel() {
    private val _userPosts:MutableLiveData<List<PostData>> = MutableLiveData()
    val userPosts:LiveData<List<PostData>>
          get()=_userPosts
    private val _totalPosts:MutableLiveData<List<PostData>> = MutableLiveData()
    val totalPosts:LiveData<List<PostData>>
           get()=_totalPosts

    fun fetchPosts(){
        viewModelScope.launch {
            val fetchedPosts = RetrofitInstance.api.fetchPosts()
            _totalPosts.value=fetchedPosts
        }
    }
    fun userSpecificPost(userId:Int){
        viewModelScope.launch {
            val fetchedPosts = RetrofitInstance.api.fetchPosts()
            _userPosts.value= fetchedPosts.filter{it.userId==userId}
        }
    }
}