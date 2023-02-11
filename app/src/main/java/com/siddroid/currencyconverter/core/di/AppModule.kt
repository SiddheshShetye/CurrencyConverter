package com.siddroid.currencyconverter.core.di

import android.content.Context
import androidx.room.Room
import com.siddroid.currencyconverter.BuildConfig
import com.siddroid.currencyconverter.core.db.CurrencyConverterDB
import com.siddroid.currencyconverter.core.network.InternetConnectivity
import com.siddroid.currencyconverter.data.db.TransactionsDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyConverterDatabase(@ApplicationContext appContext: Context): CurrencyConverterDB {
        return Room.databaseBuilder(appContext,
            CurrencyConverterDB::class.java,
            "cur-con-db")
            .build()
    }

    @Provides
    @Singleton
    fun provideTransactionsDao(db: CurrencyConverterDB): TransactionsDAO {
        return db.transactionsDao()
    }

    @Provides
    @Singleton
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Provides
    @Singleton
    fun provideInternetConnectivity(@ApplicationContext context: Context): InternetConnectivity {
        return InternetConnectivity(context)
    }


}