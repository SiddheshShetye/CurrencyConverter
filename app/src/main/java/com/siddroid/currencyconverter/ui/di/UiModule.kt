package com.siddroid.currencyconverter.ui.di

import android.content.Context
import android.widget.ArrayAdapter
import com.siddroid.currencyconverter.ui.adapters.HistoricalDataAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class UiModule {

    @Provides
    @ActivityScoped
    fun providesHistoricalDataAdapter(): HistoricalDataAdapter {
        return HistoricalDataAdapter()
    }

    @Provides
    fun provideArrayAdapter(@ActivityContext context: Context): ArrayAdapter<String> {
        val adapter = ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, mutableListOf())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }
}