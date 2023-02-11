package com.siddroid.currencyconverter.data.model


    data class CurrencyModel(
        val success: Boolean,
        val symbols: Map<String, String>
    )