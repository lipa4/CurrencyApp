package com.example.tomislav.currencyconverter.di

import android.app.Application
import android.content.Context
import com.example.tomislav.currencyconverter.data.repository.CurrenciesRepository
import com.example.tomislav.currencyconverter.data.repository.HNBService
import com.example.tomislav.currencyconverter.utils.rx.RxSchedulers
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule{
    @Provides
    @Singleton
    fun provideCurrencyRepository(hnbService: HNBService, rxSchedulers: RxSchedulers): CurrenciesRepository = CurrenciesRepository(hnbService,rxSchedulers)

}
