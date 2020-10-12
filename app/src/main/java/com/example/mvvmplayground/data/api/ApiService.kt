package com.example.mvvmplayground.data.api

import com.example.mvvmplayground.data.model.User
import io.reactivex.Single

interface ApiService {

    fun getUsers():Single<List<User>>
}