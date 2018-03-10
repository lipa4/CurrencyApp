package com.example.tomislav.currencyconverter.di

import com.example.tomislav.currencyconverter.view.ui.CalculatorFragment
import com.example.tomislav.currencyconverter.view.ui.ChartFragment
import com.example.tomislav.currencyconverter.view.ui.RatesListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeRatesListFragment(): RatesListFragment

    @ContributesAndroidInjector
    abstract fun contributeCalculatorFragment(): CalculatorFragment

    @ContributesAndroidInjector
    abstract fun contributeChartFragment(): ChartFragment
}