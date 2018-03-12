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

    fun convertCurrencies(from:Currency, to:Currency, amount:Double) = repository.convertCurrencies(from,to,amount)

    fun getFilteredCurrencyListFrom(excludeCode:String = "EUR")= repository.getFilteredCurrencyList(excludeCode)

    fun getFilteredCurrencyListTo(excludeCode:String = "HRK")= repository.getFilteredCurrencyList(excludeCode)

}