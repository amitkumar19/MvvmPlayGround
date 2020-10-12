package com.example.mvvmplayground.data.api

class ApiHelper(private val apiService: ApiService) {
    fun getUsers() = apiService.getUsers()
}