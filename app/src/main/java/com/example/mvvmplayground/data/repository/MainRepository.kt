package com.example.mvvmplayground.data.repository

import com.example.mvvmplayground.data.api.ApiHelper
import com.example.mvvmplayground.data.model.User
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {

    fun getUsers():Single<List<User>>{
       return apiHelper.getUsers()
    }
}