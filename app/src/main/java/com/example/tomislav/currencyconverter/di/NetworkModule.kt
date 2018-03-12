package com.example.tomislav.currencyconverter.di

import android.content.Context
import com.example.tomislav.currencyconverter.data.repository.CurrenciesRepository
import com.example.tomislav.currencyconverter.data.repository.HNBService
import com.example.tomislav.currencyconverter.utils.rx.AppRxSchedulers
import com.example.tomislav.currencyconverter.utils.rx.RxSchedulers
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
class NetworkModule{

    companion object {
        val BASE_URL = "http://hnbex.eu/api/v1/"
    }

    @Singleton
    @Provides
    internal fun provideApiService(client: OkHttpClient, moshi: MoshiConverterFactory): HNBService {
        val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(moshi)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return  retrofit.create(HNBService::class.java)
    }


    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        return builder.build()
    }


  /*  @Singleton
    @Provides
    fun provideCache(file: File): Cache = Cache(file,10*10*1000)

    @Singleton
    @Provides
    fun provideCacheFile(context: Context): File = context.filesDir*/

    @Provides
    fun provideMoshiClient(): MoshiConverterFactory = MoshiConverterFactory.create()

    @Provides
    fun provideRxSchedulers(): RxSchedulers = AppRxSchedulers()

}