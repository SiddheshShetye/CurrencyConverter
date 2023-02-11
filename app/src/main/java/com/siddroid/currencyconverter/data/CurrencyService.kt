package com.siddroid.currencyconverter.data

import com.siddroid.currencyconverter.BuildConfig
import com.siddroid.currencyconverter.data.model.ConvertResponseModel
import com.siddroid.currencyconverter.data.model.CurrencyModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CurrencyService {

    @GET("convert")
    @Headers("apikey:${BuildConfig.fixer_api_key}")
    suspend fun convert(@Query("amount") amount: Int, @Query("from") from: String, @Query("to") to: String): ConvertResponseModel

    @GET("symbols")
    @Headers("apikey:${BuildConfig.fixer_api_key}")
    suspend fun getCurrencies(): CurrencyModel
}