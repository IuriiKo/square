package com.example.square.common

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    protected val clearDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        clearDisposable.clear()
    }
}