package com.example.tomislav.currencyconverter.utils

import android.content.Context
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.widget.Toast


object UiUtils {

    fun handleThrowable(throwable: Throwable) {
        Log.e("RxError: ", "There was an error!", throwable)
    }

    fun showSnackbar(context: Context, message: String, length: Int) {
        Toast.makeText(context,message,length).show()
    }
}