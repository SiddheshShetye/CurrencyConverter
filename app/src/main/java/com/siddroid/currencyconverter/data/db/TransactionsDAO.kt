package com.siddroid.currencyconverter.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface TransactionsDAO {
    @Query("Select * from transactions where date >= :time")
    fun getAllTransactions(time: Long): List<Transactions>

    @Insert
    fun insertTransaction(transaction: Transactions)
}