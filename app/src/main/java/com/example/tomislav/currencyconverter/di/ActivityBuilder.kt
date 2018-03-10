package com.example.tomislav.currencyconverter.di

import com.example.tomislav.currencyconverter.view.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class, FragmentBuildersModule::class))
    internal abstract fun bindMainActivity(): MainActivity


}