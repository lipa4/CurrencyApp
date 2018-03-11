package com.example.tomislav.currencyconverter.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import com.example.tomislav.currencyconverter.view.ui.CalculatorFragment
import com.example.tomislav.currencyconverter.view.ui.ChartFragment
import com.example.tomislav.currencyconverter.view.ui.RatesListFragment
import java.util.ArrayList
import javax.inject.Inject


class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val fragments = ArrayList<Fragment>()

    /*@Inject
    lateinit var ratesListFragment: RatesListFragment
    @Inject
    lateinit var calculatorFragment: CalculatorFragment
     @Inject
    lateinit var chartFragment: ChartFragment
*/
    //TODO implement injection
    init {
        fragments.clear()
        fragments.add(RatesListFragment())
        fragments.add(CalculatorFragment())
        fragments.add(ChartFragment())
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, 1, fragments[1])
    }

}