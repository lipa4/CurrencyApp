package com.example.tomislav.currencyconverter.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.tomislav.currencyconverter.viewmodel.CurrencyViewModel
import com.example.tomislav.currencyconverter.viewmodel.CurrencyViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import java.util.*

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey( CurrencyViewModel::class )
    abstract fun bindCurrencyViewModel( currencyViewModel: CurrencyViewModel ): ViewModel

    @Binds
    abstract fun bindViewModelFactory( currencyFactory: CurrencyViewModelFactory): ViewModelProvider.Factory

}