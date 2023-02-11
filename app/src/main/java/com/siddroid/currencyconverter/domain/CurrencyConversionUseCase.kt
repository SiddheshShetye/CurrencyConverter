package com.siddroid.currencyconverter.domain

import com.siddroid.currencyconverter.data.model.ConvertResponseModel
import com.siddroid.currencyconverter.data.model.CurrencyModel

interface CurrencyConversionUseCase {
    suspend fun invoke(amount: Int, from: String, to: String): ConvertResponseModel
    suspend fun fetch(): CurrencyModel
}