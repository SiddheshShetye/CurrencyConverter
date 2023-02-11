package com.siddroid.currencyconverter.data.di

import com.siddroid.currencyconverter.data.CurrencyRepositoryImpl
import com.siddroid.currencyconverter.data.CurrencyService
import com.siddroid.currencyconverter.data.db.TransactionsDAO
import com.siddroid.currencyconverter.domain.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideCurrencyRepository(currencyService: CurrencyService, transactionsDAO: TransactionsDAO): CurrencyRepository {
        return CurrencyRepositoryImpl(currencyService, transactionsDAO)
    }

    @Provides
    @ViewModelScoped
    fun provideCurrencyService(retrofit: Retrofit): CurrencyService {
        return retrofit.create(CurrencyService::class.java)
    }
}