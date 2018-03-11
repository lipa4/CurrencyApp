package com.example.tomislav.currencyconverter.data.repository

import com.example.tomislav.currencyconverter.data.model.Currency
import com.example.tomislav.currencyconverter.data.model.CurrencyDate
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HNBService{
    @GET("rates/daily/")
    fun getCurrencyRates(@Query("date")date:String): Call<List<Currency>>

    @GET("rates/{currency}/")
    fun getCurrencyRatesForPeriod(@Path("currency")
                                  @Query("from") from: String,
                                  @Query("to") to: String): Call<List<CurrencyDate>>
}