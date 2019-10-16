package com.example.square.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.square.api.SquareRepository
import com.example.square.common.BaseViewModel
import com.example.square.common.ViewModelError
import com.example.square.ui.SquareModel
import com.example.square.ui.convert
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class SquareRepositoriesViewModel @Inject constructor(
    private val squareRepository: SquareRepository
) : BaseViewModel() {
    // Observe data changes on server
    val squareLiveData: MutableLiveData<List<SquareModel>> = MutableLiveData()
    // Observe errors
    val errorLiveData: MutableLiveData<ViewModelError> = MutableLiveData()
    // Observe view model state
    val viewModelStateLiveData: MutableLiveData<State> = MutableLiveData()

    private var squareRepositoryDisposable: Disposable? = null

    // Load all data from server
    fun loadData() {
        viewModelStateLiveData.postValue(State.DataLoadingState(true))
        squareRepositoryDisposable = squareRepository.getSquareService()
            .getAllData()
            .map { apiModels -> apiModels.map { it.convert() } }
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                viewModelStateLiveData.postValue(State.DataLoadingState(false))
            }
            .subscribeBy(
                onSuccess = { squareLiveData.postValue(it) },
                onError = { error ->
                    Timber.e(error)
                    errorLiveData.postValue(object : ViewModelError {
                        override val error: Throwable = error
                    })
                }
            )

        squareRepositoryDisposable?.addTo(clearDisposable)
    }

    fun refresh() {
        if (canRefresh()) {
            loadData()
        }
    }

    private fun canRefresh(): Boolean =
        squareRepositoryDisposable == null || squareRepositoryDisposable?.isDisposed == true

    sealed class State {
        class DataLoadingState(val isLoading: Boolean) : State()
    }

}