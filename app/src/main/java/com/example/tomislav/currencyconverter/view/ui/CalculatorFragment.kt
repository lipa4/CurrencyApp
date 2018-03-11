package com.example.tomislav.currencyconverter.view.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.tomislav.currencyconverter.R
import com.example.tomislav.currencyconverter.viewmodel.CurrencyViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.rates_list_fragment.*
import javax.inject.Inject
import com.mynameismidori.currencypicker.CurrencyPickerListener
import com.mynameismidori.currencypicker.CurrencyPicker
import kotlinx.android.synthetic.main.calculator_fragment.*


class CalculatorFragment():DaggerFragment(){

   /* lateinit var viewModel:CurrencyViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory*/
    //TODO inject problem no.2
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.calculator_fragment,container,false)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // viewModel = ViewModelProviders.of(this,viewModelFactory).get(CurrencyViewModel::class.java)
    }



}