package com.siddroid.currencyconverter.domain

import com.siddroid.currencyconverter.data.model.ConvertResponseModel
import com.siddroid.currencyconverter.data.model.CurrencyModel
import javax.inject.Inject

class CurrencyConversionUseCaseImpl @Inject constructor(private val repository: CurrencyRepository): CurrencyConversionUseCase {
    override suspend fun invoke(amount: Int, from: String, to: String): ConvertResponseModel {
        return repository.convertCurrency(amount, from, to)
    }

    override suspend fun fetch(): CurrencyModel {
        return repository.getCurrencies();
    }
}