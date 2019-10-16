package com.example.square.ui.viewmodel

import com.example.square.BaseTest
import com.example.square.api.SquareRepository
import com.example.square.api.services.SquareService
import com.example.square.ui.SquareModel
import com.nhaarman.mockitokotlin2.doReturn
import io.reactivex.Single
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*


class SquareRepositoriesViewModelTest : BaseTest() {
    @Mock
    lateinit var squareRepository: SquareRepository
    @Mock
    lateinit var squareService: SquareService

    lateinit var squareRepositoriesViewModel: SquareRepositoriesViewModel

    @Suppress("UNCHECKED_CAST")
    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }

    @Before
    fun before() {
        `when`(squareRepository.getSquareService()).thenReturn(squareService)
        `when`(squareService.getAllData()).thenReturn(Single.just(emptyList()))

        squareRepositoriesViewModel = SquareRepositoriesViewModel(squareRepository)
    }

    @Test
    fun getSquareLiveData_expectEmptyData_ifDataFromServerIsEmpty() {
        squareRepositoriesViewModel.loadData()

        verify(squareService).getAllData()

        val data: List<SquareModel> = squareRepositoriesViewModel.squareLiveData.value!!

        assertThat(data, equalTo(emptyList()))
    }

    @Test
    fun getErrorLiveData_emitViewModelError_ifServerResponseThrowException() {
        val serverError: Throwable = NullPointerException()
        `when`(squareService.getAllData()).thenReturn(Single.error(serverError))
        squareRepositoriesViewModel.loadData()

        verify(squareService).getAllData()

        assertThat(squareRepositoriesViewModel.errorLiveData.value!!.error, equalTo(serverError))
    }

    @Test
    fun getViewModelStateLiveData_emitDateLoadingState_whenRefreshIsCalling() {
        val observer: (SquareRepositoriesViewModel.State) -> Unit =
            com.nhaarman.mockitokotlin2.mock {
                on { invoke(any()) } doReturn Unit
            }
        squareRepositoriesViewModel.viewModelStateLiveData.observeForever(observer)
        squareRepositoriesViewModel.refresh()

        verify(observer, times(2)).invoke(any())
    }

}