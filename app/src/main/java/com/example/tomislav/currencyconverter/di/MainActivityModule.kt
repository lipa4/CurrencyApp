package com.example.tomislav.currencyconverter.di

import com.example.tomislav.currencyconverter.data.repository.CurrenciesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class MainActivityModule {

    @Provides
    @Singleton
    fun provideCurrencyRepository():CurrenciesRepository = CurrenciesRepository()
}