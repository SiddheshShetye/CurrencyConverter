package com.siddroid.currencyconverter.data.model

data class ConvertResponseModel(
    val date: String?,
    val historical: String?,
    val info: Info?,
    val query: Query?,
    val result: Double?,
    val error: Error? = null,
    val success: Boolean
)

data class Info(
    val rate: Double,
    val timestamp: Long
)

data class Query(
    val amount: Int,
    val from: String,
    val to: String
)

data class Error(
    val code: Int,
    val info: String,
    val type: String
)
