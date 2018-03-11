package com.example.tomislav.currencyconverter.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tomislav.currencyconverter.R
import com.example.tomislav.currencyconverter.data.model.Currency
import kotlinx.android.synthetic.main.currency_rate_list_item.view.*


class CurrencyListAdapter(items:List<Currency> = ArrayList<Currency>()) : RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder>() {

    var currencyList= items


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.currency_rate_list_item, parent, false)
        return CurrencyViewHolder(itemView)
    }

    override fun getItemCount() = currencyList.size


    override fun onBindViewHolder(holder:CurrencyViewHolder, position: Int) {
        val item = currencyList.get(position)
        holder.currencyName.text = item.currency_code
        holder.buyRate.text = item.buying_rate.toString()
        holder.medianRate.text = item.median_rate.toString()
        holder.sellRate.text = item.selling_rate.toString()
    }

    class CurrencyViewHolder(view:View):RecyclerView.ViewHolder(view){
        val currencyName = itemView.list_item_currency_name
        val buyRate = itemView.list_item_buy_rate_value
        val medianRate = itemView.list_item_median_rate_value
        val sellRate = itemView.list_item_sell_rate_value
    }


}
