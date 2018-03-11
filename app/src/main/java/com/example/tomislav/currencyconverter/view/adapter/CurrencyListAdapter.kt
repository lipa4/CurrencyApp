package com.example.tomislav.currencyconverter.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tomislav.currencyconverter.R


class CurrencyListAdapter() : RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.currency_rate_list_item, parent, false)
        return CurrencyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder:CurrencyViewHolder, position: Int) {
    }

    class CurrencyViewHolder(view:View):RecyclerView.ViewHolder(view){

    }
}
