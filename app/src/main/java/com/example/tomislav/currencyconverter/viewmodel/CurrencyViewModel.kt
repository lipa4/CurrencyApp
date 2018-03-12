package com.example.tomislav.currencyconverter.viewmodel

import android.arch.lifecycle.ViewModel
import android.content.Context
import javax.inject.Inject
import com.example.tomislav.currencyconverter.data.model.Currency
import com.example.tomislav.currencyconverter.data.repository.CurrenciesRepository
import io.reactivex.Observable



class CurrencyViewModel @Inject constructor(val repository: CurrenciesRepository): ViewModel(){


    fun getcurrencyExchangeRates(inCurrency:String):Observable<List<Currency>>{
        return repository.getExchangeRates(inCurrency)
    }

    fun isNetworkAvailable(context: Context): Observable<Boolean> = repository.isNetworkAvailable(context)


}