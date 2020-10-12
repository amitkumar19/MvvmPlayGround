package com.example.mvvmplayground.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmplayground.R
import com.example.mvvmplayground.data.api.ApiHelper
import com.example.mvvmplayground.data.api.ApiServiceImpl
import com.example.mvvmplayground.data.model.User
import com.example.mvvmplayground.ui.base.ViewModelFactory
import com.example.mvvmplayground.ui.main.adapter.MainAdapter
import com.example.mvvmplayground.ui.main.viewmodel.MainViewModel
import com.example.mvvmplayground.utils.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.getUsers().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data.let { users -> renderList(users) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
            }
        })
    }

    private fun renderList(users: List<User>?) {
        if (users != null) {
            adapter.addData(users)
        }
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }
}