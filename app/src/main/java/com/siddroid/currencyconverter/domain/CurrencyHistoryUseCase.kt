package com.siddroid.currencyconverter.domain

import com.siddroid.currencyconverter.data.db.Transactions

interface CurrencyHistoryUseCase {
    suspend fun fetch(date: Long): List<Transactions>
}