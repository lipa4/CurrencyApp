package com.example.tomislav.currencyconverter.data.model


data class Currency(val unit_value:Int,
                    val currency_code:String,
                    val median_rate:Double,
                    val selling_rate:Double,
                    val buying_rate:Double){

}