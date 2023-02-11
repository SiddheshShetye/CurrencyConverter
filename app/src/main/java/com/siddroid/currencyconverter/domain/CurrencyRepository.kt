package com.siddroid.currencyconverter.domain

import com.siddroid.currencyconverter.data.db.Transactions
import com.siddroid.currencyconverter.data.model.ConvertResponseModel
import com.siddroid.currencyconverter.data.model.CurrencyModel

interface CurrencyRepository {
    suspend fun convertCurrency(amount: Int, from: String, to: String): ConvertResponseModel
    suspend fun getHistoricalData(date: Long): List<Transactions>
    suspend fun getCurrencies(): CurrencyModel
}