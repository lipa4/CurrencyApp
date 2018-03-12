package com.example.tomislav.currencyconverter.view.ui


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tomislav.currencyconverter.R
import com.example.tomislav.currencyconverter.viewmodel.CurrencyViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class CalculatorFragment():DaggerFragment(){

    lateinit var viewModel: CurrencyViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.calculator_fragment,container,false)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(CurrencyViewModel::class.java)
    }



}