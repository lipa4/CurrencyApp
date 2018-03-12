package com.example.tomislav.currencyconverter.di

import com.example.tomislav.currencyconverter.data.repository.CurrenciesRepository
import com.example.tomislav.currencyconverter.viewmodel.CurrencyViewModel
import dagger.Component

@Component( modules = arrayOf(
        ViewModelModule::class
))
interface ViewModelComponent {
    fun inject( currencyViewModel: CurrencyViewModel)

}