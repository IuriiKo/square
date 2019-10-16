package com.example.square

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.BeforeClass
import org.junit.Rule
import org.mockito.junit.MockitoJUnit

/**
 * Base class for unit tests with base rules
 */
open class BaseTest {
    @get:Rule
    var mockitoRule = MockitoJUnit.rule()
    @get:Rule
    val rule = InstantTaskExecutorRule()

    companion object {

        @JvmStatic
        @BeforeClass
        fun setup() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }
    }
}