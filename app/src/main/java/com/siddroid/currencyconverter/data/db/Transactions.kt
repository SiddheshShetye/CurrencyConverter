package com.siddroid.currencyconverter.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transactions(@PrimaryKey(autoGenerate = true) val tid: Int = 0,
                       @ColumnInfo val date: Long,
                       @ColumnInfo val amount: String,
                       @ColumnInfo val from: String,
                       @ColumnInfo val to: String,
                        @ColumnInfo val convertedValue: String
)