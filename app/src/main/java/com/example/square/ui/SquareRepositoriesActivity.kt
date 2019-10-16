package com.example.square.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.square.R
import com.example.square.ui.viewmodel.SquareRepositoriesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import com.example.square.ui.viewmodel.SquareRepositoriesViewModel.State.DataLoadingState

class SquareRepositoriesActivity : AppCompatActivity() {

    @Inject
    lateinit var squareRepositoriesViewModel: SquareRepositoriesViewModel

    private lateinit var adapter: SquareRepositoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initRecyclerView()
        initViewModel()

        swipeToRefreshLayout.setOnRefreshListener {
            squareRepositoriesViewModel.refresh()
        }

        squareRepositoriesViewModel.squareLiveData.observe(this, Observer {
            adapter.setData(it)
        })

        squareRepositoriesViewModel.errorLiveData.observe(this, Observer {
            showSnackBar(getString(R.string.snack_bar_something_happen))
        })

        squareRepositoriesViewModel.viewModelStateLiveData.observe(this, Observer { action ->
            when (action) {
                is DataLoadingState -> {
                    swipeToRefreshLayout.isRefreshing = false
                }
            }
        })

        squareRepositoriesViewModel.loadData()
    }

    @Suppress("UNCHECKED_CAST")
    private fun initViewModel() {
        squareRepositoriesViewModel =
            ViewModelProviders.of(this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return squareRepositoriesViewModel as T
                }
            }).get(SquareRepositoriesViewModel::class.java)
    }

    private fun initRecyclerView() {
        adapter = SquareRepositoriesAdapter()
        recyclerLayout.adapter = adapter
        recyclerLayout.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    private fun showSnackBar(text: CharSequence) {
        Snackbar.make(recyclerLayout, text, Snackbar.LENGTH_LONG).show()
    }
}
