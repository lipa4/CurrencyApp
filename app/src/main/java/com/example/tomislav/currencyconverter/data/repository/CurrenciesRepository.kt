package com.example.tomislav.currencyconverter.data.repository

import android.content.Context
import android.support.design.widget.Snackbar
import android.util.Log
import com.example.tomislav.currencyconverter.R
import com.example.tomislav.currencyconverter.data.model.Currency
import com.mynameismidori.currencypicker.ExtendedCurrency
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import com.example.tomislav.currencyconverter.utils.NetworkUtils
import com.example.tomislav.currencyconverter.utils.UiUtils
import com.example.tomislav.currencyconverter.utils.rx.RxSchedulers
import com.example.tomislav.currencyconverter.view.adapter.CurrencyListAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.rates_list_fragment.*
import javax.inject.Singleton
import java.math.BigDecimal
import java.math.RoundingMode


@Singleton
class CurrenciesRepository(private val hnbService: HNBService, private val rxSchedulers: RxSchedulers){

    private var list:Array<ExtendedCurrency>
    lateinit var currencyListNow:List<Currency>
    var subscriptions: CompositeDisposable = CompositeDisposable()

    lateinit var exchangeRates:Observable<List<Currency>>
    companion object MYCURRENCIES{
        var currencies = arrayListOf<String>("HRK","AUD","CAD","CZK","DKK","HUF","JPY","NOK","SEK","CHF","GBP","USD","BAM","EUR","PLN")
    }

    init {
        val tempList: ArrayList<ExtendedCurrency> = ArrayList()
        tempList.addAll(ExtendedCurrency.CURRENCIES)
        tempList.add(createBAMCurrency())
        val finalList=tempList.filter { it.code in MYCURRENCIES.currencies }
        this.list=finalList.toTypedArray()
        getExchangeRatesNow()
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

    fun isNetworkAvailable(context: Context): Observable<Boolean> {
        return NetworkUtils.isNetworkAvailableObservable(context)
    }

    fun getExchangeRatesFromHNB(date:String) = hnbService.getCurrencyRates(date)


    fun getStringDateFormat(date:Date):String{
        val formatter = SimpleDateFormat("yyyy-MM-dd ")
        return formatter.format(date).toString()
    }

    private fun getExchangeRatesNow(){
        subscriptions.add(getExchangeRatesFromHNB(getStringDateFormat(Date()))
                .subscribeOn(rxSchedulers.compute())
                .observeOn(rxSchedulers.androidThread())
                .subscribe({ currencies ->
            Log.d("Currency list: ", "Load completed!")
            currencyListNow=currencies
        }, { throwable -> UiUtils.handleThrowable(throwable) }
        ));
        subscriptions.clear()
    }

    fun convertCurrencies(fromCurrency:Currency, toCurrency:Currency, amount:Double): Double {
        val fromCurrencyToKN = fromCurrency.median_rate * fromCurrency.unit_value
        val toCurrencyToKN = toCurrency.median_rate * toCurrency.unit_value
        var result: Double =((amount * fromCurrencyToKN )/ toCurrencyToKN)
        val bd = BigDecimal(result).setScale(2, RoundingMode.HALF_EVEN)
        result = bd.toDouble()
        return result
    }
}