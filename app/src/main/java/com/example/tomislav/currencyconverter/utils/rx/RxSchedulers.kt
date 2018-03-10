package com.example.tomislav.currencyconverter.utils.rx

import io.reactivex.Scheduler


interface RxSchedulers {

    fun runOnBackground(): Scheduler

    fun io(): Scheduler

    fun compute(): Scheduler

    fun androidThread(): Scheduler

    fun internet(): Scheduler

}