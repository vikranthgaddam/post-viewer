package com.example.postviewersapp.models

data class PostData(
    val userId: Int,
    val id: Int,
    val title: String,
   val body:String,
    var expand:Boolean=false)