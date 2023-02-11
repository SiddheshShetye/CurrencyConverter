package com.siddroid.currencyconverter.domain

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.siddroid.currencyconverter.data.model.ConvertResponseModel
import com.siddroid.currencyconverter.data.model.Info
import com.siddroid.currencyconverter.data.model.Query
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
class CurrencyConversionUseCaseTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val currencyRepository: CurrencyRepository = mock()
    private val currenctConversionUseCase by lazy { CurrencyConversionUseCaseImpl(currencyRepository) }

    @Test
    fun testCurrencyConversionPositiveFlow() {
        runTest {
            whenever(
                currencyRepository.convertCurrency(
                    anyInt(),
                    anyString(),
                    anyString()
                )
            ).thenReturn(
                ConvertResponseModel(
                    "",
                    "",
                    Info(100.0, 23456),
                    Query(5, "USD", "INR"),
                    500.0,
                    null,
                    true
                )
            )
            val resp = currenctConversionUseCase.invoke(5, "USD", "INR")
            assert(resp.success)
            assert(resp.info?.rate == 100.0)
            assert(resp.result == 500.0)
            verify(currencyRepository, times(1)).convertCurrency(5, "USD", "INR")
        }
    }

        @Test
        fun testCurrencyConversionNegativeFlow() {
            runTest {
                whenever(currencyRepository.convertCurrency(anyInt(), anyString(), anyString())).thenReturn(
                    ConvertResponseModel(null, null, null, null, null, com.siddroid.currencyconverter.data.model.Error(403, "No input provided", "BAD_REQUEST"), false)
                )

                val resp = currenctConversionUseCase.invoke(0, "", "")
                assert(!resp.success)
                verify(currencyRepository, times(1)).convertCurrency(0, "", "")
            }
        }
}