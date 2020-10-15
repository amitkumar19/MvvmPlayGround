package com.example.mvvmplayground.ui.main.viewmodel

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmplayground.data.model.User
import com.example.mvvmplayground.data.repository.MainRepository
import com.example.mvvmplayground.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val users = MutableLiveData<Resource<List<User>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchUser()
    }

    private fun fetchUser() {
        users.postValue(Resource.loading(null))
        compositeDisposable.add(mainRepository.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ usersList->
                users.postValue(Resource.success(usersList))
            },{
            users.postValue(Resource.error("Something went wrong",null))
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getUsers():LiveData<Resource<List<User>>>{
        return users
    }
}