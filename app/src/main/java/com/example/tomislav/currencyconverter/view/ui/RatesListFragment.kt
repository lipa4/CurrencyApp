package com.example.tomislav.currencyconverter.view.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tomislav.currencyconverter.R
import com.example.tomislav.currencyconverter.view.adapter.CurrencyListAdapter
import com.example.tomislav.currencyconverter.viewmodel.CurrencyViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.rates_list_fragment.*
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable
import com.example.tomislav.currencyconverter.utils.rx.RxSchedulers
import io.reactivex.disposables.Disposable
import android.support.design.widget.Snackbar
import android.util.Log
import com.example.tomislav.currencyconverter.utils.UiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork




class RatesListFragment():DaggerFragment(){

    @Inject
    lateinit var rxSchedulers: RxSchedulers

    var subscriptions: CompositeDisposable = CompositeDisposable()
    private var waitForNetwork: Disposable? = null

    lateinit var viewModel: CurrencyViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.rates_list_fragment,container,false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rates_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
            adapter = CurrencyListAdapter()

        }
        subscriptions.add(getCurrencyList());
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(CurrencyViewModel::class.java)
    }


    private fun getCurrencyList(): Disposable {

        return viewModel.isNetworkAvailable(context!!).doOnNext({ networkAvailable ->
            if (!networkAvailable) {
                Log.d("Connection error: ", "No connection!")
                UiUtils.showSnackbar(context!!, "No connection!", Snackbar.LENGTH_LONG)
                subscriptions.add(waitForConnection())
            }
        }).filter({ isNetworkAvailable -> true }).flatMap({ isAvailable -> viewModel.getcurrencyExchangeRates("HRK") }).subscribeOn(rxSchedulers.internet()).observeOn(rxSchedulers.androidThread()).subscribe({ currencies ->
            Log.d("Currency list: ", "Load completed!")
            rates_recycler_view.swapAdapter(CurrencyListAdapter(currencies),true)
        }, { throwable -> UiUtils.handleThrowable(throwable) }
        )

    }
    //TODO implement this feature
    private fun waitForConnection(): Disposable {

        waitForNetwork = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ connected ->
                    if (connected!!) {
                        getCurrencyList()
                        subscriptions.remove(waitForNetwork!!)
                    }

                }
                ) { throwable -> UiUtils.handleThrowable(throwable) }
        return waitForNetwork!!
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.clear()
    }


}