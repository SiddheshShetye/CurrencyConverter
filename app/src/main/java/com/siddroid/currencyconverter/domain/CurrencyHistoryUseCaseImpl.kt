package com.siddroid.currencyconverter.domain

import com.siddroid.currencyconverter.data.db.Transactions

class CurrencyHistoryUseCaseImpl(private val currencyRepository: CurrencyRepository): CurrencyHistoryUseCase {
    override suspend fun fetch(date: Long):List<Transactions> {
        return currencyRepository.getHistoricalData(date)
    }

}