package com.example.tomislav.currencyconverter.data.repository

import android.content.Context
import com.example.tomislav.currencyconverter.R
import com.example.tomislav.currencyconverter.data.model.Currency
import com.mynameismidori.currencypicker.ExtendedCurrency
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import com.example.tomislav.currencyconverter.utils.NetworkUtils



class CurrenciesRepository{

    private val list:Array<ExtendedCurrency>
    @Inject
    lateinit var hnbService:HNBService
    @Inject
    lateinit var context: Context
    lateinit var exchangeRates:Observable<List<Currency>>

    init {
        val tempList: ArrayList<ExtendedCurrency> = ArrayList()
        tempList.addAll(ExtendedCurrency.CURRENCIES)
        tempList.add(createBAMCurrency())
        this.list=tempList as Array<ExtendedCurrency>
    }

    private fun createBAMCurrency():ExtendedCurrency{
        return ExtendedCurrency("BAM","Bosnian mark","KM", R.drawable.flag_bam)
    }

    fun getFilteredCurrencyList(excludeCode:String) = list.filter { it.code!=excludeCode }.toTypedArray()

    fun getExchangeRates(inCurrency:String, date:Date = Date() ): Observable<List<Currency>> {
        val stringDate = getStringDateFormat(date)
        if(inCurrency == "HRK")
            exchangeRates = getExchangeRatesFromHNB(stringDate)
        //TODO implement currencies show in different currency
        return exchangeRates
    }

    fun isNetworkAvailable(): Observable<Boolean> {
        return NetworkUtils.isNetworkAvailableObservable(context)
    }

    fun getExchangeRatesFromHNB(date:String) = hnbService.getCurrencyRates(date)


    fun getStringDateFormat(date:Date):String{
        val formatter = SimpleDateFormat("yyyy-MM-dd ")
        return formatter.format(date).toString()
    }

}