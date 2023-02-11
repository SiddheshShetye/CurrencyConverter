package com.siddroid.currencyconverter.data

import com.siddroid.currencyconverter.data.db.Transactions
import com.siddroid.currencyconverter.data.db.TransactionsDAO
import com.siddroid.currencyconverter.data.model.ConvertResponseModel
import com.siddroid.currencyconverter.data.model.CurrencyModel
import com.siddroid.currencyconverter.domain.CurrencyRepository
import javax.inject.Inject


class CurrencyRepositoryImpl @Inject constructor(private val currencyService: CurrencyService, val transactionsDAO: TransactionsDAO): CurrencyRepository {
    override suspend fun convertCurrency(amount: Int, from: String, to: String): ConvertResponseModel {
        val resp = currencyService.convert(amount, from, to)
        if (resp.success) {
            transactionsDAO.insertTransaction(
                Transactions(
                    amount = resp.query?.amount.toString(),
                    date = resp.info?.timestamp ?: 0.toLong(),
                    from = resp.query?.from.orEmpty(),
                    to = resp.query?.to.orEmpty(),
                    convertedValue = resp.result?.toString().orEmpty()
                )
            )
        }
        return resp
    }

    override suspend fun getHistoricalData(date: Long): List<Transactions> {
        return transactionsDAO.getAllTransactions(date)
    }

    override suspend fun getCurrencies(): CurrencyModel {
        return currencyService.getCurrencies()
    }

}