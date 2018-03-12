package com.example.tomislav.currencyconverter.view.ui


import android.graphics.Typeface
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.view.ViewPager
import android.view.WindowManager
import com.example.tomislav.currencyconverter.R
import com.example.tomislav.currencyconverter.view.adapter.ViewPagerAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import it.sephiroth.android.library.bottomnavigation.BottomNavigation


class MainActivity : DaggerAppCompatActivity(), BottomNavigation.OnMenuItemSelectionListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBarTranslucent(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        initializeBottomNavigation()
        initializeUI()
    }

    fun setStatusBarTranslucent(makeTranslucent: Boolean) {
        if (makeTranslucent) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    override fun onContentChanged() {
        super.onContentChanged()
        BottomNavigation.let {
            val typeface = Typeface.createFromAsset(assets, "Roboto-Light.ttf")
            BottomNavigation.setOnMenuItemClickListener(this)
            BottomNavigation.setDefaultTypeface(typeface)
        }
    }

    protected fun initializeBottomNavigation() {
            BottomNavigation.setDefaultSelectedIndex(0)
    }

    fun initializeUI(){

        val listener = it.sephiroth.android.library.bottomnavigation.BottomNavigation.OnMenuChangedListener {
            view_pager.adapter = ViewPagerAdapter(supportFragmentManager)
            view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
                override fun onPageSelected(position: Int) {
                    if (BottomNavigation.getSelectedIndex() != position) {
                        BottomNavigation.setSelectedIndex(position, false)
                    }
                }
            })
        }

        view_pager.let {
            BottomNavigation.setOnMenuChangedListener(listener)
        }
    }

    override fun onMenuItemSelect(itemId: Int, position: Int, fromUser: Boolean) {
        if (fromUser) {
            view_pager?.currentItem=position
        }
    }

    override fun onMenuItemReselect(@IdRes itemId: Int, position: Int, fromUser: Boolean) {
        onMenuItemSelect(itemId,position,fromUser)
    }


}
