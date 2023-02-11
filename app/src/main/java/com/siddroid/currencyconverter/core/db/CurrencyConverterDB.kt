package com.siddroid.currencyconverter.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.siddroid.currencyconverter.data.db.Transactions
import com.siddroid.currencyconverter.data.db.TransactionsDAO

@Database(entities = [Transactions::class], version = 1)
abstract class CurrencyConverterDB: RoomDatabase() {
    abstract fun transactionsDao(): TransactionsDAO
}