package com.example.api.data.repository

import com.example.api.data.model.PostBaru
import com.example.api.data.network.ApiService

class UserRepository(private val apiService: ApiService) {
    suspend fun getUsers() = apiService.getUsers()
    suspend fun getPosts() = apiService.getPosts()
    suspend fun createPost(post: PostBaru) = apiService.createPost(post)
}
