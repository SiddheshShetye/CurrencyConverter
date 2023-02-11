package com.siddroid.currencyconverter.ui.model

import com.siddroid.currencyconverter.data.db.Transactions
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object StateMapper {
    private val formatter: SimpleDateFormat

    init {
        formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        formatter.timeZone = TimeZone.getDefault()
    }

        fun mapTransactionToHistoryViewState(transaction: Transactions): HistoryViewState {
            val date = Date().apply { time = TimeUnit.SECONDS.toMillis(transaction.date) }
            return HistoryViewState(formatter.format(date), "${transaction.from} - ${transaction.to}", transaction.amount, transaction.convertedValue)
        }

}