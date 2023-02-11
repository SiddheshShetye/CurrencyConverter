package com.siddroid.currencyconverter.domain.di

import com.siddroid.currencyconverter.domain.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideCurrencyConversionUseCase(repository: CurrencyRepository): CurrencyConversionUseCase {
        return CurrencyConversionUseCaseImpl(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideCurrencyHistoryUseCase(repository: CurrencyRepository): CurrencyHistoryUseCase {
        return CurrencyHistoryUseCaseImpl(repository)
    }
}