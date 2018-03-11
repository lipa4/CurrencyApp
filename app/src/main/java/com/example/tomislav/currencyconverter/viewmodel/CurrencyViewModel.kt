package com.example.tomislav.currencyconverter.viewmodel

import android.arch.lifecycle.ViewModel
import javax.inject.Inject
import com.example.tomislav.currencyconverter.data.model.Currency
import com.example.tomislav.currencyconverter.data.repository.CurrenciesRepository
import io.reactivex.Observable



class CurrencyViewModel(): ViewModel(){


    @Inject
    lateinit var repository: CurrenciesRepository


    fun getcurrencyExchangeRates(inCurrency:String):Observable<List<Currency>>{
        return repository.getExchangeRates(inCurrency)
    }

    fun isNetworkAvailable(): Observable<Boolean> = repository.isNetworkAvailable()




}