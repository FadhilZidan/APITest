package com.example.api.data.network

import com.example.api.data.model.Post
import com.example.api.data.model.PostBaru
import com.example.api.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @POST("posts")
    suspend fun createPost(@Body post: PostBaru): Response<Post>
}
