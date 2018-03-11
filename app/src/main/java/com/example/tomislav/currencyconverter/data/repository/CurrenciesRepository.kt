package com.example.tomislav.currencyconverter.data.repository

import com.example.tomislav.currencyconverter.R
import com.example.tomislav.currencyconverter.data.model.Currency
import com.mynameismidori.currencypicker.ExtendedCurrency
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CurrenciesRepository{

    private val list:Array<ExtendedCurrency>
    @Inject
    lateinit var hnbService:HNBService


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

    fun getExchangeRates(forCurrency:Currency, inCurrency:Currency, date:Date = Date() ){
        val tempDate = getStringDateFormat(date)
    }

    fun getExchangeRatesFromHNB(date:String){

    }

    fun getStringDateFormat(date:Date):String{
        val formatter = SimpleDateFormat("yyyy-MM-dd ")
        return formatter.format(date).toString()
    }

}