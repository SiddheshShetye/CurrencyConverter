package com.siddroid.currencyconverter.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.siddroid.currencyconverter.data.model.ConvertResponseModel
import com.siddroid.currencyconverter.domain.CurrencyConversionUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val currencyUseCase: CurrencyConversionUseCase = mock()
    private val currencyViewModel by lazy { CurrencyViewModel(currencyUseCase) }

    @Test
    fun testValidInput() {
        runTest {
            whenever(currencyUseCase.invoke(anyInt(), anyString(), anyString())).thenReturn(
                ConvertResponseModel(null, null, null, null, null, null, true)
            )
            currencyViewModel.convert("5", "USD", "INR")
            verify(currencyUseCase, times(1)).invoke(anyInt(), anyString(), anyString())
        }
    }

    @Test
    fun testInvalidInput() {
        runTest {
            whenever(currencyUseCase.invoke(anyInt(), anyString(), anyString())).thenReturn(
                ConvertResponseModel(null, null, null, null, null, null, true)
            )
            currencyViewModel.convert("0", "", "")
            verify(currencyUseCase, times(0)).invoke(anyInt(), anyString(), anyString())
        }
    }
}