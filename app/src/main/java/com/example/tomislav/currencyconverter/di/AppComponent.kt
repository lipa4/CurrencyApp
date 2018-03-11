package com.example.tomislav.currencyconverter.di

import com.example.tomislav.currencyconverter.App
import com.example.tomislav.currencyconverter.view.adapter.ViewPagerAdapter
import com.example.tomislav.currencyconverter.viewmodel.CurrencyViewModel
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class),
                    (AppModule::class),
                    (NetworkModule::class),
                    (ActivityBuilder::class),
                    (ViewModelModule::class)])

interface AppComponent: AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()

    //TODO override build method
    fun inject( viewPagerAdapter: ViewPagerAdapter)

}