package com.example.postviewersapp.api

import com.example.postviewersapp.models.PostData
import retrofit2.http.GET

interface PostsApi{
    @GET("posts")
    suspend fun fetchPosts():List<PostData>
}