package com.example.tomislav.currencyconverter.view.ui


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tomislav.currencyconverter.R
import com.example.tomislav.currencyconverter.data.model.Currency
import com.example.tomislav.currencyconverter.utils.UiUtils
import com.example.tomislav.currencyconverter.utils.rx.RxSchedulers
import com.example.tomislav.currencyconverter.view.adapter.CurrencyListAdapter
import com.example.tomislav.currencyconverter.viewmodel.CurrencyViewModel
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.jakewharton.rxbinding2.widget.RxTextView
import com.mynameismidori.currencypicker.CurrencyPicker
import com.mynameismidori.currencypicker.CurrencyPickerListener
import com.mynameismidori.currencypicker.ExtendedCurrency
import dagger.android.support.DaggerFragment
import io.reactivex.BackpressureStrategy
import kotlinx.android.synthetic.main.calculator_fragment.*
import javax.inject.Inject
import io.reactivex.subscribers.DisposableSubscriber
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Flowables
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.rates_list_fragment.*


class CalculatorFragment():DaggerFragment(){

    companion object ButtonDefaultValues{
        const val DEFAULT_FROM_VALUE = "EUR"
        const val DEFAULT_TO_VALUE = "HRK"
        const val DEFAULT_FROM_FLAG = R.drawable.flag_eur
        const val DEFAULT_TO_FLAG = R.drawable.flag_hrk
    }
    private var fromButtonState:String = ButtonDefaultValues.DEFAULT_FROM_VALUE
    private var toButtonState:String = ButtonDefaultValues.DEFAULT_TO_VALUE
    private var disposableSubscriber: DisposableSubscriber<Boolean>? = null
    private var amountChangeObservable: Observable<CharSequence>? = null
    private var fromButtonObservable:Subject<String> = PublishSubject.create()
    private var toButtonObservable:Subject<CharSequence> = PublishSubject.create()
    lateinit var viewModel: CurrencyViewModel
    lateinit var tempList:List<Currency>

    @Inject
    lateinit var rxSchedulers: RxSchedulers

    var subscriptions: CompositeDisposable = CompositeDisposable()
    private var waitForNetwork: Disposable? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.calculator_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        from_button.apply {
            text = ButtonDefaultValues.DEFAULT_FROM_VALUE
            fromButtonState=ButtonDefaultValues.DEFAULT_FROM_VALUE
            fromButtonObservable.onNext(fromButtonState)
            setCompoundDrawablesRelativeWithIntrinsicBounds(ButtonDefaultValues.DEFAULT_FROM_FLAG,0,0,0)
            setOnClickListener({showCurrencyPicker(false)})
        }
        to_button.apply {
            text = ButtonDefaultValues.DEFAULT_TO_VALUE
            toButtonState=ButtonDefaultValues.DEFAULT_TO_VALUE
            fromButtonObservable.onNext(toButtonState)
            setCompoundDrawablesRelativeWithIntrinsicBounds(ButtonDefaultValues.DEFAULT_TO_FLAG,0,0,0)
            setOnClickListener({showCurrencyPicker(true)})
        }
        floatingActionButton.setOnClickListener({ swapButtonValues()})
        subscriptions.add(getCurrencyList());
        amountChangeObservable=RxTextView.textChanges(editText)
        converterListener()
    }


    override fun onDestroy() {
        super.onDestroy()
        subscriptions.clear()
        disposableSubscriber?.dispose()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(CurrencyViewModel::class.java)
    }

//  From is 0 and To is 1
    fun showCurrencyPicker(fromOrTo:Boolean){
        var picker = CurrencyPicker.newInstance("Select currency")
        if(fromOrTo){
            picker.setCurrenciesList(viewModel.getFilteredCurrencyListTo(from_button.text.toString()).asList())
        }
        else{
            picker.setCurrenciesList(viewModel.getFilteredCurrencyListFrom(to_button.text.toString()).asList())
        }
        picker.setListener(object : CurrencyPickerListener{
            override fun onSelectCurrency(name:String, code: String, symbol: String, flagDrawableResID: Int) {
                if (fromOrTo){
                    to_button.apply {
                        text = code
                        toButtonState = code
                        toButtonObservable.onNext(toButtonState)
                        setCompoundDrawablesRelativeWithIntrinsicBounds(flagDrawableResID,0,0,0)
                    }
                }
                else{
                    from_button.apply {
                        text = code
                        fromButtonState = code
                        fromButtonObservable.onNext(fromButtonState)
                        setCompoundDrawablesRelativeWithIntrinsicBounds(flagDrawableResID,0,0,0)
                    }
                }
            picker.dismiss()
            }
        })
        picker.show(fragmentManager,"CURRENCY_PICKER")
    }
    private fun swapButtonValues(){
        var tempValue = from_button.text
        var tempFlag = from_button.compoundDrawablesRelative[0]
        from_button.text=to_button.text
        fromButtonState=from_button.text.toString()
        fromButtonObservable.onNext(fromButtonState)
        from_button.setCompoundDrawablesRelativeWithIntrinsicBounds(to_button.compoundDrawablesRelative[0],null,null,null)
        to_button.text=tempValue
        toButtonState=to_button.text.toString()
        toButtonObservable.onNext(toButtonState)
        to_button.setCompoundDrawablesRelativeWithIntrinsicBounds(tempFlag,null,null,null)
    }

    private fun converterListener() {



        Observables.combineLatest(
                fromButtonObservable,
                amountChangeObservable!!,
                toButtonObservable,
                this::combine).subscribe(object :Observer<Boolean>{

            override fun onSubscribe(d: Disposable) {}
            override fun onNext(t: Boolean) {
                if (t) {
                    val amount = java.lang.Double.valueOf(editText.text.toString())
                    val from = getCurrencyObject(from_button.text.toString())
                    val to = getCurrencyObject(to_button.text.toString())
                    val result = viewModel.convertCurrencies(from, to, amount)
                    calculator_text_switcher.setText(result.toString()+" "+getCurrencySymbol(to_button.text.toString()) )
                }
            }
            override fun onError(e: Throwable) {
                Log.e("Disposable subscriber: ", "There was an error!", e)

            }
            override fun onComplete() {
                Log.d("Converter subscriber: ", "Completed!")

            }
        })

    }

    fun combine(from: String, amount: CharSequence, to: CharSequence): Boolean {
        if (amount.isEmpty()) {
            calculator_text_switcher.setText("")
            return false
        }
        return true

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
            tempList=currencies
        }, { throwable -> UiUtils.handleThrowable(throwable) }
        )

    }
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

    fun getCurrencyObject(code:String) = tempList?.first { it.currency_code==code }
    fun getCurrencySymbol(code:String):String {
        val temp:ExtendedCurrency = ExtendedCurrency.CURRENCIES.first { it.code==code }
        return temp.symbol
    }
}
